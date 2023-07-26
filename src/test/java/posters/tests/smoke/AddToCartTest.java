package posters.tests.smoke;

import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selenide;
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
    public void testAddProductsToCart()
    {   
        // use test data class
        AddToCartTestData addToCartTestData = DataUtils.get(AddToCartTestData.class);
        final String shippingCosts = Neodymium.dataValue("shippingCosts");
        int totalCount = 0;

        /// ----- PART 1: GO TO EMPTY CART AND VALIDATE ----- ///
        
        // go to homepage
        var homePage = OpenHomePageFlow.flow();
        
        // go to cart page and validate
        var cartPage = homePage.miniCart.openCartPage();
        cartPage.validateEmptyCartPage();
        cartPage.miniCart.validateEmptyMiniCart();
        cartPage.validateHeaderAndFooter(cartPage);
        
        // go back to homepage
        cartPage.openHomePage();        
        
        /// ----- PART 2: USE TOP NAVIGATION TO ADD PRODUCT TO CART ----- ///

        // store old subtotal
        final String oldSubtotal = homePage.miniCart.getSubtotal();

        // go to top category page and validate
        var categoryPage = homePage.topNav.clickCategory(addToCartTestData.getTopCategory());
        categoryPage.validate(addToCartTestData.getTopCategory(), addToCartTestData.getExpectedCategoryResultCount());
        categoryPage.validateHeaderAndFooter(categoryPage);

        // go to sub category page
        var subCategoryPage = categoryPage.topNav.clickSubCategory(addToCartTestData.getTopCategory(), addToCartTestData.getSubCategory());
        subCategoryPage.validate(addToCartTestData.getSubCategory(), addToCartTestData.getExpectedSubCategoryResultCount());

        // go to product page
        final String productName = categoryPage.getProductNameByPosition(addToCartTestData.getPosition());
        var productDetailPage = categoryPage.clickProductByPosition(addToCartTestData.getPosition());
        productDetailPage.validate(productName);
        productDetailPage.validateHeaderAndFooter(productDetailPage);

        // add product to cart
        productDetailPage.addToCart("16 x 12 in", "matte");

        // go to cart
        final var product = productDetailPage.getProduct();
        cartPage = productDetailPage.miniCart.openCartPage();

        /*
        // validate cart page
        cartPage.validate(shippingCosts);
        cartPage.miniCart.validateMiniCart(addToCartTestData.getPosition(), product);
        cartPage.validateCartItem(addToCartTestData.getPosition(), product);

        // validate (sub)total after adding new item to cart
        cartPage.validateTotalAfterAdd(addToCartTestData.getPosition(), oldSubtotal, 0.00);

        // store old subtotal
        final String oldSubtotal2 = cartPage.miniCart.getSubtotal();

        // validate mini cart total product count
        cartPage.miniCart.validateTotalCount(++totalCount);
        
        /*
        /// ----- part 2: search for product and add to cart ----- ///

        // search for product on cart page
        categoryPage = cartPage.search.categoryPageResult(addToCartTestData.getSearchTerm());
        categoryPage.validateSearchHits(addToCartTestData.getSearchTerm(), addToCartTestData.getSearchResultCount());

        // go to product page
        final String productName2 = categoryPage.getProductNameByPosition(addToCartTestData.getPosition());
        productDetailPage = categoryPage.clickProductByPosition(addToCartTestData.getPosition());
        productDetailPage.validate(productName2);

        // add product to cart
        productDetailPage.addToCart("64 x 48 in", "gloss");

        // go to cart
        final var product2 = productDetailPage.getProduct();
        cartPage = productDetailPage.miniCart.openCartPage();

        // validate cart page
        cartPage.validate(shippingCosts);
        cartPage.miniCart.validateMiniCart(addToCartTestData.getPosition(), product2);
        cartPage.validateCartItem(addToCartTestData.getPosition(), product2);
        
        // validate (sub)total after adding new item to cart
        cartPage.validateTotalAfterAdd(1, oldSubtotal2, 0.00);

        // store old subtotal
        final String oldSubtotal3 = cartPage.miniCart.getSubtotal(); 
        
        // validate mini cart total product count
        cartPage.miniCart.validateTotalCount(++totalCount); 
        
        /// ----- part 3: change amount of product in cart ----- ///
        
        // update amount of product on cart page
        final var productBeforeUpdate = cartPage.getProduct(addToCartTestData.getPosition());
        cartPage.updateProductCount(addToCartTestData.getPosition(), addToCartTestData.getAmountChange()); 
        
        // validate cart page
        final String newLineItemPrice = cartPage.getProductTotalUnitPrice(addToCartTestData.getPosition());  
        cartPage.validate(shippingCosts); 
        cartPage.miniCart.validateMiniCart(addToCartTestData.getPosition(), productBeforeUpdate, addToCartTestData.getAmountChange(), newLineItemPrice);
        cartPage.validateCartItem(addToCartTestData.getPosition(), productBeforeUpdate, addToCartTestData.getAmountChange());
        
        // validate (sub)total after changing product amount
        cartPage.validateTotalAfterAdd(addToCartTestData.getPosition(), oldSubtotal3, productBeforeUpdate.getTotalPrice());
        
        // validate mini cart total product count
        totalCount = totalCount + addToCartTestData.getAmountChange() - 1; 
        cartPage.miniCart.validateTotalCount(totalCount);
        
        // store old subtotal
        final String oldSubTotal4 = cartPage.miniCart.getSubtotal();
        
        // ----- part 4: remove product on cart page ----- //
        
        // remove product on cart page cartPage.removeProduct(productToUpdatePosition);
        final String oldLineItemTotal = cartPage.getProductTotalUnitPrice(addToCartTestData.getPosition());
        cartPage.removeProduct(addToCartTestData.getPosition());
        
        // validate cart page
        cartPage.validateTotalAfterRemove(oldSubTotal4, oldLineItemTotal);
        cartPage.validate(shippingCosts);
        
        // validate mini cart total product count
        totalCount = totalCount - addToCartTestData.getAmountChange();
        cartPage.miniCart.validateTotalCount(totalCount);
        
        // ----- part 5: navigate to product and add to cart ----- //
        
        // open product page
        final var productFromCartPage = cartPage.getProduct(addToCartTestData.getPosition());
        productDetailPage = cartPage.openProductDetailPage(addToCartTestData.getPosition());
        productDetailPage.validate(productFromCartPage.getName());
        
        // add product to cart
        productDetailPage.addToCart(productFromCartPage.getSize(), productFromCartPage.getStyle());
        
        // go to cart
        cartPage = productDetailPage.miniCart.openCartPage();
        
        // validate cart page
        cartPage.validateCartItem(addToCartTestData.getPosition(), productFromCartPage, 2); 
        cartPage.miniCart.validateTotalCount(++totalCount);
        */
    }
}
