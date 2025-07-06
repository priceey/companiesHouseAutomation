package com.companies.house.cucumber.pages;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.annotations.LazyComponent;
import com.companies.house.context.ScenarioContext;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // Header
    @FindBy(how = How.CSS, using = "h1")
    private WebElement headerText;

    // text fields
    @FindBy(how = How.XPATH, using = "//div[label[@for='checkin']]//input")
    private WebElement checkInDateTxt;

    @FindBy(how = How.XPATH, using = "//div[label[@for='checkout']]//input")
    private WebElement checkOutDateTxt;

    // buttons
    @FindBy(how = How.CSS, using = "#booking button")
    private WebElement checkAvailabilityBtn;

    // card
    @FindBy(how = How.CSS, using = "div.room-card")
    private List<WebElement> availableRoomCards;

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
    @FindBy(how = How.CSS, using = "[id='contact'] h3")
    private WebElement messageSuccessTitle;

    @FindBy(how = How.CSS, using = "#contact p:nth-child(2)")
    private WebElement messageGetBackTxt;

    @FindBy(how = How.CSS, using = "#contact p:nth-child(3)")
    private WebElement messageSuccessSubjectTxt;

    @FindBy(how = How.CSS, using = "#contact p:nth-child(4)")
    private WebElement messageSSuccessSoonAsPossibleTxt;

    public HomePage(WebDriver driver, WebDriverWait wait, ScenarioContext context) {
        super(driver, wait, context);
    }

    public void loadHomePage() {
        goToUrl(url);
        assertEquals("Welcome to Shady Meadows B&B", readText(headerText), "Not on home page");
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
        boolean buttonFound = false;

        for (WebElement element : availableRoomCards) {
            WebElement title = element.findElement(By.cssSelector("h5.card-title"));
            System.out.println(title);
            if (title.getText().equalsIgnoreCase(roomType)) {
                WebElement button = element.findElement(By.cssSelector("[href*='reservation/']"));
                buttonFound = true;
                click(button);
                break;
            }
        }

        assertTrue(buttonFound, "Unable to locate the book now button for room type" + roomType);
    }

    public void iSubmitMessage() {
        String fullName = faker.name().fullName();
        context.put("fullName", fullName);

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
        String thanksText = String.join(" ", "Thanks for getting in touch", context.pop("fullName", String.class) + "!");
        assertEquals(thanksText, readText(messageSuccessTitle), "Success title not matching expected");

        assertEquals("We'll get back to you about", readText(messageGetBackTxt), "Get back text not displayed");
        assertEquals(context.pop("messageSubject", String.class), readText(messageSuccessSubjectTxt), "subject is incorrect");
        assertEquals("as soon as possible.", readText(messageSSuccessSoonAsPossibleTxt), "Soon as possible text is missing");
    }
}
