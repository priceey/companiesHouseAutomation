package com.companies.house.cucumber.hooks;

import com.companies.house.annotations.LazyAutowired;
import com.companies.house.configuration.AccessibilityConfig;
import com.companies.house.context.ScenarioContext;
import com.companies.house.cucumber.api.rooms.RoomsApiCalls;
import com.companies.house.utils.ScreenshotUtility;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
public class CucumberHooks {

    private static boolean cleaned = false;
    private final ApplicationContext applicationContext;

    @Autowired
    private ScenarioContext scenarioContext;

    @Autowired
    private AccessibilityConfig accessibilityConfig;

    @Autowired
    private ScenarioContext context;

    @LazyAutowired
    private RoomsApiCalls roomsApiCalls;

    @Before
    public void beforeScenario(Scenario scenario) {
        scenarioContext.setScenario(scenario);

        // clean up the accessibility report directory at the start of each run
        try {
            if (!cleaned) {
                File dir = new File(accessibilityConfig.getReportDirectory());
                FileUtils.forceMkdir(dir);
                FileUtils.cleanDirectory(dir);
                cleaned = true;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        WebDriver driver = applicationContext.getBean(WebDriver.class);
        if (scenario.isFailed()) {
            String base64Screenshot = ScreenshotUtility.takeScreenshotBase64(driver);
            scenario.attach(
                    Base64.getDecoder().decode(base64Screenshot),
                    "image/png",
                    "screenshot"
            );
        }
    }

    @After
    public void afterScenario() {
        // Cleanup created product if any created
        if (context.contains("roomId")) {
            roomsApiCalls.deleteRoom(context.get("roomId", Integer.class));
        }

        // Clean up browser
        WebDriver driver = applicationContext.getBean(WebDriver.class);
        driver.quit();

        ((ConfigurableBeanFactory) applicationContext.getAutowireCapableBeanFactory())
                .getRegisteredScope("webdriverscope")
                .remove("localChromeDriver");
    }
}
