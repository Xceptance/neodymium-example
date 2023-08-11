package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.components.UserMenu;
import posters.pageobjects.pages.checkout.GuestPaymentPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;
import posters.tests.AbstractTest;

@Owner("Joe Fix")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class GuestOrderTest extends AbstractTest
{    
    @Test
    @DataSet(1)
    @DataSet(2)
    public void testOrderingAsGuest()
    {        
        final String shippingCosts = Neodymium.dataValue("shippingCosts");
        
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to category page
        var categoryPage = homePage.topNav.clickCategory(guestOrderTestData.getTopCategory());
        
        // go to product detail page, add and store displayed product
        var productDetailPage = categoryPage.clickProductByPosition(guestOrderTestData.getResultPosition());
        productDetailPage.addToCart(guestOrderTestData.getsSizeProduct(), guestOrderTestData.getStyleProduct());
        final var product = productDetailPage.getProduct();
        
        // go to cart page
        var cartPage = productDetailPage.miniCart.openCartPage();
        
        // go to shipping address page and validate
        var shippingAddressPage = cartPage.openGuestShippingAddressPage();
        shippingAddressPage.validateStructure();
        
        // declare variables used in if
        GuestPaymentPage paymentPage;
        PlaceOrderPage placeOrderPage;
        
        if (!guestOrderTestData.getShipAddrEqualBillAddr()) 
        {
            // go to billing address page and validate
            var billingAddressPage = shippingAddressPage.goToGuestBillingAddressPage(guestOrderTestData.getShippingAddress());
            billingAddressPage.validateStructure();
        
            // go to payment page and validate
            paymentPage = billingAddressPage.goToGuestPaymentPage(guestOrderTestData.getBillingAddress());
            paymentPage.validateStructure();
            
            // go to place order page and validate order overview
            placeOrderPage = paymentPage.goToPlaceOrderPage(guestOrderTestData.getCreditCard());
            placeOrderPage.validateOrderOverview(guestOrderTestData.getShippingAddress(), guestOrderTestData.getBillingAddress(), guestOrderTestData.getCreditCard());
        }
        else
        {
            // go to payment page and validate
            paymentPage = shippingAddressPage.goToGuestPaymentPage(guestOrderTestData.getShippingAddress());
            paymentPage.validateStructure();
            
            // go to place order page and validate order overview
            placeOrderPage = paymentPage.goToPlaceOrderPage(guestOrderTestData.getCreditCard());
            placeOrderPage.validateOrderOverview(guestOrderTestData.getShippingAddress(), guestOrderTestData.getShippingAddress(), guestOrderTestData.getCreditCard());
        }
        
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product);
        placeOrderPage.validatePriceSummary(placeOrderPage.getSubtotal(), shippingCosts);
        
        // go to order confirmation page and validate
        var orderConfirmationPage = placeOrderPage.placeOrder();
        orderConfirmationPage.validateStructure();
       
        // go to homepage
        homePage = orderConfirmationPage.openHomePage();
    }
}
