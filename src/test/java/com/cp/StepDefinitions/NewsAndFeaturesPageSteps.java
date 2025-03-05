package com.cp.StepDefinitions;

import com.framework.utils.DriverManager;
import com.cp.pages.NewsAndFeaturesPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class NewsAndFeaturesPageSteps {
    private NewsAndFeaturesPage newsAndFeaturesPage;

    public NewsAndFeaturesPageSteps() {
        newsAndFeaturesPage = new NewsAndFeaturesPage(DriverManager.getDriver());
    }

    @And("I count number of videos older than {string} days")
    public void iCountNumberOfVideosOlderThanDays(String days) {
        newsAndFeaturesPage.countNumberofVideosOlderThan3d(days);
    }
}
