package com.dp1.pages;

import com.framework.base.BasePage;
import com.framework.utils.LogUtil;
import com.framework.utils.SeleniumUtil;
import com.framework.utils.TestDataUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HomePage extends BasePage {

    private final TestDataUtil testDataUtil=new TestDataUtil("src/test/resources/dp1/data/HomePageData.json");

    @FindBy(xpath = "//a[@title='Home']//span[text()='Philadelphia Sixers']")
    private WebElement pageTitle;

    @FindBy(xpath = "//button[contains(@class,'TileHeroStories_tileHeroStoriesButton__hW8ew')]")
    private List<WebElement> tilesList;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void checkForPageDisplay(){
        waitForPageLoad();
        Assert.assertTrue(SeleniumUtil.isElementDisplayed(pageTitle),"Page not displayed!!");
    }

    public void navigateThroughTilesDetails() {
        List<String> expectedTitles = StreamSupport.stream(
                        testDataUtil.getJsonNode("HomePage", "tilesData").
                                get("titles").spliterator(), false)
                .map(JsonNode::asText)
                .collect(Collectors.toList());

        int expectedTileDuration = testDataUtil.
                getJsonNode("HomePage", "tilesData").get("tileDuration").asInt();

        Assert.assertEquals(tilesList.size(), expectedTitles.size(), "Mismatch in the number of tiles!");

        String lastActiveTitle = "";
        int observedTiles = 0;
        Map<String, Long> tileDurations = new LinkedHashMap<>();

        waitForCondition(() -> tilesList.stream()
                .anyMatch(tile -> "true".equals(getAttribute(tile,"aria-selected"))), 20);

        while (observedTiles < expectedTitles.size()) {
            WebElement activeTile = tilesList.stream()
                    .filter(tile -> "true".equals(getAttribute(tile,"aria-selected")))
                    .findFirst()
                    .orElse(null);

            if (activeTile != null) {
                WebElement titleElement = activeTile.findElement(By.cssSelector(".TileHeroStories_tileHeroStoriesButtonTitle__8Xiey"));
                String currentTitle = getText(titleElement).trim();
                LogUtil.info("🎯 Active Tile Detected: " + currentTitle);

                if (!expectedTitles.contains(currentTitle)) {
                    Assert.fail("Unexpected tile detected: " + currentTitle);
                }

                if (!currentTitle.equals(lastActiveTitle)) {
                    Assert.assertEquals(currentTitle, expectedTitles.get(observedTiles), "Unexpected tile title!");
                    lastActiveTitle = currentTitle;
                    observedTiles++;

                    long startTime = System.currentTimeMillis();

                    SeleniumUtil.waitForCondition(() -> {
                        WebElement newActiveTile = tilesList.stream()
                                .filter(tile -> "true".equals(SeleniumUtil.getAttribute(tile,"aria-selected")))
                                .findFirst()
                                .orElse(null);

                        if (newActiveTile != null) {
                            String newTitle = newActiveTile.findElement(By.cssSelector(".TileHeroStories_tileHeroStoriesButtonTitle__8Xiey")).getText().trim();
                            return !newTitle.equals(currentTitle);
                        }
                        return false;
                    }, expectedTileDuration + 10); // Added buffer to handle delays

                    long duration = (System.currentTimeMillis() - startTime) / 1000;
                    tileDurations.put(currentTitle, duration);
                    LogUtil.info("⏳ Tile '" + currentTitle + "' displayed for " + duration + " seconds");

                    Assert.assertTrue(duration >= expectedTileDuration,
                            "❌ Tile duration is too short! Expected: " + expectedTileDuration + "s, but got: " + duration + "s");
                }
            }
        }
    }





}


