package com.xceptance.neodymium.scripting.template.selenide;

import org.junit.Test;

import com.xceptance.multibrowser.TestTargets;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.page.PCart;
import com.xceptance.neodymium.scripting.template.selenide.page.PCategory;
import com.xceptance.neodymium.scripting.template.selenide.page.PHome;
import com.xceptance.neodymium.scripting.template.selenide.page.PProduct;

@TestTargets(
{
  "Chrome_1024x768"
})

public class TAddToCart extends BasicTest
{

    static final String SHIPPINGCOSTS = "7.00";

    @Test
    public void test()
    {
        PHome homePage = new FOpenHomepage().flow();
        homePage.validate();

        // TODO Discuss where and how to validate state dependent things
        homePage.footer().validate();
        homePage.miniCart().validateQuantity(0);
        homePage.miniCart().validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart().getSubtotal();
        final String currency = "$";

        // Click a top category
        final String topCatName = "World of Nature";
        PCategory categoryPage = homePage.topNav().clickCategory(topCatName);
        categoryPage.validateCategoryName(topCatName);

        // Click a sub category
        final String categoryName = categoryPage.topNav().getSubCategoryNameByIndex(1, 1);
        categoryPage = categoryPage.topNav().clickSubCategoryByIndex(1, 1);
        categoryPage.validateCategoryName(categoryName);
        categoryPage.validate();

        final String productName = categoryPage.getProducNametByIndex(1, 1);
        PProduct productPage = categoryPage.clickProductByIndex(1, 1);
        productPage.validate();
        productPage.validateProductName(productName);
        productPage.addToCart(0, "matte");

        final String productSize = productPage.getChosenSize();
        final String productStyle = productPage.getChosenStyle();
        final String productPrice = productPage.getProductPrice();
        final int productCount = 1;

        PCart cartPage = productPage.miniCart().openCartPage();
        cartPage.validate();
        cartPage.validateShippingCosts(SHIPPINGCOSTS);
        cartPage.miniCart().validateMiniCart(1,
                                             productName,
                                             productStyle,
                                             productSize,
                                             productCount,
                                             productPrice);
        cartPage.validateCartItem(0,
                                  productName,
                                  productStyle,
                                  productSize,
                                  productCount,
                                  productPrice);

        cartPage.validateSubTotal(0,
                                  oldSubtotal,
                                  currency,
                                  productPrice);

        final String oldSubtotal2 = homePage.miniCart().getSubtotal();

        cartPage.miniCart().validateQuantity(productCount);

        final String searchTerm = "pizza";
        final int searchTermExpectedCount = 1;
        categoryPage = cartPage.search().categoryPageResult(searchTerm);
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);

        final String productName2 = categoryPage.getProducNametByIndex(1, 1);
        PProduct productPage2 = categoryPage.clickProductByIndex(1, 1);
        productPage2.validate();
        productPage2.validateProductName(productName2);
        productPage2.addToCart(2, "gloss");

        final String productSize2 = productPage2.getChosenSize();
        final String productStyle2 = productPage2.getChosenStyle();
        final String productPrice2 = productPage2.getProductPrice();
        final int productCount2 = 1;

        PCart cartPage2 = productPage2.miniCart().openCartPage();
        cartPage2.validate();
        cartPage2.validateShippingCosts(SHIPPINGCOSTS);

        cartPage2.miniCart().validateMiniCart(1,
                                              productName2,
                                              productStyle2,
                                              productSize2,
                                              productCount2,
                                              productPrice2);
        cartPage2.validateCartItem(0,
                                   productName2,
                                   productStyle2,
                                   productSize2,
                                   productCount2,
                                   productPrice2);
        cartPage2.validateSubTotal(0,
                                   oldSubtotal2,
                                   currency,
                                   productPrice2);

    }
}
