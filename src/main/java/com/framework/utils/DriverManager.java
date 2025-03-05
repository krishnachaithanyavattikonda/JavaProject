package com.framework.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import com.framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Map;

public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            setDriver(System.getProperty("browser", "chrome"));
        }
        return driver.get();
    }

    public static void setDriver(String browser) {
        LogUtil.info("Initializing WebDriver for: " + browser);

        if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = getFirefoxOptions();
            driver.set(new FirefoxDriver(options));
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = getChromeOptions();
            driver.set(new ChromeDriver(options));
        }

        LogUtil.info("WebDriver initialized successfully.");

        // Dynamically fetch the environment
        String env = System.getProperty("testEnv", ConfigReader.getProperty("environment"));
        String envUrl = ConfigReader.getProperty(env + ".baseURL");

        if (envUrl == null || envUrl.isEmpty()) {
            throw new RuntimeException("🚨 Environment URL is missing! Check testEnv parameter.");
        }

        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get().manage().window().maximize();
        driver.get().navigate().to(envUrl);

        LogUtil.info("🌍 Running tests on environment: " + env + " - URL: " + envUrl);

        JavascriptExecutor js = (JavascriptExecutor) driver.get();
        js.executeScript("document.body.style.zoom='0.8'");
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--force-device-scale-factor=0.8");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("start-maximized");
        options.addArguments("--disable-extensions");
        return options;
    }
    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();

        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("privacy.popups.showBrowserMessage", false);
        options.addPreference("dom.disable_open_during_load", true);
        options.addPreference("privacy.trackingprotection.enabled", true);
        options.addPreference("dom.webdriver.enabled", false);
        options.addPreference("extensions.enabledScopes", 0);
        options.addPreference("layout.css.devPixelsPerPx", "0.8");
        options.addArguments("--start-maximized");

        return options;
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
