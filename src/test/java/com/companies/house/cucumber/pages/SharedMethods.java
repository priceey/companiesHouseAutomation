package com.companies.house.cucumber.pages;

import com.companies.house.annotations.LazyComponent;
import com.companies.house.context.ScenarioContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@LazyComponent
public class SharedMethods extends AbstractBasePage{

    public SharedMethods(WebDriver driver, WebDriverWait wait, ScenarioContext context) {
        super(driver, wait, context);
    }

    public boolean doesErrorMessageExistInList(String errorMessage, By validationErrorMessageListBy) {
        boolean found = false;
        List<WebElement> elements = findElements(validationErrorMessageListBy);
        for (WebElement element : elements) {
            if (element.getText().contains(errorMessage)) {
                found = true;
                break;
            }
        }
        return found;
    }
}
