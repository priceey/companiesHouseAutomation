package com.companies.house.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "accessibility")
public class AccessibilityConfig {

    @Value("${accessibility.base.folder}")
    private String reportDirectory;

}
