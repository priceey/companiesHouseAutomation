package com.companies.house.cucumber.pages;

import com.companies.house.context.ScenarioContext;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

@Slf4j
public class AbstractBasePage {

    private static final int CLEAR_WEB_FIELD_MAX_VALUE = 80;
    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final int MAX_WAIT_RETRY = 10;

    public AbstractBasePage(WebDriver driver, WebDriverWait wait, ScenarioContext context) {
        this.driver = driver;
        this.wait = wait;
    }

    @PostConstruct
    private void init() {
        PageFactory.initElements(this.driver, this);
    }

    protected void goToUrl(String url) {
        driver.get(url);
    }

    protected <T> String readText(T elementAttr) {
        waitForElement(elementAttr);
        if (elementAttr instanceof By by) {
            return driver.findElement(by).getText();
        } else if (elementAttr instanceof WebElement) {
            return ((WebElement) elementAttr).getText();
        } else {
            throw new IllegalArgumentException("Unsupported element type: " + elementAttr.getClass().getSimpleName());
        }
    }

    protected <T> void click(T elementAttr) {
        boolean success = reattempt(() -> clickFunction(elementAttr));
        Assertions.assertTrue(success);
    }

    private <T> void clickFunction(T elementAttr) {
        WebElement element;

        if (elementAttr instanceof By by) {
            element = driver.findElement(by);
        } else if (elementAttr instanceof WebElement webElement) {
            element = webElement;
        } else {
            throw new IllegalArgumentException("Unsupported element type: " + elementAttr.getClass().getSimpleName());
        }

        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center', inline: 'nearest'});",
                    element);
            element.click();
        } catch (TimeoutException e) {
            throw new TimeoutException();
        }
    }

    protected <T> void waitForElement(T element) {
        boolean success = reattempt(() -> waitForElementFunction(element));
        Assertions.assertTrue(success);
    }

    private boolean reattempt(Runnable runnable) {
        int attempts = 0;
        while (attempts < MAX_WAIT_RETRY) {
            try {
                runnable.run();
                return true;
            } catch (Exception e) {
                log.debug("Attempt {} of waiting for requested element", attempts);
            }
            attempts++;
        }
        return false;
    }

    private <T> void waitForElementFunction(T elementAttr) {
        ExpectedCondition<WebElement> expectedCondition;
        if (elementAttr instanceof By by) {
            expectedCondition = ExpectedConditions.visibilityOfElementLocated(by);
        } else if (elementAttr instanceof WebElement webElement) {
            expectedCondition = ExpectedConditions.visibilityOf(webElement);
        } else {
            throw new IllegalArgumentException("Unknown elementAttr type: " + elementAttr.getClass());
        }

        WebElement element = wait.until(expectedCondition);
        scrollAndWait(element);
    }

    private void scrollAndWait(WebElement element) {
        // new Actions(driver).moveToElement(element).perform();
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                element
        );
    }

    protected <T> void writeText(T elementAttr, CharSequence text) {
        waitForElement(elementAttr);
        if (elementAttr instanceof By by) {
            driver.findElement(by).sendKeys(text);
        } else if (elementAttr instanceof WebElement webElement) {
            webElement.sendKeys(text);
        } else {
            throw new IllegalArgumentException("Unknown elementAttr type: " + elementAttr.getClass());
        }
    }

    protected <T> void clearElement(T elementAttr) {
        waitForElement(elementAttr);
        WebElement webElement;

        int counter = 0;
        if (elementAttr instanceof By by) {
            webElement = driver.findElement(by);
        } else if (elementAttr instanceof WebElement element) {
            webElement = element;
        } else {
            throw new IllegalArgumentException("Unknown elementAttr type: " + elementAttr.getClass());
        }

        while (true) {
            if (!webElement.getAttribute("value").isEmpty() && counter < CLEAR_WEB_FIELD_MAX_VALUE) {
                Assertions.assertTrue(webElement.isEnabled());
                webElement.sendKeys(Keys.BACK_SPACE);
                counter++;
            } else {
                break;
            }
        }

        Assertions.assertTrue(counter < CLEAR_WEB_FIELD_MAX_VALUE, "Failed to clear field before reaching max value");
    }

    protected <T> void clearAndWriteText(T elementAttr, CharSequence text) {
        clearElement(elementAttr);
        writeText(elementAttr, text);
    }

    protected void waitForNumberOfElementsToBe(By by, int expectedNumberOfElements) {
        wait.until(ExpectedConditions.numberOfElementsToBe(by, expectedNumberOfElements));
    }

    protected List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    protected void waitForVisibilityOfElements(By by){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    protected void waitForTextToBe(By by, String text){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text));
    }

    protected Results analyzeAccessibility(AxeBuilder axeBuilder) {
        return axeBuilder.analyze(driver);
    }
}
