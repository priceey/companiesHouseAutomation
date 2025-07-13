package com.companies.house.cucumber.steps;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.cucumber.pages.HomePage;
import com.companies.house.cucumber.pages.RoomPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ValidationSteps {

    @LazyAutowired
    private HomePage homepage;

    @LazyAutowired
    private RoomPage roomPage;

    @When("I can verify and validate the home page")
    public void iCanVerifyAndValidateTheHomePage() {
        homepage.verifyHomePage();
        homepage.validateHomePage();
    }

    @Then("I can verify and validate the room page")
    public void iCanVerifyAndValidateTheRoomPage() {
        roomPage.verifyRoomPage();
        roomPage.validateRoomPage();
    }
}
