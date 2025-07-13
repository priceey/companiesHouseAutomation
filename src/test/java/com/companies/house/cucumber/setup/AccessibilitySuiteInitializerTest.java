package com.companies.house.cucumber.setup;

import com.companies.house.configuration.AccessibilityConfig;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccessibilitySuiteInitializerTest {

    @Autowired
    private AccessibilityConfig accessibilityConfig;

    @BeforeAll
    public void beforeAll() throws IOException {
        String reportDirectory = accessibilityConfig.getReportDirectory();
        Path path = Paths.get(reportDirectory);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        FileUtils.cleanDirectory(path.toFile());
    }

    @Test
    void contextLoads() {
        int i = 0;
        // Forces test discovery
    }
}

