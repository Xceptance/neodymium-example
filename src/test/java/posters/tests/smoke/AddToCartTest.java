package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.AddToCartTestData;

@Owner("Joe Fix")
@Severity(SeverityLevel.CRITICAL)
@Tag("smoke")
public class AddToCartTest extends AbstractTest
{
    @Test
    @DataSet(1)
    @DataSet(2)
    public void testAddProductsToCart()
    {   
        // use test data class
        AddToCartTestData addToCartTestData = DataUtils.get(AddToCartTestData.class);
        final String shippingCosts = Neodymium.dataValue("shippingCosts");
        int totalCount = 0;

        /// ----- PART 1: GO TO EMPTY CART AND VALIDATE ----- ///
        
        // go to homepage
        var homePage = OpenHomePageFlow.flow();
        
        // go to empty cart page
        var cartPage = homePage.miniCart.openCartPage();
        
        // validate empty cart page
        cartPage.validateEmptyCartPage();
        cartPage.validateHeaderAndFooter(cartPage);
        cartPage.miniCart.validateEmptyMiniCart();
        
        // go back to homepage
        cartPage.openHomePage();        
        
        /// ----- PART 2: USE TOP NAVIGATION TO ADD PRODUCT TO CART ----- ///

        // store old subtotal
        final String oldSubtotal = homePage.miniCart.getSubtotal();

        // go to top category page
        var categoryPage = homePage.topNav.clickCategory(addToCartTestData.getTopCategory());
        
        // validate top category page
        categoryPage.validate(addToCartTestData.getTopCategory(), addToCartTestData.getExpectedCategoryResultCount());
        categoryPage.validateHeaderAndFooter(categoryPage);
        categoryPage.miniCart.validateEmptyMiniCart();

        // go to sub category page and validate
        var subCategoryPage = categoryPage.topNav.clickSubCategory(addToCartTestData.getTopCategory(), addToCartTestData.getSubCategory());
        
        // validate sub category page
        subCategoryPage.validate(addToCartTestData.getSubCategory(), addToCartTestData.getExpectedSubCategoryResultCount());

        // go to product detail page
        var productDetailPage = categoryPage.clickProductByPosition(addToCartTestData.getSubCategoryResultPosition());
        
        // add product to cart
        productDetailPage.addToCart(addToCartTestData.getSizeFirstProduct(), addToCartTestData.getStyleFirstProduct());
        
        // store displayed product
        final var product = productDetailPage.getProduct();
        
        // validate product detail page
        productDetailPage.validate(product.getName());
        productDetailPage.validateHeaderAndFooter(productDetailPage);
        productDetailPage.miniCart.validateTotalCount(++totalCount);
        productDetailPage.miniCart.validateMiniCart(1, product);
        
        // go to cart page
        cartPage = productDetailPage.miniCart.openCartPage();

        // validate cart page
        cartPage.validateCartItem(1, product);
        cartPage.validate(shippingCosts, cartPage.miniCart.getSubtotal());
        cartPage.validateTotalAfterAdd(1, oldSubtotal, 0.00);
        cartPage.miniCart.validateTotalCount(totalCount);
        cartPage.miniCart.validateMiniCart(1, product);
 
        /// ----- PART 3: USE SEARCH BAR TO ADD PRODUCT TO CART ----- ///
        
        // store old subtotal
        final String oldSubtotal2 = cartPage.miniCart.getSubtotal();
        
        // go to category page via search
        categoryPage = cartPage.search.categoryPageResult(addToCartTestData.getSearchTerm());
        
        // validate category page
        categoryPage.validate(addToCartTestData.getSearchTerm(), addToCartTestData.getExpectedSearchResultCount());
        categoryPage.miniCart.validateMiniCart(1, product);

        // go to product detail page
        productDetailPage = categoryPage.clickProductByPosition(addToCartTestData.getSearchResultPosition());
        
        // add product to cart
        productDetailPage.addToCart(addToCartTestData.getSizeSecondProduct(), addToCartTestData.getStyleSecondProduct());
        
        // store displayed product
        final var product2 = productDetailPage.getProduct();
        
        // validate product detail page
        productDetailPage.validate(product2.getName());
        productDetailPage.miniCart.validateTotalCount(++totalCount);
        productDetailPage.miniCart.validateMiniCart(1, product2);
        productDetailPage.miniCart.validateMiniCart(2, product);

        // go to cart page
        cartPage = productDetailPage.miniCart.openCartPage();

        // validate cart page
        cartPage.validateCartItem(1, product2);
        cartPage.validateCartItem(2, product);
        cartPage.validate(shippingCosts, cartPage.miniCart.getSubtotal());
        cartPage.validateTotalAfterAdd(1, oldSubtotal2, 0.00);
        cartPage.miniCart.validateTotalCount(totalCount);
        cartPage.miniCart.validateMiniCart(1, product2);
        cartPage.miniCart.validateMiniCart(2, product);
        
        /// ----- PART 4: CHANGE QUANTITY OF PRODUCT IN CART ----- ///

        // store old subtotal
        final String oldSubtotal3 = cartPage.miniCart.getSubtotal(); 
        
        // store product before update
        final var productBeforeUpdate = cartPage.getProduct(addToCartTestData.getProductUpdatePosition());
        
        // update amount of product on cart page
        cartPage.updateProductCount(addToCartTestData.getProductUpdatePosition(), addToCartTestData.getAmountChange());
        totalCount = totalCount + addToCartTestData.getAmountChange() - 1;
        
        // store subtotal of updated product
        String subtotalAfterUpdate = cartPage.getProductTotalPrice(addToCartTestData.getProductUpdatePosition());
        
        // validate cart page
        cartPage.validateCartItem(addToCartTestData.getProductUpdatePosition(), productBeforeUpdate, addToCartTestData.getAmountChange());
        cartPage.validate(shippingCosts, cartPage.miniCart.getSubtotal());
        cartPage.validateTotalAfterAdd(addToCartTestData.getProductUpdatePosition(), oldSubtotal3, productBeforeUpdate.getTotalPrice());
        cartPage.miniCart.validateTotalCount(totalCount);
        cartPage.miniCart.validateMiniCart(1, productBeforeUpdate, addToCartTestData.getAmountChange(), subtotalAfterUpdate);
        
        /// ----- PART 5: REMOVE PRODUCT FROM CART ----- ///
        
        // store old subtotal
        final String oldSubtotal4 = cartPage.miniCart.getSubtotal();
        
        // store subtotal product before remove
        final String subtotalBeforeRemove = cartPage.getProductTotalPrice(addToCartTestData.getProductRemovePosition());
        
        // remove product on cart page
        cartPage.removeProduct(addToCartTestData.getProductRemovePosition());
        totalCount = totalCount - addToCartTestData.getAmountChange();
        
        // validate cart page
        cartPage.validateCartItem(1, product);
        cartPage.validate(shippingCosts, cartPage.miniCart.getSubtotal());
        cartPage.validateTotalAfterRemove(oldSubtotal4, subtotalBeforeRemove);
        cartPage.miniCart.validateTotalCount(totalCount);
        cartPage.miniCart.validateMiniCart(1, product);
        
        /// ----- PART 6: ADD SAME PRODUCT TO CART AGAIN ----- ///
        
        // store old subtotal
        final String oldSubtotal5 = cartPage.miniCart.getSubtotal();
        
        // store product on cart page
        final var productFromCartPageBefore = cartPage.getProduct(1);
        
        // go to product detail page
        productDetailPage = cartPage.openProductDetailPage(1);
        
        // add product to cart
        productDetailPage.addToCart(productFromCartPageBefore.getSize(), productFromCartPageBefore.getStyle());
        
        // validate product detail page
        productDetailPage.validate(productFromCartPageBefore.getName());
        productDetailPage.miniCart.validateTotalCount(++totalCount);
        
        // go to cart
        cartPage = productDetailPage.miniCart.openCartPage();
        
        // store subtotal of updated product
        subtotalAfterUpdate = cartPage.getProductTotalPrice(1);
        final var productFromCartPageAfter = cartPage.getProduct(1);
        
        // validate cart page
        cartPage.validateCartItem(1, productFromCartPageBefore, productFromCartPageAfter.getAmount());
        cartPage.validate(shippingCosts, cartPage.miniCart.getSubtotal());
        cartPage.validateTotalAfterAdd(1, oldSubtotal5, productFromCartPageBefore.getTotalPrice());
        cartPage.miniCart.validateTotalCount(totalCount);
        cartPage.miniCart.validateMiniCart(1, productFromCartPageBefore, totalCount, subtotalAfterUpdate);
    }
}
