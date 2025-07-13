package com.companies.house.cucumber.pages;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.annotations.LazyComponent;
import com.companies.house.context.ScenarioContext;
import com.companies.house.service.AccessibilityService;
import com.deque.html.axecore.results.Results;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@LazyComponent
public class HomePage extends AbstractBasePage {

    @Value("${application.url}")
    private String url;

    @Value("${dummy.phone.number}")
    private String dummyPhoneNumber;

    @LazyAutowired
    private Faker faker;

    @Autowired
    private ScenarioContext context;

    @Autowired
    private AccessibilityService accessibilityService;

    @LazyAutowired
    private SharedMethods sharedMethods;

    // Header
    @FindBy(how = How.CSS, using = "#root-container h1")
    private WebElement headerText;

    @FindBy(how = How.CSS, using = "[class='lead mb-4']")
    private WebElement headerMessage;

    // labels and smaller headings
    @FindBy(how = How.CSS, using = "#booking h3")
    private WebElement checkAvailabilityHeader;

    @FindBy(how = How.CSS, using = "label[for='checkin']")
    private WebElement labelCheckIn;

    @FindBy(how = How.CSS, using = "label[for='checkout']")
    private WebElement labelCheckout;

    @FindBy(how = How.CSS, using = "#rooms [class='display-5']")
    private WebElement ourRoomsHeading;

    @FindBy(how = How.CSS, using = "#rooms [class='display-5'] + p")
    private WebElement ourRoomsText;

    @FindBy(how = How.CSS, using = "#location [class='display-5']")
    private WebElement ourLocationHeading;

    @FindBy(how = How.CSS, using = "#location [class='display-5'] + p")
    private WebElement ourLocationText;

    @FindBy(how = How.CSS, using = "#location h3")
    private WebElement contactInformationHeading;

    @FindBy(how = How.CSS, using = "#location h5")
    private List<WebElement> contactInformationSubHeadingList;

    @FindBy(how = How.CSS, using = "#location h5 + p")
    private List<WebElement> contactInformationTextList;

    @FindBy(how = How.CSS, using = "[class='card-body'] h4")
    private WebElement gettingHereHeading;

    @FindBy(how = How.CSS, using = "[class='card-body'] h4 + p")
    private WebElement gettingHereText;

    @FindBy(how = How.CSS, using = "label[for='name']")
    private WebElement contactUsNameLabel;

    @FindBy(how = How.CSS, using = "label[for='email']")
    private WebElement contactUsEmailLabel;

    @FindBy(how = How.CSS, using = "label[for='phone']")
    private WebElement contactUsPhoneLabel;

    @FindBy(how = How.CSS, using = "label[for='subject']")
    private WebElement contactUsSubjectLabel;

    @FindBy(how = How.CSS, using = "label[for='message']")
    private WebElement contactUsDescriptionLabel;

    // text fields
    @FindBy(how = How.XPATH, using = "//div[label[@for='checkin']]//input")
    private WebElement checkInDateTxt;

    @FindBy(how = How.XPATH, using = "//div[label[@for='checkout']]//input")
    private WebElement checkOutDateTxt;

    // buttons
    @FindBy(how = How.CSS, using = "#booking button")
    private WebElement checkAvailabilityBtn;

    // card
    private final By availableRoomCards = By.cssSelector("div.room-card");

    // message entry
    @FindBy(how = How.ID, using = "name")
    private WebElement messageNameTxt;

    @FindBy(how = How.ID, using = "email")
    private WebElement messageEmailTxt;

    @FindBy(how = How.ID, using = "phone")
    private WebElement messagePhoneTxt;

    @FindBy(how = How.ID, using = "subject")
    private WebElement messageSubjectTxt;

    @FindBy(how = How.ID, using = "description")
    private WebElement messageBodyTxt;

    @FindBy(how = How.XPATH, using = "//button[contains(.,'Submit')]")
    private WebElement messageSubmitButton;

    // message success
    private final By messageSuccessTitle = By.cssSelector("[id='contact'] h3");

    @FindBy(how = How.CSS, using = "#contact p:nth-child(2)")
    private WebElement messageGetBackTxt;

    @FindBy(how = How.CSS, using = "#contact p:nth-child(3)")
    private WebElement messageSuccessSubjectTxt;

    @FindBy(how = How.CSS, using = "#contact p:nth-child(4)")
    private WebElement messageSuccessSoonAsPossibleTxt;

    // validation errors - using By class to find the elements due to a timing issue displaying the errors on the page, especially with chrome
    private final By validationErrorMessageListBy = By.cssSelector("[class='alert alert-danger'] p");

    public HomePage(WebDriver driver, WebDriverWait wait, ScenarioContext context) {
        super(driver, wait, context);
    }

    public void loadHomePage(String scenarioName) {
        goToUrl(url);
        assertEquals("Welcome to Shady Meadows B&B", readText(headerText), "Not on home page");

        if (context.contains("isAccessibilityTest") && context.get("isAccessibilityTest", Boolean.class)) {
            Results results = analyzeAccessibility(accessibilityService.createAxeBuilder());
            accessibilityService.generateAxeReport(results, scenarioName, "Home Page");
        }
    }

    public void setCheckInDate(String checkInDate) {
        clearElement(checkInDateTxt);
        writeText(checkInDateTxt, checkInDate);
        writeText(checkInDateTxt, Keys.TAB);
    }

    public void setCheckOutDate(String checkOutDate) {
        clearElement(checkOutDateTxt);
        writeText(checkOutDateTxt, checkOutDate);
        writeText(checkOutDateTxt, Keys.TAB);
    }

    public void clickCheckAvailability() {
        click(checkAvailabilityBtn);
    }

    public void selectRoomType(String roomType) {

        // end the test if we can't find the button.
        boolean found = false;
        int retries = 0;

        // Lots of trouble here with the elements being stale. We need to retry a few times.
        while (!found && retries < 3) {
            try {
                waitForVisibilityOfElements(availableRoomCards);

                List<WebElement> cards = findElements(availableRoomCards);

                for (WebElement card : cards) {
                    String title = card.findElement(By.cssSelector("h5.card-title")).getText();

                    if (title.equalsIgnoreCase(roomType)) {
                        WebElement button = card.findElement(By.cssSelector("[href*='reservation/']"));
                        String href = button.getAttribute("href");

                        // Extract room ID
                        Pattern pattern = Pattern.compile("/reservation/(\\d+)");
                        Matcher matcher = pattern.matcher(href);
                        if (matcher.find()) {
                            context.put("roomIdUnderTest", Integer.parseInt(matcher.group(1)));
                        }

                        click(button);
                        found = true;
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                log.warn("Stale elements detected. Retrying search (attempt {})", retries + 1);
            }
            retries++;
        }

        assertTrue(found, "Unable to locate the book now button for room type: " + roomType);
    }

    public void iSubmitMessage() {
        String fullName = faker.name().fullName();
        context.put("fullNameMessage", fullName);

        String subject = faker.text().text(10);
        context.put("messageSubject", subject);

        writeText(messageNameTxt, fullName);
        writeText(messageEmailTxt, faker.internet().emailAddress() + "xxx");
        writeText(messageSubjectTxt, subject);
        writeText(messagePhoneTxt, dummyPhoneNumber);
        writeText(messageBodyTxt, faker.hitchhikersGuideToTheGalaxy().marvinQuote());
        click(messageSubmitButton);
    }

    public void confirmMessageSent() {
        String thanksText = String.join(" ", "Thanks for getting in touch", context.get("fullNameMessage", String.class) + "!");
        waitForTextToBe(messageSuccessTitle, thanksText);

        assertEquals("We'll get back to you about", readText(messageGetBackTxt), "Get back text not displayed");
        assertEquals(context.get("messageSubject", String.class), readText(messageSuccessSubjectTxt), "subject is incorrect");
        assertEquals("as soon as possible.", readText(messageSuccessSoonAsPossibleTxt), "Soon as possible text is missing");
    }

    public void verifyHomePage() {

        String propertyDescription = "Welcome to Shady Meadows, a delightful Bed & Breakfast nestled in the hills on Newingtonfordburyshire." +
                " A place so beautiful you will never want to leave. All our rooms have comfortable beds and we provide breakfast from " +
                "the locally sourced supermarket. It is a delightful place.";
        assertEquals(propertyDescription, readText(headerMessage), "Header message not displayed");

        // availability section
        assertEquals("Check Availability & Book Your Stay", readText(checkAvailabilityHeader),
                "Check Availability header not displayed");

        // date picker section
        assertEquals("Check In", readText(labelCheckIn), "Check In label not displayed");
        assertEquals("Check Out", readText(labelCheckout), "Check Out label not displayed");

        // our rooms section
        assertEquals("Our Rooms", readText(ourRoomsHeading), "Our Rooms heading not displayed");
        assertEquals("Comfortable beds and delightful breakfast from locally sourced ingredients",
                readText(ourRoomsText), "Our Rooms text not displayed");

        // our location
        assertEquals("Our Location", readText(ourLocationHeading), "Our Location heading not displayed");
        assertEquals("Find us in the beautiful Newingtonfordburyshire countryside", readText(ourLocationText),
                "Our Rooms text not displayed");

        // contact information
        assertEquals("Contact Information", readText(contactInformationHeading), "Contact Information heading not displayed");
        assertEquals("Address", readText(contactInformationSubHeadingList.getFirst()), "Address heading not displayed");
        assertEquals("Shady Meadows B&B, Shadows valley, Newingtonfordburyshire, Dilbery, N1 1AA",
                readText(contactInformationTextList.getFirst()), "Address not displayed");

        assertEquals("Phone", readText(contactInformationSubHeadingList.get(1)), "Phone heading not displayed");
        assertEquals("012345678901", readText(contactInformationTextList.get(1)), "Phone number not displayed");
        assertEquals("Email", readText(contactInformationSubHeadingList.get(2)), "Email heading not displayed");
        assertEquals("fake@fakeemail.com", readText(contactInformationTextList.get(2)), "Email not displayed");

        // getting here
        assertEquals("Getting Here", readText(gettingHereHeading), "Getting Here heading not displayed");
        assertEquals(propertyDescription, readText(gettingHereText), "Getting Here text not displayed");

        // send us a message
        assertEquals("Name", readText(contactUsNameLabel), "Send Us A Message name label not displayed");
        assertEquals("Email", readText(contactUsEmailLabel), "Send Us A Message email label not displayed");
        assertEquals("Phone", readText(contactUsPhoneLabel), "Send Us A Message phone label not displayed");
        assertEquals("Subject", readText(contactUsSubjectLabel), "Send Us A Message subject label not displayed");
        assertEquals("Message", readText(contactUsDescriptionLabel), "Send Us A Message description label not displayed");
    }

    public void validateHomePage() {
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 8);
        assertEquals(8, findElements(validationErrorMessageListBy).size(), "Validation errors not correct amount");

        String subjectErrorMessage = "Subject error message incorrect";
        String phoneErrorMessage = "Phone error message incorrect";

        assertTrue(sharedMethods.doesErrorMessageExistInList("Message must be between 20 and 2000 characters",
                validationErrorMessageListBy), "Message error message incorrect");

        assertTrue(sharedMethods.doesErrorMessageExistInList("Phone may not be blank", validationErrorMessageListBy),
                phoneErrorMessage);

        assertTrue(sharedMethods.doesErrorMessageExistInList("Subject may not be blank", validationErrorMessageListBy),
                subjectErrorMessage);

        assertTrue(sharedMethods.doesErrorMessageExistInList("Name may not be blank", validationErrorMessageListBy),
                "Name error message incorrect");

        assertTrue(sharedMethods.doesErrorMessageExistInList("Message may not be blank", validationErrorMessageListBy),
                "Message error message incorrect");

        assertTrue(sharedMethods.doesErrorMessageExistInList("Email may not be blank", validationErrorMessageListBy),
                "Email error message incorrect");

        assertTrue(sharedMethods.doesErrorMessageExistInList("Subject must be between 5 and 100 characters.",
                validationErrorMessageListBy), subjectErrorMessage);

        assertTrue(sharedMethods.doesErrorMessageExistInList("Phone must be between 11 and 21 characters.",
                validationErrorMessageListBy), phoneErrorMessage);

        writeText(messageNameTxt, "S");
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 7);
        writeText(messagePhoneTxt, "0");
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 7);
        writeText(messageSubjectTxt, "A");
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 5);
        writeText(messageBodyTxt, "A");
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 4);
        writeText(messagePhoneTxt, dummyPhoneNumber);
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 3);
        writeText(messageEmailTxt, faker.internet().emailAddress() + "xxx");
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 2);
        clearAndWriteText(messageEmailTxt, "S");
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 3);
        assertTrue(sharedMethods.doesErrorMessageExistInList("must be a well-formed email address",
                validationErrorMessageListBy), "Email error message incorrect");

        writeText(messageSubjectTxt, faker.text().text(10));
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 2);
        clearAndWriteText(messageEmailTxt, faker.internet().emailAddress() + "xxx");
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 1);
        writeText(messageBodyTxt, faker.hitchhikersGuideToTheGalaxy().marvinQuote());
        click(messageSubmitButton);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 1);
    }
}
