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

        // go to homepage
        var homePage = OpenHomePageFlow.flow();
        
        // validate empty mini cart window
        int totalCount = 0;
        homePage.miniCart.validateTotalCount(totalCount);
        homePage.miniCart.validateSubtotal("$0.00");
        
        // ???
        final String oldSubtotal = homePage.miniCart.getSubtotal();

        // go to top category page {topCategory}
        final String topCategory = addToCartTestData.getTopCategory();
        var categoryPage = homePage.topNav.clickCategory(topCategory);
        categoryPage.validate(topCategory);

        // go to sub category page {subCategory}
        final String subCategory = addToCartTestData.getSubCategory();
        var subCategoryPage = categoryPage.topNav.clickSubCategory(topCategory, subCategory);
        subCategoryPage.validate(subCategory);
        
        // go to product page
        final String productName = categoryPage.getProductNameByPosition(addToCartTestData.getPosition());
        var productDetailPage = categoryPage.clickProductByPosition(addToCartTestData.getPosition());
        productDetailPage.validate(productName);
        
        // add product to cart
        productDetailPage.addToCart("16 x 12 in", "matte");
        
        // go to cart
        final var product = productDetailPage.getProduct();
        var cartPage = productDetailPage.miniCart.openCartPage();
        
        // validate cart page
        final String shippingCosts = Neodymium.dataValue("shippingCosts");
        cartPage.validate(shippingCosts);
        cartPage.miniCart.validateMiniCart(addToCartTestData.getPosition(), product);
        cartPage.validateCartItem(addToCartTestData.getPosition(), product);
        
        
        
        // ----------------------------------------------------------------


        /*
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal, 0.00);

        final String oldSubtotal2 = cartPage.miniCart.getSubtotal();
        cartPage.miniCart.validateTotalCount(++totalCount);

        // Search for product on cart page
        final String searchTerm = Neodymium.dataValue("searchTerm");
        final int searchTermExpectedCount = 1;
        categoryPage = cartPage.search.categoryPageResult(searchTerm);
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);
        final String productName2 = categoryPage.getProductNameByPosition(1, 1);

        // Go to product page and add to cart
        productDetailPage = categoryPage.clickProductByPosition(1, 1);
        productDetailPage.validate(productName2);
        productDetailPage.addToCart("64 x 48 in", "gloss");
        final var product2 = productDetailPage.getProduct();

        // Go to cart and validate
        cartPage = productDetailPage.miniCart.openCartPage();
        cartPage.validate(shippingCosts);

        cartPage.miniCart.validateMiniCart(1, product2);

        cartPage.miniCart.validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product2);
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal2, 0.00);

        int productToUpdatePosition = 1;
        int newProductAmount = 3;
        final String oldSubtotal3 = cartPage.miniCart.getSubtotal();
        final var productBeforeUpdate = cartPage.getProduct(productToUpdatePosition);

        // Update amount of product on cart page
        cartPage.updateProductCount(productToUpdatePosition, newProductAmount);
        cartPage.validateProductAmount(productToUpdatePosition, newProductAmount);

        final String newLinItemPrice = cartPage.getProductTotalUnitPrice(productToUpdatePosition);
        cartPage.validateSubAndLineItemTotalAfterAdd(productToUpdatePosition,
                                                     oldSubtotal3,
                                                     productBeforeUpdate.getTotalPrice());
        cartPage.validateCartItem(1, productBeforeUpdate, newProductAmount);
        cartPage.miniCart.validateMiniCart(1, productBeforeUpdate, newProductAmount, newLinItemPrice);
        totalCount = totalCount + newProductAmount - 1;
        cartPage.miniCart.validateTotalCount(totalCount);

        final String oldLineItemTotal = cartPage.getProductTotalUnitPrice(productToUpdatePosition);
        final String oldSubTotal4 = cartPage.miniCart.getSubtotal();

        // Remove product on cart page
        cartPage.removeProduct(productToUpdatePosition);
        cartPage.validateSubAndLineItemTotalAfterRemove(oldSubTotal4, oldLineItemTotal);
        totalCount = totalCount - newProductAmount;
        cartPage.miniCart.validateTotalCount(totalCount);

        final var productFromCartPage = cartPage.getProduct(1);
        productDetailPage = cartPage.openProductPage(1);
        productDetailPage.validate(productFromCartPage.getName());
        productDetailPage.addToCart(productFromCartPage.getSize(), productFromCartPage.getStyle());
        cartPage = productDetailPage.miniCart.openCartPage();

        cartPage.validateCartItem(1, productFromCartPage, 2);
        cartPage.miniCart.validateTotalCount(++totalCount);
        */
    }
}
