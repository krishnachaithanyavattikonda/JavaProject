package com.cp.StepDefinitions;

import com.framework.utils.DriverManager;
import com.cp.pages.HomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomePageSteps {

    private HomePage homePage;

    public HomePageSteps() {
        homePage= new HomePage(DriverManager.getDriver());
    }

    @When("I Click on {string} Menu")
    public void i_click_on_menu(String string) {
        homePage.hoverShopMenuItem();
    }

    @When("I close info pointer If Available")
    public void iCloseInfoPointerIfAvailable() {
        homePage.closeCursorAvailable();
    }

    @Then("I click on Men's Item from Shop Menu")
    public void iClickOnMenSItemFromShopMenu() {
        homePage.clickShopMenuMensItem();
    }

    @And("Switch to new page for Men's shopping items")
    public void switchToNewPageForMenSShoppingItems() {
        homePage.switchToNewPage();
    }

    @And("I hover over three dots in home page")
    public void iHoverOverThreeDotsInHomePage() {
        homePage.hoverOnThreeDots();
    }

    @And("I click on NewsAndFeatures Item from menu")
    public void iClickOnNewsAndFeaturesItemFromMenu() {
        homePage.clickNewsAndFeaturesItem();
    }
}
