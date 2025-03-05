package com.cp.StepDefinitions;

import com.framework.utils.DriverManager;
import com.cp.pages.ShoppingPage;
import io.cucumber.java.en.And;

public class ShoppingPageSteps {

    private ShoppingPage shoppingPage;

    public ShoppingPageSteps() {
        shoppingPage=new ShoppingPage(DriverManager.getDriver());
    }

    @And("I filter Jackets from the List")
    public void iFilterJacketsFromTheList() throws InterruptedException {
        shoppingPage.filterMensItemListWithJackets();
    }

    @And("I set page size as {string}")
    public void iSetPageSizeAs(String pageSize) {
        shoppingPage.setPageSizeOfList(pageSize);
    }

    @And("I read Product details to file")
    public void iReadProductDetailsToFile() throws InterruptedException {
        shoppingPage.readAllJacketProductsAcrossPages();
    }

}
