package com.companies.house.configuration;


import com.companies.house.annotations.LazyConfiguration;
import com.companies.house.annotations.WebDriverScopedBean;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Primary;

@LazyConfiguration
public class WebdriverConfig {

    @WebDriverScopedBean
    @ConditionalOnMissingBean
    @Primary
    public WebDriver remoteChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        return new ChromeDriver(options);
    }
}
