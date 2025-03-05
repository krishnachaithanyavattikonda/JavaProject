package com.dp1.StepDefinitions;

import com.dp1.pages.HomePage;
import com.framework.utils.DriverManager;
import com.framework.utils.TestDataUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class HomePageSteps {
    private HomePage homePage;

    public HomePageSteps() {
        this.homePage = new HomePage(DriverManager.getDriver());
    }


    @When("I check for page displayed")
    public void iCheckForPageDisplayed() {
        homePage.checkForPageDisplay();
    }

    @And("I validate tiles details")
    public void iValidateTilesDetails() {
        homePage.navigateThroughTilesDetails();
    }

}
