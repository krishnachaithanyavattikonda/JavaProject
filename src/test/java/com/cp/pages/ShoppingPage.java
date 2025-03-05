package com.cp.pages;

import com.framework.base.BasePage;
import com.framework.utils.LogUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ShoppingPage extends BasePage {

    @FindBy(xpath = "//div[@class='side-nav-facets']//div[@data-trk-id='all-departments']//span[text()='Jackets']")
    private WebElement filterJackets;

    @FindBy(xpath = "//div[@data-talos='ddPageSize'][@role='button']")
    private WebElement pageSizeDropdown;

    @FindBy(xpath = "//div[@data-talos='ddPageSize'][@role='listbox']//li[text()='96 Items']")
    private WebElement pageSize96;

    @FindBy(xpath = "//div[@class='product-grid-top-area']//ul[@class='pagination-list-container']//li//a[@data-talos='linkSearchResultsPage']")
    private List<WebElement> allPagesList;

    @FindBy(xpath = "//div[@class='layout-row product-grid']//div[@class='column']//div[@class='price-row']//span[@class='lowest']//span[@class='price' or @class='price primary']")
    private List<WebElement> productPriceTag;

    @FindBy(xpath = "//div[@class='layout-row product-grid']//div[@class='column']//div[@class='product-card-title']//a")
    private List<WebElement> productDesciptionTag;

    @FindBy(xpath = "//div[@class='layout-row product-grid']//div[@class='column']//div[@class='product-vibrancy-container']")
    private List<WebElement> productVibrancyMessage;

    public ShoppingPage(WebDriver driver){
        super(driver);
    }

    public void filterMensItemListWithJackets() throws InterruptedException {
        waitForPageLoad();
        Thread.sleep(5000);
        click(filterJackets);
    }
    public void setPageSizeOfList(String pageSize){
        waitForPageLoad();
        click(pageSizeDropdown);
        click(pageSize96);
    }
    public void readAllJacketProductsAcrossPages() throws InterruptedException {
        String filePath = "src/test/resources/cp/data/jacket_products.txt"; // Output file
        try (FileWriter writer = new FileWriter(filePath)) {
            try {
                for (WebElement page : allPagesList) {
                    Thread.sleep(5000);
                    scrollIntoView(page);
                    clickWithJS(page);
                    Thread.sleep(5000);

                    for (int i = 0; i < productDesciptionTag.size(); i++) {
                        String desc = productDesciptionTag.get(i).getText();
                        String price = productPriceTag.get(i).getText();
                        String vibrancyMessage = "";

                        String dynamicVibrancyXPath = "//div[@class='layout-row product-grid']//div[@class='column']//div[@class='product-card-title']//a[contains(text(),'" + desc + "')]//../../..//div[@class='product-vibrancy-container']";
                        try {
                            WebElement vibMes = driver.findElement(By.xpath(dynamicVibrancyXPath));
                            if (isElementDisplayed(vibMes)) {
                                vibrancyMessage = vibMes.getText();
                            }
                        } catch (Exception e){}

                        String line = "📌 Product: " + desc +
                                "\n💲 Price: " + price +
                                "\n✨ Vibrancy: " + (vibrancyMessage.isEmpty() ? "N/A" : vibrancyMessage) +
                                "\n------------------------------------------------------------------------\n";

                        writer.write(line);
                        LogUtil.info("Product saved:\n" + line);
                    }
                }
            }catch(Exception e){
                LogUtil.warn("Not able to click page..!!");
            }
            LogUtil.info("Product details successfully written to: " + filePath);
        } catch (IOException e) {
            LogUtil.error("Error writing to file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
