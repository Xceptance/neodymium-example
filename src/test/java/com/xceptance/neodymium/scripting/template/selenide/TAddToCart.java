package com.xceptance.neodymium.scripting.template.selenide;

import org.junit.Test;

import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.page.CartPage;
import com.xceptance.neodymium.scripting.template.selenide.page.CategoryPage;
import com.xceptance.neodymium.scripting.template.selenide.page.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.ProductPage;
import com.xceptance.neodymium.scripting.template.selenide.utility.VariableStoreProvider;

public class TAddToCart extends BasicTest
{

    @Test
    public void test()
    {
        HomePage homePage = FOpenHomepage.flow();
        homePage.validate();

        // TODO Discuss where and how to validate state dependent things
        homePage.footer().validate();
        homePage.miniCart().validateQuantity(0);
        homePage.miniCart().validateSubtotal("$0.00");
        // String oldSubtotal = homePage.miniCart().getSubtotal();

        // Click a top category
        String topCatName = "World of Nature";
        CategoryPage categoryPage = homePage.topNav().clickCategory(topCatName);
        categoryPage.validateCategoryName(topCatName);

        // Click a sub category
        String categoryName = categoryPage.topNav().getSubCategoryNameByIndex(1, 1);
        categoryPage = categoryPage.topNav().clickSubCategoryByIndex(1, 1);
        categoryPage.validateCategoryName(categoryName);
        categoryPage.validate();

        ProductPage productPage = categoryPage.clickProductByIndex(1, 1);
        productPage.validate();
        productPage.addToCart("2", "gloss");

        CartPage cartPage = productPage.miniCart().openCartPage();
        cartPage.validate();
        cartPage.miniCart().validateMiniCart("1",
                                             VariableStoreProvider.get("productName").toString(),
                                             VariableStoreProvider.get("productStyle").toString(),
                                             VariableStoreProvider.get("productSize").toString(),
                                             "1",
                                             "$25.50");
    }
}
