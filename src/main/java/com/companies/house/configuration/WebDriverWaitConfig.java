package com.companies.house.configuration;

import com.companies.house.annotations.LazyConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@LazyConfiguration
public class WebDriverWaitConfig {

    @Value("5s")
    private Duration timeout;

    @Bean
    public WebDriverWait webDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, timeout);
    }
}
