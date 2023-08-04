package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;

@Owner("Joe Fix")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class GuestOrderTest extends AbstractTest
{
    @Test
    @DataSet(1)
    public void testOrderingAsGuest()
    {
        final String shippingCosts = Neodymium.dataValue("shippingCosts");
        
        final var shippingAddress = DataUtils.get(Address.class);
        final boolean sameBillingAddress = false;
        final var billingAddress = DataUtils.get(Address.class);
        final var creditCard = DataUtils.get(CreditCard.class);
        
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
        var shippingAddressPage = cartPage.openShippingAddressPage();
        shippingAddressPage.validateStructure();
        
        // go to billing address page and validate
        var billingAddressPage = shippingAddressPage.sendShippingAddressForm(shippingAddress, sameBillingAddress);
        billingAddressPage.validateStructure();
        
        // go to payment page and validate
        var paymentPage = billingAddressPage.sendBillingAddressForm(billingAddress);
        //paymentPage.validateStructure();
        
        // go to place order page and validate
        var placeOrderPage = paymentPage.sendPaymentForm(creditCard);
        placeOrderPage.validateStructure();
        placeOrderPage.validateOrderOverview(shippingAddress, billingAddress, creditCard);
        placeOrderPage.validateProduct(1, product);
        placeOrderPage.validatePriceSummary(placeOrderPage.getSubtotal(), shippingCosts);
        
        // go to order confirmation page and validate
        var orderConfirmationPage = placeOrderPage.placeOrder();
        orderConfirmationPage.validateStructure();
        
        // go to homepage
        homePage = orderConfirmationPage.openHomePage();
    }
}
