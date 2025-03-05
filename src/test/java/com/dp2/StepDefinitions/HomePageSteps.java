package com.dp2.StepDefinitions;

import com.dp2.pages.HomePage;
import com.framework.utils.DriverManager;
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

    @And("I validate footer links and write to csv")
    public void iValidateFooterLinksAndWriteToCsv() {
        homePage.writeFooterLinksToCSV();
    }
}
