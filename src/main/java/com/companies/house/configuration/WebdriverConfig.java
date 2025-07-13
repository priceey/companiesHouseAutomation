package com.companies.house.configuration;

import com.companies.house.annotations.LazyConfiguration;
import com.companies.house.annotations.WebDriverScopedBean;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@LazyConfiguration
public class WebdriverConfig {

    private static final String BROWSER = "browser";

    @WebDriverScopedBean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = BROWSER, havingValue = "chrome")
    public WebDriver localChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        return new ChromeDriver(options);
    }

    @WebDriverScopedBean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = BROWSER, havingValue = "firefox")
    public WebDriver localFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920,height=1080");
        return new FirefoxDriver(options);
    }

    @WebDriverScopedBean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = BROWSER, havingValue = "edge")
    public WebDriver localEdgeDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("windowsize=1920,1080");
        return new EdgeDriver(options);
    }
}
