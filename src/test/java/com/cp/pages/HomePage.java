package com.cp.pages;

import com.framework.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//div[@role='dialog']//div[@class='p-2 absolute right-3 hover:cursor-pointer']")
    private WebElement cursorPtr;

    @FindBy(xpath = "//nav[@aria-label='header-primary-menu']//span[text()='Shop']")
    private WebElement menuItemShop;

    @FindBy(xpath = "//nav[@aria-label='header-secondary-menu']//span[text()='...']")
    private  WebElement threeDots;

    @FindBy(xpath = "//nav[@aria-label='header-primary-menu']//span[text()='Shop']/parent::a/parent::li//a[@title=\"Men's\"]")
    private WebElement shopMenuItemMens;

    @FindBy(xpath = "//nav[@aria-label='header-secondary-menu']//a[text()='News & Features']")
    private WebElement newsandfeaturesItem;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void closeCursorAvailable(){
        if(isElementDisplayed(cursorPtr)){
            click(cursorPtr);
        }
    }

    public void hoverShopMenuItem() {
        waitForPageLoad();
        hoverOverElement(menuItemShop);
    }

    public void hoverOnThreeDots() {
        waitForPageLoad();
        hoverOverElement(threeDots);
    }

    public void clickShopMenuMensItem(){
        waitForPageLoad();
        click(shopMenuItemMens);
    }

    public void clickNewsAndFeaturesItem(){
        waitForPageLoad();
        click(newsandfeaturesItem);
    }

    public void switchToNewPage(){
        switchToNewWindow();
    }
}
