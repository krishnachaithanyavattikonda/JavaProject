package com.dp2.pages;

import com.framework.utils.CsvUtil;
import com.framework.utils.LogUtil;
import com.framework.utils.SeleniumUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.framework.base.BasePage;
import com.framework.utils.TestDataUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.*;

public class HomePage extends BasePage {
    private TestDataUtil testDataUtil=new TestDataUtil("src/test/resources/dp2/data/HomePageData.json");

    @FindBy(xpath = "//a[@title='Home']//span[text()='Chicago Bulls']")
    private  WebElement homePageTitle;

    @FindBy(xpath = "//footer[@role='contentinfo'][@data-testid='footer']//nav")
    private List<WebElement> footerLinkSections;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void checkForPageDisplay(){
        waitForPageLoad();
        Assert.assertTrue(SeleniumUtil.isElementDisplayed(homePageTitle),"Home Page Not Displayed.");
    }

    public void writeFooterLinksToCSV() {
        try {
            List<String[]> csvData = new ArrayList<>();
            csvData.add(new String[]{"Heading", "Link Name", "URL"});

            Map<String, List<String>> linkOccurrences = new HashMap<>();
            List<String[]> duplicateLinks = new ArrayList<>();

            for (WebElement linkSection : footerLinkSections) {
                scrollIntoView(linkSection);
                WebElement heading = linkSection.findElement(By.tagName("h2"));
                String headingText = getText(heading);

                List<WebElement> footerLinks = linkSection.findElements(By.xpath(".//ul/li/a"));

                for (WebElement link : footerLinks) {
                    String linkText = getText(link);
                    String linkUrl = getAttribute(link,"href");

                    String sectionAndLink = headingText + "." + linkText;

                    if (linkOccurrences.containsKey(linkUrl)) {
                        linkOccurrences.get(linkUrl).add(sectionAndLink);

                        System.out.println("Duplicate Link Found: " + linkUrl);
                        System.out.println("Appears in sections: " + linkOccurrences.get(linkUrl));

                        duplicateLinks.add(new String[]{"DUPLICATE", linkText, linkUrl,
                                String.join(" | ", linkOccurrences.get(linkUrl))});
                    } else {

                        linkOccurrences.put(linkUrl, new ArrayList<>(Collections.singletonList(sectionAndLink)));
                        csvData.add(new String[]{headingText, linkText, linkUrl});
                    }
                }
            }

            CsvUtil.writeToCsv(testDataUtil.getJsonNode("HomePageData","footerTests").
                    get("allLinksPath").asText(), csvData,false);

            if (!duplicateLinks.isEmpty()) {
                CsvUtil.writeToCsv(testDataUtil.getJsonNode("HomePageData","footerTests").
                        get("duplicateLinksPath").asText(), duplicateLinks,false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.error(e.getMessage());
        }
    }
}
