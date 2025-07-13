package com.companies.house.cucumber.steps;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.context.ScenarioContext;
import com.companies.house.cucumber.pages.HomePage;
import com.companies.house.cucumber.pages.RoomPage;
import com.companies.house.utils.Formatters;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class BasicEndToEndSteps {

    @Autowired
    ScenarioContext context;

    @LazyAutowired
    private HomePage homepage;

    @LazyAutowired
    private RoomPage roomPage;

    @Given("I have launched the website under test")
    public void iHaveLaunchedTheWebsiteUnderTest() {
        context.put("isAccessibilityTest", false);
        homepage.loadHomePage(context.getScenario().getName());
    }

    @Given("I have launched the website under test and have enabled accessibility testing")
    public void iHaveLaunchedTheWebsiteUnderTestAndHaveEnabledAccessibilityTesting() {
        context.put("isAccessibilityTest", true);
        homepage.loadHomePage(context.getScenario().getName());
    }

    @And("^I set the check in date to today$")
    public void iSetTheStartDateTo() {
        LocalDate today = LocalDate.now();
        context.put("checkInDate", today);
        homepage.setCheckInDate(Formatters.DD_MM_YYYY.format(today));
    }

    @And("^I want to stay for (.*) nights$")
    public void iSetTheCheckOutDate(int nights) {
        LocalDate checkinDate = context.get("checkInDate", LocalDate.class);
        LocalDate checkOutDate = checkinDate.plusDays(nights);
        context.put("checkOutDate", checkOutDate);
        context.put("nights", nights);
        homepage.setCheckOutDate(Formatters.DD_MM_YYYY.format(checkOutDate));
    }

    @And("I set a random check in date")
    public void setRandomCheckInDate() {
        int days = ThreadLocalRandom.current().nextInt(5, 365);
        LocalDate checkInDate = LocalDate.now().plusDays(days);
        context.put("checkInDate", checkInDate);
        homepage.setCheckInDate(Formatters.DD_MM_YYYY.format(checkInDate));
    }

    @When("^I click the check availability button and select the correct room option$")
    public void iClickCheckAvailabilityAndSelectRoomType() {
        homepage.clickCheckAvailability();
        String roomType = context.get("roomType", String.class);
        homepage.selectRoomType(roomType);
        context.put("roomType", roomType);
    }

    @And("I can reserve my room and populate the additional details")
    public void iCanReserveMyRoomAndPopulateAdditionalDetails() {
        String roomType = context.get("roomType", String.class);
        roomPage.correctPageIsDisplayed(roomType, context.getScenario().getName());
        roomPage.clickReserveNow();
        roomPage.populateAdditionalDetails();
        roomPage.clickSecondReserveNowButton();
    }

    @And("the confirmation details are displayed")
    public void theConfirmationDetailsAreDisplayed() {
        roomPage.confirmationDisplayed();
    }

    @And("^I want to book a (.*) room$")
    public void iWantToBookASingleRoom(String roomType) {
        context.put("roomType", roomType);
    }
}
