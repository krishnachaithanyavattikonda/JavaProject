package com.framework.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;
import java.util.function.BooleanSupplier;

public class SeleniumUtil {
    private static final int DEFAULT_TIMEOUT = 10;  // Default timeout in seconds
    private static final int POLLING_INTERVAL = 1000; // Polling every 500ms
    private static WebDriver driver;
    private static WebDriverWait waitDriver;
    private static Actions actions;

    private static WebDriver getDriver() {
        if(driver==null){
            driver=DriverManager.getDriver();
        }
        return driver; // Retrieve WebDriver instance dynamically
    }

    private static WebDriverWait getWait() {
        if(waitDriver==null){
            waitDriver=new WebDriverWait(getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
        }
        return waitDriver;
    }

    private static Actions getActions() {
        if(actions==null){
            actions=new Actions(getDriver());
        }
        return actions;
    }

    public static void sendKeys(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    public static void click(WebElement element) {
        waitForElementToBeVisible(element);
        waitForElementToBeClickable(element);
        element.click();
    }

    public static String getText(WebElement element) {
        waitForElementToBeVisible(element);
        return element.getText();
    }

    public static String getAttribute(WebElement element, String attribute) {
        waitForElementToBeVisible(element);
        return element.getAttribute(attribute);
    }

    public static boolean isElementDisplayed(WebElement element) {
        try {
            waitForElementToBeVisible(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private static void waitForElementToBeVisible(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    private static void waitForElementToBeClickable(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    private static void waitForElementToBeInvisible(By locator) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    private static void waitForPresenceOfElement(By locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private static void waitForFrameAndSwitchToIt(String frameName) {
        getWait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
        getDriver().switchTo().frame(frameName);
    }

    private static void waitForAlertToBePresent() {
        getWait().until(ExpectedConditions.alertIsPresent());
    }


    private static void waitForElementWithFluentWait(WebElement element) {
        new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofMillis(POLLING_INTERVAL))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    private static void waitForElementToBeClickableWithFluentWait(WebElement element) {
        new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofMillis(POLLING_INTERVAL))
                .ignoring(ElementNotInteractableException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForPageLoad() {
        new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofMillis(POLLING_INTERVAL))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
    }
    public static void waitForCondition(BooleanSupplier condition, int timeoutInSeconds) {
        new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class)
                .until(d -> condition.getAsBoolean());
    }


    public static void hoverOverElement(WebElement element) {
        waitForElementToBeVisible(element);
        scrollIntoView(element);
        getActions().moveToElement(element).perform();
    }

    public static void rightClick(WebElement element) {
        waitForElementToBeVisible(element);
        getActions().contextClick(element).perform();
    }

    public static void doubleClick(WebElement element) {
        waitForElementToBeVisible(element);
        getActions().doubleClick(element).perform();
    }

    public static void dragAndDrop(WebElement source, WebElement target) {
        getActions().dragAndDrop(source, target).perform();
    }

    public static void sendKeysWithActions(WebElement element, String text) {
        waitForElementToBeVisible(element);
        getActions().moveToElement(element).click().sendKeys(text).perform();
    }


    public static void clickWithJS(WebElement element) {
        waitForElementToBeVisible(element);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
    }

    public static void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToBottom() {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }


    public static void acceptAlert() {
        waitForAlertToBePresent();
        getDriver().switchTo().alert().accept();
    }

    public static void dismissAlert() {
        waitForAlertToBePresent();
        getDriver().switchTo().alert().dismiss();
    }

    public static String getAlertText() {
        waitForAlertToBePresent();
        return getDriver().switchTo().alert().getText();
    }

    public static void sendKeysToAlert(String text) {
        waitForAlertToBePresent();
        getDriver().switchTo().alert().sendKeys(text);
    }


    public static void selectByVisibleText(WebElement element, String text) {
        waitForElementToBeVisible(element);
        Select select = new Select(element);
        select.selectByVisibleText(text);
    }

    public static void selectByValue(WebElement element, String value) {
        waitForElementToBeVisible(element);
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public static void selectByIndex(WebElement element, int index) {
        waitForElementToBeVisible(element);
        Select select = new Select(element);
        select.selectByIndex(index);
    }

    public static List<WebElement> getAllDropdownOptions(WebElement element) {
        waitForElementToBeVisible(element);
        Select select = new Select(element);
        return select.getOptions();
    }


    public static void switchToFrameByIndex(int index) {
        getDriver().switchTo().frame(index);
    }

    public static void switchToFrameByName(String name) { waitForFrameAndSwitchToIt(name); }

    public static void switchToFrameByElement(WebElement frameElement) {
        getDriver().switchTo().frame(frameElement);
    }

    public static void switchToDefaultContent() {
        getDriver().switchTo().defaultContent();
    }


    public static void switchToWindow(String windowHandle) {
        getDriver().switchTo().window(windowHandle);
    }

    public static void switchToNewWindow() {
        for (String windowHandle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(windowHandle);
        }
    }

    public static void closeCurrentWindow() {
        getDriver().close();
    }

    public static void closeAllWindowsExceptMain() {
        String mainHandle = getDriver().getWindowHandle();
        for (String handle : getDriver().getWindowHandles()) {
            if (!handle.equals(mainHandle)) {
                getDriver().switchTo().window(handle);
                getDriver().close();
            }
        }
        getDriver().switchTo().window(mainHandle);
    }
}
