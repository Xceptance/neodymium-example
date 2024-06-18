package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.common.WorkInProgress;
import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.checkout.GuestPaymentPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;
import posters.tests.AbstractTest;
import posters.tests.testdata.processes.GuestOrderTestData;

@Owner("Joe Fix")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class GuestOrderTest extends AbstractTest
{
    @DataItem
    private GuestOrderTestData guestOrderTestData;

    @DataItem
    private String shippingCosts;

    @WorkInProgress
    @Test
    public void testOrderingAsGuest()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to category page
        var categoryPage = homePage.header.topNav.clickCategory(Neodymium.localizedText(guestOrderTestData.getTopCategory()));

        // go to product detail page, add and store displayed product
        final var testDataProduct = guestOrderTestData.getProduct();
        var productDetailPage = categoryPage.clickProductByName(testDataProduct.getName());
        productDetailPage.addToCart(testDataProduct.getSize(), testDataProduct.getStyle());

        // go to cart page
        var cartPage = productDetailPage.header.miniCart.openCartPage();
        cartPage.updateProductCount(testDataProduct.getName(), testDataProduct.getAmount());
        final var product = cartPage.getProduct(1);

        // go to shipping address page and validate
        var shippingAddressPage = cartPage.openGuestShippingAddressPage();
        shippingAddressPage.validateStructure();

        // declare variables used in if
        GuestPaymentPage paymentPage;
        PlaceOrderPage placeOrderPage;

        if (!guestOrderTestData.getSameShippingAndBillingAddress())
        {
            // go to billing address page and validate
            var billingAddressPage = shippingAddressPage.addressForm.goToGuestBillingAddressPage(guestOrderTestData.getShippingAddress());
            billingAddressPage.validateStructure();

            // go to payment page and validate
            paymentPage = billingAddressPage.addressForm.goToGuestPaymentPage(guestOrderTestData.getBillingAddress());
            paymentPage.validateStructure();

            // go to place order page and validate order overview
            placeOrderPage = paymentPage.goToPlaceOrderPage(guestOrderTestData.getCreditCard());
            placeOrderPage.validateOrderOverview(guestOrderTestData.getShippingAddress(), guestOrderTestData.getBillingAddress(),
                                                 guestOrderTestData.getCreditCard());
        }
        else
        {
            // go to payment page and validate
            paymentPage = shippingAddressPage.addressForm.goToGuestPaymentPage(guestOrderTestData.getShippingAddress());
            paymentPage.validateStructure();

            // go to place order page and validate order overview
            placeOrderPage = paymentPage.goToPlaceOrderPage(guestOrderTestData.getCreditCard());
            placeOrderPage.validateOrderOverview(guestOrderTestData.getShippingAddress(), guestOrderTestData.getShippingAddress(),
                                                 guestOrderTestData.getCreditCard());
        }

        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product);
        placeOrderPage.validatePriceSummary(shippingCosts);

        // go to order confirmation page and validate
        var orderConfirmationPage = placeOrderPage.placeOrder();
        orderConfirmationPage.validateStructure();

        // go to homepage
        homePage = orderConfirmationPage.openHomePage();
    }
    
    // Intentionally not executed because of missing WorkInProgress annotation.
    // For Showcase purposes only.
    @Test
    public void testOrderingAsGuestPayPal() 
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to category page
        var categoryPage = homePage.header.topNav.clickCategory(Neodymium.localizedText(guestOrderTestData.getTopCategory()));
        
        // go to product detail page, add and store displayed product
        final var testDataProduct = guestOrderTestData.getProduct();
        var productDetailPage = categoryPage.clickProductByName(testDataProduct.getName());
        productDetailPage.addToCart(testDataProduct.getSize(), testDataProduct.getStyle());
    }
}
