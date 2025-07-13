package com.companies.house.service;

import com.companies.house.configuration.AccessibilityConfig;
import com.deque.html.axecore.results.Results;
import com.deque.html.axecore.selenium.AxeBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@Slf4j
@Component
public class AccessibilityService {

    private final AccessibilityConfig accessibilityConfig;

    @Autowired
    public AccessibilityService(AccessibilityConfig accessibilityConfig) {
        this.accessibilityConfig = accessibilityConfig;
    }

    public AxeBuilder createAxeBuilder() {
        return new AxeBuilder();
    }

    public void generateAxeReport(Results results, String scenarioName, String pageName) {
            try {
                String baseReportDirectory = accessibilityConfig.getReportDirectory();
                ObjectMapper objMapper = new ObjectMapper();
                String json = objMapper.writeValueAsString(results);
                String scenarioReportDirectory = Paths.get(baseReportDirectory,
                        scenarioName.replace(" ", "_"), pageName.replace(" ", "_")).toString();

                Files.createDirectories(Paths.get(scenarioReportDirectory));
                Path reportPath = Paths.get(scenarioReportDirectory, "axe-results.json");
                Files.write(reportPath, Collections.singleton(json));

        } catch (IOException e) {
            log.error("Failed to generate accessibility report for scenario: {}", scenarioName);
        }
    }
}
