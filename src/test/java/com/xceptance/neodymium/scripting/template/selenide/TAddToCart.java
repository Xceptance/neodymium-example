package com.xceptance.neodymium.scripting.template.selenide;

import org.junit.Test;

import com.xceptance.multibrowser.TestTargets;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.objects.Product;
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

    static final String SHIPPINGCOSTS = "$7.00";

    @Test
    public void test()
    {
        PHome homePage = new FOpenHomepage().flow();
        homePage.validate();

        int totalCount = 0;

        // TODO Discuss where and how to validate state dependent things
        homePage.footer().validate();
        homePage.miniCart().validateTotalCount(totalCount);
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
        productPage.addToCart("16 x 12 in", "matte");

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

        cartPage.validateSubAndLineItemTotalAfterAdd(0,
                                                     oldSubtotal,
                                                     "$0.00");

        final String oldSubtotal2 = homePage.miniCart().getSubtotal();

        cartPage.miniCart().validateTotalCount(++totalCount);

        final String searchTerm = "pizza";
        final int searchTermExpectedCount = 1;
        categoryPage = cartPage.search().categoryPageResult(searchTerm);
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);

        final String productName2 = categoryPage.getProducNametByIndex(1, 1);
        PProduct productPage2 = categoryPage.clickProductByIndex(1, 1);
        productPage2.validate();
        productPage2.validateProductName(productName2);
        productPage2.addToCart("64 x 48 in", "gloss");

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
        cartPage2.miniCart().validateTotalCount(++totalCount);
        cartPage2.validateCartItem(0,
                                   productName2,
                                   productStyle2,
                                   productSize2,
                                   productCount2,
                                   productPrice2);
        cartPage2.validateSubAndLineItemTotalAfterAdd(0,
                                                      oldSubtotal2,
                                                      "$0.00");

        int productToUpdateIndex = 0;
        int newProductAmount = 3;
        final String oldSubtotal3 = cartPage2.miniCart().getSubtotal();

        Product productBeforeUpdate = cartPage2.getProduct(productToUpdateIndex);
        cartPage2.updateProductCount(productToUpdateIndex, newProductAmount);

        cartPage2.validateProductAmount(productToUpdateIndex, newProductAmount);

        final String newLinItemPrice = cartPage.getProductTotalUnitPrice(productToUpdateIndex);
        cartPage2.validateSubAndLineItemTotalAfterAdd(productToUpdateIndex,
                                                      oldSubtotal3,
                                                      productBeforeUpdate.getTotalUnitPrice());
        cartPage2.validateCartItem(0,
                                   productBeforeUpdate.getName(),
                                   productBeforeUpdate.getStyle(),
                                   productBeforeUpdate.getSize(),
                                   newProductAmount,
                                   productBeforeUpdate.getUnitPrice());
        cartPage2.miniCart().validateMiniCart(1,
                                              productBeforeUpdate.getName(),
                                              productBeforeUpdate.getStyle(),
                                              productBeforeUpdate.getSize(),
                                              newProductAmount,
                                              newLinItemPrice);
        totalCount = totalCount + newProductAmount - 1;
        cartPage2.miniCart().validateTotalCount(totalCount);

        final String oldLineItemTotal = cartPage.getProductTotalUnitPrice(productToUpdateIndex);
        final String oldSubTotal4 = cartPage2.miniCart().getSubtotal();
        cartPage2.removeProduct(productToUpdateIndex);
        cartPage2.validateSubAndLineItemTotalAfterRemove(oldSubTotal4, oldLineItemTotal);
        totalCount = totalCount - newProductAmount;
        cartPage2.miniCart().validateTotalCount(totalCount);

        Product productFromCartPage = cartPage2.getProduct(0);
        PProduct productPage3 = cartPage2.openProductPage(0);
        productPage3.validateProductName(productFromCartPage.getName());
        productPage3.addToCart(productFromCartPage.getSize(), productFromCartPage.getStyle());
        PCart cartPage3 = productPage3.miniCart().openCartPage();

        cartPage3.validateCartItem(0,
                                   productFromCartPage.getName(),
                                   productFromCartPage.getStyle(),
                                   productFromCartPage.getSize(),
                                   2, productFromCartPage.getUnitPrice());
        cartPage3.miniCart().validateTotalCount(++totalCount);
    }
}
