package com.companies.house.cucumber;

import com.companies.house.utils.ScreenshotUtility;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.Base64;

@RequiredArgsConstructor
public class CucumberHooks {

    private final ApplicationContext applicationContext;

    @AfterStep
    public void afterStep(Scenario scenario) {
        WebDriver driver = applicationContext.getBean(WebDriver.class);
        if (scenario.isFailed() && driver != null) {
            String base64Screenshot = ScreenshotUtility.takeScreenshotBase64(driver);
            scenario.attach(
                    Base64.getDecoder().decode(base64Screenshot),
                    "image/png",
                    "screenshot"
            );
        }
    }

    @After()
    public void closeBrowser() {
        WebDriver driver = applicationContext.getBean(WebDriver.class);
        driver.quit();

        ((ConfigurableBeanFactory) applicationContext.getAutowireCapableBeanFactory())
                .getRegisteredScope("webdriverscope")
                .remove("localChromeDriver");
    }
}
