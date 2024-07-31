package posters.tests.smoke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import posters.flows.CartCleanUpFlow;
import posters.flows.CartFlow;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.processes.AddToCartTestData;

@Owner("Joe Fix")
@Severity(SeverityLevel.CRITICAL)
@Tag("smoke")
public class AddToCartTest extends AbstractTest
{
    @DataItem
    private AddToCartTestData addToCartTestData;
    
    @DataItem
    private String shippingCosts;

    @NeodymiumTest
    public void testAddProductsToCart()
    {
        /// ========== PART 1: USE TOP NAVIGATION TO ADD PRODUCT TO CART ========== ///

        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // store old subtotal
        final String oldSubtotal = homePage.header.miniCart.getSubtotal();

        // go to sub category page
        var subCategoryPage = homePage.header.topNav.clickSubCategory(Neodymium.localizedText(addToCartTestData.getTopCategory()),
                                                                      Neodymium.localizedText(addToCartTestData.getSubCategory()));

        // go to product detail page, add and store displayed product
        final var firstTestDataProduct = addToCartTestData.getProduct1();
        var productDetailPage = subCategoryPage.clickProductByName(firstTestDataProduct.getName());
        productDetailPage.addToCart(firstTestDataProduct.getSize(), firstTestDataProduct.getStyle());
        final var product = productDetailPage.getProduct();

        // go to cart page
        var cartPage = productDetailPage.header.miniCart.openCartPage();

        // validate cart page
        cartPage.validateCartItem(product);
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        CartFlow.validateTotalAfterAdd(firstTestDataProduct.getName(), firstTestDataProduct.getSize(), firstTestDataProduct.getStyle(), oldSubtotal, 0.00);
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(product);

        /// ========== PART 2: USE SEARCH BAR TO ADD PRODUCT TO CART ========== ///

        // store old subtotal
        final String oldSubtotal2 = cartPage.header.miniCart.getSubtotal();

        // go to search results page via search
        var searchResultPage = cartPage.header.search.searchResult(addToCartTestData.getSearchTerm());

        // go to product detail page, add and store displayed product
        final var secondTestDataProduct = addToCartTestData.getProduct2();
        productDetailPage = searchResultPage.clickProductByName(secondTestDataProduct.getName());
        productDetailPage.addToCart(secondTestDataProduct.getSize(), secondTestDataProduct.getStyle());
        final var product2 = productDetailPage.getProduct();

        // go to cart page
        cartPage = productDetailPage.header.miniCart.openCartPage();

        // validate cart page
        cartPage.validateCartItem(product2);
        cartPage.validateCartItem(product);
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        CartFlow.validateTotalAfterAdd(secondTestDataProduct.getName(), secondTestDataProduct.getSize(), secondTestDataProduct.getStyle(), oldSubtotal2, 0.00);
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(product2);
        cartPage.header.miniCart.validateMiniCartItem(product);

        /// ========== PART 3: CHANGE QUANTITY OF PRODUCT IN CART ========== ///

        // store old subtotal
        final String oldSubtotal3 = cartPage.header.miniCart.getSubtotal();

        // store product before update
        final var productBeforeUpdate = cartPage.getProduct(1);

        // update amount of product on cart page
        cartPage.updateProductCount(secondTestDataProduct.getName(), secondTestDataProduct.getAmount());

        // store subtotal of updated product
        String subtotalAfterUpdate = cartPage.getProductTotalPrice(1);

        // validate cart page
        cartPage.validateCartItem(productBeforeUpdate, secondTestDataProduct.getAmount());
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        CartFlow.validateTotalAfterAdd(secondTestDataProduct.getName(), secondTestDataProduct.getSize(), secondTestDataProduct.getStyle(), oldSubtotal3, productBeforeUpdate.calculateTotalPrice());
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(productBeforeUpdate, secondTestDataProduct.getAmount(), subtotalAfterUpdate);

        /// ========== PART 4: REMOVE PRODUCT FROM CART ========== ///

        // store old subtotal
        final String oldSubtotal4 = cartPage.header.miniCart.getSubtotal();

        // store subtotal product before remove
        final String subtotalBeforeRemove = cartPage.getProductTotalPrice(1);

        // remove first product on cart page
        cartPage.removeProduct(secondTestDataProduct.getName());

        // validate cart page
        cartPage.validateCartItem(product);
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        CartFlow.validateTotalAfterRemove(oldSubtotal4, subtotalBeforeRemove);
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(product);

        /// ========== PART 5: ADD SAME PRODUCT TO CART AGAIN ========== ///

        // store old subtotal
        final String oldSubtotal5 = cartPage.header.miniCart.getSubtotal();

        // store product on cart page
        final var productFromCartPageBefore = cartPage.getProduct(1);

        // go to sub category page
        subCategoryPage = cartPage.header.topNav.clickSubCategory(Neodymium.localizedText(addToCartTestData.getTopCategory()),
                                                                  Neodymium.localizedText(addToCartTestData.getSubCategory()));

        // go to product detail page, add and store displayed product
        productDetailPage = subCategoryPage.clickProductByName(firstTestDataProduct.getName());
        productDetailPage.addToCart(productFromCartPageBefore.getSize(), productFromCartPageBefore.getStyle());

        // go to cart
        cartPage = productDetailPage.header.miniCart.openCartPage();

        // store subtotal of updated product
        subtotalAfterUpdate = cartPage.getProductTotalPrice(1);
        final var productFromCartPageAfter = cartPage.getProduct(1);

        // validate cart page
        cartPage.validateCartItem(productFromCartPageBefore, productFromCartPageAfter.getAmount());
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        CartFlow.validateTotalAfterAdd(firstTestDataProduct.getName(), firstTestDataProduct.getSize(), firstTestDataProduct.getStyle(), oldSubtotal5, productFromCartPageBefore.calculateTotalPrice());
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(productFromCartPageBefore, cartPage.header.miniCart.getTotalCount(), subtotalAfterUpdate);

        // go to homepage
        homePage = cartPage.openHomePage();
    }

    @AfterEach
    public void after()
    {
        CartCleanUpFlow.flow();
    }
}
