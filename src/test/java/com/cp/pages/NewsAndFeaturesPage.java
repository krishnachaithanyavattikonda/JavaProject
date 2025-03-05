package com.cp.pages;

import com.framework.base.BasePage;
import com.framework.utils.LogUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NewsAndFeaturesPage extends BasePage {

    @FindBy(xpath = "//h3[text()='VIDEOS']/parent::div/following::div//li//time//span")
    private List<WebElement> videosList;

    public NewsAndFeaturesPage(WebDriver driver) {
        super(driver);
    }

    public void countNumberofVideosOlderThan3d(String days) {

        int day=Integer.parseInt(days);
        int count= (int) videosList.stream()
                .map(e -> e.getText().trim())
                .filter(text -> text.endsWith("d") && Integer.parseInt(text.replace("d", "")) >= day)
                .count();
        System.out.println("There are "+count+" Videos older than 3 days.");
        LogUtil.info("There are "+count+" Videos older than 3 days.");
    }
}
