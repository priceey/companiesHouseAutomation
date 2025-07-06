package com.companies.house.cucumber;

import io.cucumber.java.After;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public class CucumberHooks {

    private final ApplicationContext applicationContext;

    @After()
    public void closeBrowser() {
        WebDriver driver = applicationContext.getBean(WebDriver.class);
        driver.quit();
    }
}
