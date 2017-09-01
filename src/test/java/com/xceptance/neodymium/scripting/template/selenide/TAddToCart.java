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
        int totalCount = 0;

        // TODO Discuss static call (PageHelper) vs. OO (PageObject), Not the pattern anymore?
        // Goto homepage
        PHome homePage = new FOpenHomepage().flow();
        homePage.validate();
        homePage.footer().validate();
        homePage.miniCart().validateTotalCount(totalCount);
        homePage.miniCart().validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart().getSubtotal();

        // Click a top category
        final String topCatName = "World of Nature";
        PCategory categoryPage = homePage.topNav().clickCategory(topCatName);
        categoryPage.validateCategoryName(topCatName);

        // TODO Discuss indexes natural vs. array
        // Click a sub category
        final String categoryName = categoryPage.topNav().getSubCategoryNameByIndex(1, 1);
        categoryPage = categoryPage.topNav().clickSubCategoryByIndex(1, 1);
        categoryPage.validateCategoryName(categoryName);
        categoryPage.validate();

        // Goto product page and add to cart
        final String productName = categoryPage.getProducNametByIndex(1, 1);
        PProduct productPage = categoryPage.clickProductByIndex(1, 1);
        productPage.validate();
        productPage.validateProductName(productName);
        productPage.addToCart("16 x 12 in", "matte");

        // Goto cart and validate
        final Product product = productPage.getProduct();
        PCart cartPage = productPage.miniCart().openCartPage();
        cartPage.validate();
        cartPage.validateShippingCosts(SHIPPINGCOSTS);
        cartPage.miniCart().validateMiniCart(1, product);
        cartPage.validateCartItem(0, product);

        cartPage.validateSubAndLineItemTotalAfterAdd(0, oldSubtotal, "$0.00");

        final String oldSubtotal2 = homePage.miniCart().getSubtotal();
        cartPage.miniCart().validateTotalCount(++totalCount);

        // Search for product on cart page
        final String searchTerm = "pizza";
        final int searchTermExpectedCount = 1;
        // TODO Discuss reuse of variable or new instance
        PCategory categoryPage2 = cartPage.search().categoryPageResult(searchTerm);
        categoryPage2.validateSearchHits(searchTerm, searchTermExpectedCount);
        final String productName2 = categoryPage2.getProducNametByIndex(1, 1);

        // Goto product page and add to cart
        PProduct productPage2 = categoryPage2.clickProductByIndex(1, 1);
        productPage2.validate();
        productPage2.validateProductName(productName2);
        productPage2.addToCart("64 x 48 in", "gloss");
        final Product product2 = productPage.getProduct();

        // Goto cart and validate
        PCart cartPage2 = productPage2.miniCart().openCartPage();
        cartPage2.validate();
        cartPage2.validateShippingCosts(SHIPPINGCOSTS);

        cartPage2.miniCart().validateMiniCart(1, product2);

        cartPage2.miniCart().validateTotalCount(++totalCount);
        cartPage2.validateCartItem(0, product2);
        cartPage2.validateSubAndLineItemTotalAfterAdd(0, oldSubtotal2, "$0.00");

        int productToUpdateIndex = 0;
        int newProductAmount = 3;
        final String oldSubtotal3 = cartPage2.miniCart().getSubtotal();
        Product productBeforeUpdate = cartPage2.getProduct(productToUpdateIndex);

        // Update amount of product on cart page
        cartPage2.updateProductCount(productToUpdateIndex, newProductAmount);
        cartPage2.validateProductAmount(productToUpdateIndex, newProductAmount);

        final String newLinItemPrice = cartPage.getProductTotalUnitPrice(productToUpdateIndex);
        cartPage2.validateSubAndLineItemTotalAfterAdd(productToUpdateIndex,
                                                      oldSubtotal3,
                                                      productBeforeUpdate.getTotalUnitPrice());
        cartPage2.validateCartItem(0, productBeforeUpdate, newProductAmount);
        cartPage2.miniCart().validateMiniCart(1, productBeforeUpdate, newProductAmount, newLinItemPrice);
        totalCount = totalCount + newProductAmount - 1;
        cartPage2.miniCart().validateTotalCount(totalCount);

        final String oldLineItemTotal = cartPage.getProductTotalUnitPrice(productToUpdateIndex);
        final String oldSubTotal4 = cartPage2.miniCart().getSubtotal();

        // Remove product on cart page
        cartPage2.removeProduct(productToUpdateIndex);
        cartPage2.validateSubAndLineItemTotalAfterRemove(oldSubTotal4, oldLineItemTotal);
        totalCount = totalCount - newProductAmount;
        cartPage2.miniCart().validateTotalCount(totalCount);

        Product productFromCartPage = cartPage2.getProduct(0);
        PProduct productPage3 = cartPage2.openProductPage(0);
        productPage3.validateProductName(productFromCartPage.getName());
        productPage3.addToCart(productFromCartPage.getSize(), productFromCartPage.getStyle());
        PCart cartPage3 = productPage3.miniCart().openCartPage();

        cartPage3.validateCartItem(0, productFromCartPage, 2);
        cartPage3.miniCart().validateTotalCount(++totalCount);
    }
}
