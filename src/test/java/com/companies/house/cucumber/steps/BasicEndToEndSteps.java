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

public class BasicEndToEndSteps {

    @Autowired
    ScenarioContext context;

    @LazyAutowired
    private HomePage homepage;

    @LazyAutowired
    private RoomPage roomPage;

    @Given("I have launched the website under test")
    public void iHaveLaunchedTheWebsiteUnderTest() {
        homepage.loadHomePage();
    }

    @And("^I set the check in date to today$")
    public void iSetTheStartDateTo() {
        LocalDate today = LocalDate.now();
        context.put("checkInDate", today);
        homepage.setCheckInDate(Formatters.DD_MM_YYYY.format(today));
    }

    @And("^I want to stay for (.*) nights$")
    public void iSetTheCheckOutDate(int nights){
        LocalDate checkOutDate = LocalDate.now().plusDays(nights);
        context.put("checkOutDate", checkOutDate);
        homepage.setCheckOutDate(Formatters.DD_MM_YYYY.format(checkOutDate));
    }

    @When("^I click the check availability button and select the (.*) room option$")
    public void iClickCheckAvailabilityAndSelectRoomType(String roomType) {
        homepage.clickCheckAvailability();
        homepage.selectRoomType(roomType);
        context.put("roomType", roomType);
    }

    @And ("I can reserve my room and populate the additional details")
    public void iCanReserveMyRoomAndPopulateAdditionalDetails() {
        String roomType = context.get("roomType", String.class);
        roomPage.correctPageIsDisplayed(roomType);
        roomPage.clickReserveNow();
        roomPage.populateAdditionalDetails();
        roomPage.clickSecondReserveNowButton();
    }

    @And ("the confirmation details are displayed")
    public void theConfirmationDetailsAreDisplayed() {
        roomPage.confirmationDisplayed();
    }
}
