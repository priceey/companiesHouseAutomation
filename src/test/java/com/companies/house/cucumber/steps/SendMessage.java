package com.companies.house.cucumber.steps;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.context.ScenarioContext;
import com.companies.house.cucumber.pages.AbstractBasePage;
import com.companies.house.cucumber.pages.HomePage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SendMessage extends AbstractBasePage {

    @LazyAutowired
    private HomePage homePage;

    @When("I submit a message")
    public void iSubmitMessage() {
        homePage.iSubmitMessage();
    }

    @Then("the message sent successfully text is displayed")
    public void theMessageSentSuccessfullyTextIsDisplayed() {
        homePage.confirmMessageSent();
    }

    public SendMessage(WebDriver driver, WebDriverWait wait, ScenarioContext context) {
        super(driver, wait, context);
    }
}
