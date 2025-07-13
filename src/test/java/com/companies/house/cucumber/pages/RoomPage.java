package com.companies.house.cucumber.pages;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.annotations.LazyComponent;
import com.companies.house.context.ScenarioContext;
import com.companies.house.cucumber.api.rooms.RoomsApiCalls;
import com.companies.house.cucumber.api.rooms.data.RoomDataObject;
import com.companies.house.service.AccessibilityService;
import com.companies.house.utils.Formatters;
import com.deque.html.axecore.results.Results;
import net.datafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@LazyComponent
public class RoomPage extends AbstractBasePage {

    @Autowired
    private Faker faker;

    @Autowired
    ScenarioContext context;

    @Autowired
    private AccessibilityService accessibilityService;

    @LazyAutowired
    private SharedMethods sharedMethods;

    @Value("${dummy.phone.number}")
    private String dummyPhoneNumber;

    // Header
    @FindBy(how = How.CSS, using = "h1")
    private WebElement headerText;

    // button
    @FindBy(how = How.ID, using = "doReservation")
    private WebElement reserveNowBtn;

    // the second reserve now button when extra details are displayed
    @FindBy(how = How.XPATH, using = "//*[text()='Reserve Now']")
    private WebElement secondReserveNowBtn;

    // text fields
    @FindBy(how = How.NAME, using = "firstname")
    private WebElement firstNameTxt;

    @FindBy(how = How.NAME, using = "lastname")
    private WebElement lastNameTxt;

    @FindBy(how = How.NAME, using = "email")
    private WebElement emailTxt;

    @FindBy(how = How.NAME, using = "phone")
    private WebElement phoneTxt;

    // reservation completed fields
    private final By bookingConfirmedHeader = By.cssSelector("#root-container div.container.my-5 div.col-lg-4 h2");

    @FindBy(how = How.CSS, using = "#root-container div.container.my-5 div.col-lg-4  p:nth-child(2)")
    private WebElement bookingConfirmedInfoTxt;

    @FindBy(how = How.CSS, using = "#root-container div.container.my-5 div.col-lg-4 strong")
    private WebElement datesBookedTxt;

    // labels and headings
    @FindBy(how = How.CSS, using = "[class='badge bg-success']")
    private WebElement accessibleBadge;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(3) > h2")
    private WebElement roomDescriptionHeading;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(4) h2")
    private WebElement roomFeaturesHeading;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(5) > h2")
    private WebElement roomPoliciesHeading;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(5) div div:nth-child(2) h3")
    private WebElement houseRulesHeading;

    @FindBy(how = How.CSS, using = "[class='mb-4'] h2 + p")
    private WebElement roomDescriptionText;

    @FindBy(how = How.CSS, using = "[class='bi bi-tv amenity-icon me-3'] + span")
    private WebElement tvText;

    @FindBy(how = How.CSS, using = "[class='bi bi-speaker amenity-icon me-3'] + span")
    private WebElement radioText;

    @FindBy(how = How.CSS, using = "[class='bi bi-safe amenity-icon me-3'] + span")
    private WebElement safeText;

    @FindBy(how = How.CSS, using = "#root-container form div.card.bg-light.border-0.mb-4 h3")
    private WebElement priceSummaryHeading;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(5) div:nth-child(1) div div h3")
    private WebElement checkInOutLabel;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(1) > div > div > ul li:nth-child(1) div")
    private WebElement checkInLabel;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(1) > div > div > ul > li:nth-child(2) div")
    private WebElement checkOutLabel;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(1) > div > div > ul > li:nth-child(3) div")
    private WebElement earlyLateLabel;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(5) div div:nth-child(2) li:nth-child(1) div")
    private WebElement noSmokingLabel;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(5) div div:nth-child(2) li:nth-child(2) div")
    private WebElement noPartiesLabel;

    @FindBy(how = How.CSS, using = "#root-container div:nth-child(5) div:nth-child(2) > div > div > ul > li:nth-child(3) > div")
    private WebElement noPetsLabel;

    @FindBy(how = How.CSS, using = "#root-container div.container.my-5 div.col-lg-4 h2")
    private WebElement bookThisRoomHeading;

    @FindBy(how = How.CSS, using = "[class='d-flex align-items-baseline mb-4']")
    private WebElement pricePerNightText;

    @FindBy(how = How.CSS, using = "#root-container div.card.bg-light.border-0.mb-4 div:nth-child(2) span:nth-child(1)")
    private WebElement priceSummaryNightsText;

    @FindBy(how = How.CSS, using = "#root-container div.card.bg-light.border-0.mb-4 div:nth-child(2) span:nth-child(2)")
    private WebElement basicRoomPriceText;

    @FindBy(how = How.CSS, using = "#root-container div.card.bg-light.border-0.mb-4 div:nth-child(3) span:nth-child(1)")
    private WebElement cleaningFeeText;

    @FindBy(how = How.CSS, using = "#root-container div.card.bg-light.border-0.mb-4 div:nth-child(3) span:nth-child(2)")
    private WebElement cleaningFeeValueText;

    @FindBy(how = How.CSS, using = "#root-container div.card.bg-light.border-0.mb-4 div:nth-child(4) span:nth-child(1)")
    private WebElement serviceFeeText;

    @FindBy(how = How.CSS, using = "#root-container div.card.bg-light.border-0.mb-4 div:nth-child(4) span:nth-child(2)")
    private WebElement serviceFeeValueText;

    @FindBy(how = How.CSS, using = "#root-container  div.d-flex.justify-content-between.fw-bold span:nth-child(1)")
    private WebElement totalText;

    @FindBy(how = How.CSS, using = "#root-container  div.d-flex.justify-content-between.fw-bold span:nth-child(2)")
    private WebElement totalValueText;

    private final By validationErrorMessageListBy = By.cssSelector("[class='alert alert-danger'] li");

    @Autowired
    private RoomsApiCalls roomsApiCalls;

    public void correctPageIsDisplayed(String roomType, String scenarioName) {
        String expectedHeader = String.join(" ", roomType, "Room");
        assertEquals(expectedHeader, readText(headerText), "Correct page is not displayed in time");
        if (context.contains("isAccessibilityTest") && context.get("isAccessibilityTest", Boolean.class)) {
            Results results = analyzeAccessibility(accessibilityService.createAxeBuilder());
            accessibilityService.generateAxeReport(results, scenarioName, "Room Page");
        }
    }

    public void clickReserveNow() {
        click(reserveNowBtn);
    }

    public void populateAdditionalDetails() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        writeText(firstNameTxt, firstName);
        writeText(lastNameTxt, lastName);

        context.put("firstNameSubmit", firstName);
        context.put("lastNameSubmit", lastName);

        // make sure the fake email doesn't exist
        writeText(emailTxt, faker.internet().emailAddress() + "XXX");

        // hard code so its 100% fake.
        writeText(phoneTxt, dummyPhoneNumber);
    }

    public void clickSecondReserveNowButton() {
        click(secondReserveNowBtn);
    }

    public void confirmationDisplayed() {

        waitForTextToBe(bookingConfirmedHeader, "Booking Confirmed");
        assertEquals("Your booking has been confirmed for the following dates:", readText(bookingConfirmedInfoTxt), "Booking confirmed information missing.");

        // create the booking dates
        LocalDate checkinDate = context.pop("checkInDate", LocalDate.class);
        LocalDate checkoutDate = context.pop("checkOutDate", LocalDate.class);

        String expectedText = String.join(" ", Formatters.YYYY_MM_DD_DASHED.format(checkinDate), "-", Formatters.YYYY_MM_DD_DASHED.format(checkoutDate));
        assertEquals(expectedText, readText(datesBookedTxt), "Booking dates aren't displayed");
    }

    public void verifyRoomPage() {
        assertEquals("Room Description", readText(roomDescriptionHeading), "Room description is incorrect");

        // get the room details
        RoomDataObject roomIdUnderTest = roomsApiCalls.getRoomDetails(context.get("roomIdUnderTest", Integer.class));
        if (roomIdUnderTest.isAccessible()) {
            assertEquals("Accessible", readText(accessibleBadge), "Room accessibility is incorrect");
        }
        assertEquals(roomIdUnderTest.getDescription().trim(), readText(roomDescriptionText), "Room description text is incorrect");

        // features
        assertEquals("Room Features", readText(roomFeaturesHeading), "Features heading is incorrect");

        for (String feature : roomIdUnderTest.getFeatures()) {
            switch (feature) {
                case "TV":
                    assertEquals("TV", readText(tvText), "TV is missing");
                    break;
                case "Radio":
                    assertEquals("Radio", readText(radioText), "Radio is missing");
                    break;
                case "Safe":
                    assertEquals("Safe", readText(safeText), "Safe is missing");
            }
        }

        //policies
        assertEquals("Room Policies", readText(roomPoliciesHeading), "Policies heading is incorrect");
        assertEquals("Check-in & Check-out", readText(checkInOutLabel), "Check-in & Check-out heading is incorrect");
        assertEquals("Check-in: 3:00 PM - 8:00 PM", readText(checkInLabel), "Check-in label is incorrect");
        assertEquals("Check-out: By 11:00 AM", readText(checkOutLabel), "Check-out heading is incorrect");
        assertEquals("Early/Late: By arrangement", readText(earlyLateLabel), "Early/Late label is incorrect");

        // house rules
        assertEquals("House Rules", readText(houseRulesHeading), "House rules heading is incorrect");
        assertEquals("No smoking", readText(noSmokingLabel), "No smoking label is incorrect");
        assertEquals("No parties or events", readText(noPartiesLabel), "No parties label is incorrect");
        assertEquals("Pets allowed (restrictions apply)", readText(noPetsLabel), "No pets label is incorrect");

        // book room
        assertEquals("Book This Room", readText(bookThisRoomHeading), "Book this room heading is incorrect");
        assertEquals(String.join(" ", "£" + roomIdUnderTest.getRoomPrice(), "per night"),
                readText(pricePerNightText).replaceAll("\\s+", " "), "Room price is incorrect");

        // price summary section
        assertEquals("Price Summary", readText(priceSummaryHeading), "Price summary heading is incorrect");

        String expDurationAndPrice = String.join(" ", "£" + roomIdUnderTest.getRoomPrice(), "x",
                String.valueOf(context.get("nights", Integer.class)), "nights");

        assertEquals(expDurationAndPrice, readText(priceSummaryNightsText), "Price summary nights text is incorrect");

        // calculate correct nightly cost
        int roomPrice = roomIdUnderTest.getRoomPrice();
        int nights = context.get("nights", Integer.class);
        int roomCost = roomPrice * nights;
        String expectedNightlyCost = String.join(" ", "£" + roomCost);
        assertEquals(expectedNightlyCost, readText(basicRoomPriceText), "Price summary nights text is incorrect");

        assertEquals("Cleaning fee", readText(cleaningFeeText), "Cleaning fee heading is incorrect");
        assertEquals("£25", readText(cleaningFeeValueText), "Cleaning fee text is incorrect");

        assertEquals("Service fee", readText(serviceFeeText), "Service fee heading is incorrect");
        assertEquals("£15", readText(serviceFeeValueText), "Service fee text is incorrect");

        int totalCost = roomCost + 25 + 15;

        assertEquals("Total", readText(totalText), "Total heading is incorrect");
        assertEquals("£" + totalCost, readText(totalValueText), "Total text is incorrect");
    }

    public void validateRoomPage() {
        // generate error messages
        click(reserveNowBtn);
        click(secondReserveNowBtn);

        waitForNumberOfElementsToBe(validationErrorMessageListBy, 7);

        assertTrue(sharedMethods.doesErrorMessageExistInList("must not be empty", validationErrorMessageListBy),
                "CHANGE THIS");

        assertTrue(sharedMethods.doesErrorMessageExistInList("Lastname should not be blank", validationErrorMessageListBy),
                "Last name error message is missing");

        assertTrue(sharedMethods.doesErrorMessageExistInList("size must be between 11 and 21", validationErrorMessageListBy),
                "WAHT IS THIS ERROR MESSAGE? CHANGE THIS");

        assertTrue(sharedMethods.doesErrorMessageExistInList("Firstname should not be blank", validationErrorMessageListBy),
                "Firstname should not be blank");

        assertTrue(sharedMethods.doesErrorMessageExistInList("size must be between 3 and 30", validationErrorMessageListBy),
                "WHAT IS THIS ERROR MESSAGE? CHANGE THIS");

        assertTrue(sharedMethods.doesErrorMessageExistInList("must not be empty", validationErrorMessageListBy),
                "CHANGE THIS");

        assertTrue(sharedMethods.doesErrorMessageExistInList("size must be between 3 and 18", validationErrorMessageListBy),
                "WHAT IS THIS ERROR MESSAGE? CHANGE THIS");

        writeText(firstNameTxt, "A");
        click(secondReserveNowBtn);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 6);
        clearAndWriteText(firstNameTxt, faker.name().firstName());
        click(secondReserveNowBtn);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 5);
        writeText(lastNameTxt, "A");
        click(secondReserveNowBtn);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 4);
        clearAndWriteText(lastNameTxt, faker.name().lastName());
        click(secondReserveNowBtn);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 3);
        writeText(phoneTxt, "0");
        click(secondReserveNowBtn);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 2);
        clearAndWriteText(phoneTxt, dummyPhoneNumber);
        click(secondReserveNowBtn);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 1);
        writeText(emailTxt, "A");
        click(secondReserveNowBtn);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 1);
        clearAndWriteText(emailTxt, faker.internet().emailAddress() + "xxx");
        click(secondReserveNowBtn);
        waitForNumberOfElementsToBe(validationErrorMessageListBy, 0);
    }

    public RoomPage(WebDriver driver, WebDriverWait wait, ScenarioContext context) {
        super(driver, wait, context);
    }
}
