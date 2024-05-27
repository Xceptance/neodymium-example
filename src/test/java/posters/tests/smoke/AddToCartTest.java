package posters.tests.smoke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.CartCleanUpFlow;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.processes.AddToCartTestData;

@Owner("Joe Fix")
@Severity(SeverityLevel.CRITICAL)
@Tag("smoke")
public class AddToCartTest extends AbstractTest
{
    private AddToCartTestData addToCartTestData;

    private String shippingCosts;

    @BeforeEach
    public void setup()
    {
        shippingCosts = Neodymium.dataValue("shippingCosts");
        addToCartTestData = DataUtils.get(AddToCartTestData.class);
    }

    @NeodymiumTest
    @DataSet(1)
    @DataSet(2)
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
        var productDetailPage = subCategoryPage.clickProductByPosition(addToCartTestData.getSubCategoryResultPosition());
        productDetailPage.addToCart(addToCartTestData.getSizeFirstProduct(), addToCartTestData.getStyleFirstProduct());
        final var product = productDetailPage.getProduct();

        // go to cart page
        var cartPage = productDetailPage.header.miniCart.openCartPage();

        // validate cart page
        cartPage.validateCartItem(1, product);
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        cartPage.validateTotalAfterAdd(1, oldSubtotal, 0.00);
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(1, product);

        /// ========== PART 2: USE SEARCH BAR TO ADD PRODUCT TO CART ========== ///

        // store old subtotal
        final String oldSubtotal2 = cartPage.header.miniCart.getSubtotal();

        // go to category page via search
        var categoryPage = cartPage.header.search.categoryPageResult(addToCartTestData.getSearchTerm());

        // go to product detail page, add and store displayed product
        productDetailPage = categoryPage.clickProductByPosition(addToCartTestData.getSearchResultPosition());
        productDetailPage.addToCart(addToCartTestData.getSizeSecondProduct(), addToCartTestData.getStyleSecondProduct());
        final var product2 = productDetailPage.getProduct();

        // go to cart page
        cartPage = productDetailPage.header.miniCart.openCartPage();

        // validate cart page
        cartPage.validateCartItem(1, product2);
        cartPage.validateCartItem(2, product);
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        cartPage.validateTotalAfterAdd(1, oldSubtotal2, 0.00);
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(1, product2);
        cartPage.header.miniCart.validateMiniCartItem(2, product);

        /// ========== PART 3: CHANGE QUANTITY OF PRODUCT IN CART ========== ///

        // store old subtotal
        final String oldSubtotal3 = cartPage.header.miniCart.getSubtotal();

        // store product before update
        final var productBeforeUpdate = cartPage.getProduct(1);

        // update amount of product on cart page
        cartPage.updateProductCount(1, addToCartTestData.getAmountChange());
        cartPage.waitForProductUpdate(oldSubtotal3);

        // store subtotal of updated product
        String subtotalAfterUpdate = cartPage.getProductTotalPrice(1);

        // validate cart page
        cartPage.validateCartItem(1, productBeforeUpdate, addToCartTestData.getAmountChange());
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        cartPage.validateTotalAfterAdd(1, oldSubtotal3, productBeforeUpdate.getTotalPrice());
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(1, productBeforeUpdate, addToCartTestData.getAmountChange(), subtotalAfterUpdate);

        /// ========== PART 4: REMOVE PRODUCT FROM CART ========== ///

        // store old subtotal
        final String oldSubtotal4 = cartPage.header.miniCart.getSubtotal();

        // store subtotal product before remove
        final String subtotalBeforeRemove = cartPage.getProductTotalPrice(1);

        // remove first product on cart page
        cartPage.removeProduct(1);
        cartPage.waitForProductUpdate(oldSubtotal4);

        // validate cart page
        cartPage.validateCartItem(1, product);
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        cartPage.validateTotalAfterRemove(oldSubtotal4, subtotalBeforeRemove);
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(1, product);

        /// ========== PART 5: ADD SAME PRODUCT TO CART AGAIN ========== ///

        // store old subtotal
        final String oldSubtotal5 = cartPage.header.miniCart.getSubtotal();

        // store product on cart page
        final var productFromCartPageBefore = cartPage.getProduct(1);

        // go to sub category page
        subCategoryPage = cartPage.header.topNav.clickSubCategory(Neodymium.localizedText(addToCartTestData.getTopCategory()),
                                                                  Neodymium.localizedText(addToCartTestData.getSubCategory()));

        // go to product detail page, add and store displayed product
        productDetailPage = subCategoryPage.clickProductByPosition(addToCartTestData.getSubCategoryResultPosition());
        productDetailPage.addToCart(productFromCartPageBefore.getSize(), productFromCartPageBefore.getStyle());

        // go to cart
        cartPage = productDetailPage.header.miniCart.openCartPage();

        // store subtotal of updated product
        subtotalAfterUpdate = cartPage.getProductTotalPrice(1);
        final var productFromCartPageAfter = cartPage.getProduct(1);

        // validate cart page
        cartPage.validateCartItem(1, productFromCartPageBefore, productFromCartPageAfter.getAmount());
        cartPage.validate(shippingCosts, cartPage.header.miniCart.getSubtotal());
        cartPage.validateTotalAfterAdd(1, oldSubtotal5, productFromCartPageBefore.getTotalPrice());
        cartPage.header.miniCart.validateStructure();
        cartPage.header.miniCart.validateMiniCartItem(1, productFromCartPageBefore, cartPage.header.miniCart.getTotalCount(), subtotalAfterUpdate);

        // go to homepage
        homePage = cartPage.openHomePage();
    }

    @AfterEach
    public void after()
    {
        CartCleanUpFlow.flow();
    }
}
