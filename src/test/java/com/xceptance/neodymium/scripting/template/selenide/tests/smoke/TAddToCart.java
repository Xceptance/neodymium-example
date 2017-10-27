package com.xceptance.neodymium.scripting.template.selenide.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.OpenHomePageFlow;
import com.xceptance.neodymium.scripting.template.selenide.objects.Product;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.CategoryPage;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.ProductdetailPage;
import com.xceptance.neodymium.scripting.template.selenide.page.checkout.CartPage;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;

@Browser(
{
  "Chrome_1024x768"
})
public class TAddToCart extends BasicTest
{

    @Test
    public void test()
    {
        int totalCount = 0;

        // TODO adapt shorter style

        // Goto homepage
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validateStructure();
        homePage.footer().validate();
        homePage.miniCart().validateTotalCount(totalCount);
        homePage.miniCart().validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart().getSubtotal();

        // Go to a top category page
        final String topCatName = "World of Nature";
        CategoryPage categoryPage = homePage.topNav().clickCategory(topCatName);
        categoryPage.validateCategoryName(topCatName);

        // TODO Discuss indexes natural vs. array !!! Implement natural
        // Goto sub category page
        final String categoryName = categoryPage.topNav().getSubCategoryNameByIndex(1, 1);
        CategoryPage categoryPage2 = categoryPage.topNav().clickSubCategoryByIndex(1, 1);
        categoryPage2.validateCategoryName(categoryName);
        categoryPage2.validateStructure();

        // Goto product page and add to cart
        final String productName = categoryPage2.getProductNameByPosition(1, 1);
        ProductdetailPage productPage = categoryPage2.clickProductByPosition(1, 1);
        productPage.validateStructure();
        productPage.validateProductName(productName);
        productPage.addToCart("16 x 12 in", "matte");

        // Goto cart and validate
        final Product product = productPage.getProduct();
        CartPage cartPage = productPage.miniCart().openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(SHIPPINGCOSTS);
        cartPage.miniCart().validateMiniCart(1, product);
        cartPage.validateCartItem(0, product);

        cartPage.validateSubAndLineItemTotalAfterAdd(0, oldSubtotal, "$0.00");

        final String oldSubtotal2 = cartPage.miniCart().getSubtotal();
        cartPage.miniCart().validateTotalCount(++totalCount);

        // Search for product on cart page
        final String searchTerm = "pizza";
        final int searchTermExpectedCount = 1;
        // TODO Discuss reuse of variable or new instance !!! Reuse and declare in place
        CategoryPage categoryPage3 = cartPage.search().categoryPageResult(searchTerm);
        categoryPage3.validateSearchHits(searchTerm, searchTermExpectedCount);
        final String productName2 = categoryPage3.getProductNameByPosition(1, 1);

        // Goto product page and add to cart
        ProductdetailPage productPage2 = categoryPage3.clickProductByPosition(1, 1);
        productPage2.validateStructure();
        productPage2.validateProductName(productName2);
        productPage2.addToCart("64 x 48 in", "gloss");
        final Product product2 = productPage.getProduct();

        // Goto cart and validate
        CartPage cartPage2 = productPage2.miniCart().openCartPage();
        cartPage2.validateStructure();
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
        ProductdetailPage productPage3 = cartPage2.openProductPage(0);
        productPage3.validateProductName(productFromCartPage.getName());
        productPage3.addToCart(productFromCartPage.getSize(), productFromCartPage.getStyle());
        CartPage cartPage3 = productPage3.miniCart().openCartPage();

        cartPage3.validateCartItem(0, productFromCartPage, 2);
        cartPage3.miniCart().validateTotalCount(++totalCount);
    }
}
