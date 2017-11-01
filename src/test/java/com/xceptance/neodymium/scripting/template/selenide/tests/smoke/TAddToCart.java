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

        // Goto homepage
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

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
        categoryPage = categoryPage.topNav().clickSubCategoryByIndex(1, 1);
        categoryPage.validate(categoryName);

        // Goto product page and add to cart
        final String productName = categoryPage.getProductNameByPosition(1, 1);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(1, 1);
        productPage.validate(productName);
        productPage.addToCart("16 x 12 in", "matte");

        // Goto cart and validate
        final Product product = productPage.getProduct();
        CartPage cartPage = productPage.miniCart().openCartPage();
        cartPage.validate(SHIPPINGCOSTS);
        cartPage.miniCart().validateMiniCart(1, product);
        cartPage.validateCartItem(0, product);

        cartPage.validateSubAndLineItemTotalAfterAdd(0, oldSubtotal, "$0.00");

        final String oldSubtotal2 = cartPage.miniCart().getSubtotal();
        cartPage.miniCart().validateTotalCount(++totalCount);

        // Search for product on cart page
        final String searchTerm = "pizza";
        final int searchTermExpectedCount = 1;
        categoryPage = cartPage.search().categoryPageResult(searchTerm);
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);
        final String productName2 = categoryPage.getProductNameByPosition(1, 1);

        // Goto product page and add to cart
        productPage = categoryPage.clickProductByPosition(1, 1);
        productPage.validate(productName2);
        productPage.addToCart("64 x 48 in", "gloss");
        final Product product2 = productPage.getProduct();

        // Goto cart and validate
        cartPage = productPage.miniCart().openCartPage();
        cartPage.validate(SHIPPINGCOSTS);

        cartPage.miniCart().validateMiniCart(1, product2);

        cartPage.miniCart().validateTotalCount(++totalCount);
        cartPage.validateCartItem(0, product2);
        cartPage.validateSubAndLineItemTotalAfterAdd(0, oldSubtotal2, "$0.00");

        int productToUpdateIndex = 0;
        int newProductAmount = 3;
        final String oldSubtotal3 = cartPage.miniCart().getSubtotal();
        Product productBeforeUpdate = cartPage.getProduct(productToUpdateIndex);

        // Update amount of product on cart page
        cartPage.updateProductCount(productToUpdateIndex, newProductAmount);
        cartPage.validateProductAmount(productToUpdateIndex, newProductAmount);

        final String newLinItemPrice = cartPage.getProductTotalUnitPrice(productToUpdateIndex);
        cartPage.validateSubAndLineItemTotalAfterAdd(productToUpdateIndex,
                                                     oldSubtotal3,
                                                     productBeforeUpdate.getTotalUnitPrice());
        cartPage.validateCartItem(0, productBeforeUpdate, newProductAmount);
        cartPage.miniCart().validateMiniCart(1, productBeforeUpdate, newProductAmount, newLinItemPrice);
        totalCount = totalCount + newProductAmount - 1;
        cartPage.miniCart().validateTotalCount(totalCount);

        final String oldLineItemTotal = cartPage.getProductTotalUnitPrice(productToUpdateIndex);
        final String oldSubTotal4 = cartPage.miniCart().getSubtotal();

        // Remove product on cart page
        cartPage.removeProduct(productToUpdateIndex);
        cartPage.validateSubAndLineItemTotalAfterRemove(oldSubTotal4, oldLineItemTotal);
        totalCount = totalCount - newProductAmount;
        cartPage.miniCart().validateTotalCount(totalCount);

        Product productFromCartPage = cartPage.getProduct(0);
        productPage = cartPage.openProductPage(0);
        productPage.validate(productFromCartPage.getName());
        productPage.addToCart(productFromCartPage.getSize(), productFromCartPage.getStyle());
        cartPage = productPage.miniCart().openCartPage();

        cartPage.validateCartItem(0, productFromCartPage, 2);
        cartPage.miniCart().validateTotalCount(++totalCount);
    }
}
