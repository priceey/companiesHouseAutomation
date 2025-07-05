package pages;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class AbstractBasePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private static final int MAX_WAIT_RETRY = 10;

    public AbstractBasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
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
            throw new IllegalStateException("Unsupported element type: " + elementAttr.getClass().getSimpleName());
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

    private <T> void waitForElementFunction(T element) {
        ExpectedCondition<WebElement> expectedCondition;
        if (element instanceof By by) {
            expectedCondition = ExpectedConditions.visibilityOfElementLocated(by);
        } else if (element instanceof WebElement webElement) {
            expectedCondition = ExpectedConditions.visibilityOf(webElement);
        } else {
            throw new IllegalArgumentException("Unknown element type: " + element.getClass());
        }

        scrollAndWait(expectedCondition);
    }

    private void scrollAndWait(ExpectedCondition<WebElement> expectedConditions) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);" +
                "window.scrollBy(0, -window.innerHeight /4 );", wait.until(expectedConditions));
    }
}
