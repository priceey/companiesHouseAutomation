package com.companies.house.cucumber.pages;

import com.companies.house.annotations.LazyComponent;
import com.companies.house.context.ScenarioContext;
import com.companies.house.utils.Formatters;
import net.datafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@LazyComponent
public class RoomPage extends AbstractBasePage {

    @Autowired
    private Faker faker;

    @Autowired
    ScenarioContext context;

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
    @FindBy(how = How.CSS, using = "#root-container div.container.my-5 div.col-lg-4 h2")
    private WebElement bookingConfirmedHeader;

    @FindBy(how = How.CSS, using = "#root-container div.container.my-5 div.col-lg-4  p:nth-child(2)")
    private WebElement bookingConfirmedInfoTxt;

    @FindBy(how = How.CSS, using = "#root-container div.container.my-5 div.col-lg-4 strong)")
    private WebElement datesBookedTxt;

    public void correctPageIsDisplayed(String roomType) {
        String expectedHeader = String.join(" ", roomType, "Room");
        assertEquals(expectedHeader, readText(headerText), "Correct page is not displayed in time");
    }

    public void clickReserveNow() {
        click(reserveNowBtn);
    }

    public void populateAdditionalDetails() {
        writeText(firstNameTxt, faker.name().firstName());
        writeText(lastNameTxt, faker.name().lastName());

        // make sure the fake email doesn't exist
        writeText(emailTxt, faker.internet().emailAddress() + "XXX");

        // hard code so its 100% fake.
        writeText(phoneTxt, dummyPhoneNumber);
    }

    public void clickSecondReserveNowButton() {
        click(secondReserveNowBtn);
    }

    public void confirmationDisplayed() {
        assertEquals("Booking Confirmed", readText(bookingConfirmedHeader), "Booking confirmed header missing");
        assertEquals("Your booking has been confirmed for the following dates:", readText(bookingConfirmedInfoTxt), "Booking confirmed information missing.");

        // create the booking dates
        LocalDate checkinDate = context.pop("CheckinDate", LocalDate.class);
        LocalDate checkoutDate = context.pop("CheckOutDate", LocalDate.class);

        String expectedText = String.join(" ", Formatters.YYYY_MM_DD.format(checkinDate), "-", Formatters.YYYY_MM_DD.format(checkoutDate));
        assertEquals(expectedText, readText(datesBookedTxt), "Booking dates aren't displayed");
    }

    public RoomPage(WebDriver driver, WebDriverWait wait, ScenarioContext context) {
        super(driver, wait, context);
    }
}
