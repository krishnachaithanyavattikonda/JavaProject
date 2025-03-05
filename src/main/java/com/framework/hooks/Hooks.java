package com.framework.hooks;

import com.framework.config.ConfigReader;
import com.framework.utils.DriverManager;
import com.framework.utils.LogUtil;
import com.framework.utils.ReportUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.FileWriter;
import java.io.IOException;

public class Hooks {

    private static boolean isLogCleared = false;

    @BeforeAll
    public static void clearLogsOnce() throws IOException {
        if (!isLogCleared) {
            clearLogFile();
            isLogCleared = true;
        }
    }
    @Before
    public void setup() {
        String browser = System.getProperty("browser", ConfigReader.getProperty("browser"));
        LogUtil.info("Running tests on: " + browser);
        LogUtil.info("Starting new test scenario...");
        DriverManager.setDriver(browser);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed() || ConfigReader.getProperty("screenshotMode").equalsIgnoreCase("all")) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            ReportUtil.attachScreenshot(screenshot);
        }
        LogUtil.info("Test scenario finished, closing browser.");
        if (DriverManager.getDriver() != null) {
            DriverManager.quitDriver();
        }
    }

    private static void clearLogFile() throws IOException {
        String logFilePath = "logs/application.log"; // Update this path if needed
        try (FileWriter fileWriter = new FileWriter(logFilePath, false)) {
            fileWriter.write("");
            System.out.println("✅ Log file cleared before test execution.");
        } catch (IOException e) {
            System.err.println("🚨 Failed to clear log file: " + e.getMessage());
        }
    }
}
