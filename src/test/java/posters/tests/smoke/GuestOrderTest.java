package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.dataobjects.Address;
import posters.dataobjects.CreditCard;
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
        int totalCount = 0;
        
        final var shippingAddress = DataUtils.get(Address.class);
        final boolean sameBillingAddress = false;
        final var billingAddress = DataUtils.get(Address.class);
        final var creditCard = DataUtils.get(CreditCard.class);
        
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // store old subtotal
        final String oldSubtotal = homePage.miniCart.getSubtotal();

        // go to category page
        var categoryPage = homePage.topNav.clickCategory(guestOrderTestData.getTopCategory());
        
        // go to product detail page, add and store displayed product
        var productDetailPage = categoryPage.clickProductByPosition(guestOrderTestData.getResultPosition());
        productDetailPage.addToCart(guestOrderTestData.getsSizeProduct(), guestOrderTestData.getStyleProduct());
        
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
        paymentPage.validateStructure();
        
        
        /*


        // Send billing address and validate payment form
        var newPaymentPage = newBillingAddressPage.sendBillingAddressForm(billingAddress);
        newPaymentPage.validateStructure();

        // Send payment data and validate place order page
        var placeOrderPage = newPaymentPage.sendPaymentForm(creditCard);
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateAddressesAndPayment(shippingAddress, billingAddress, creditCard);

        // Place order
        //homePage = placeOrderPage.placeOrder();
        
        // Place Order
        var orderConfirmationPage = placeOrderPage.placeOrder();
        
        // Validate order confirmation on the Order Confirmation page
        orderConfirmationPage.validate();
        orderConfirmationPage.validateSuccessfulOrder();
        
        //navigate to the Home Page
        homePage = orderConfirmationPage.goHome();
       

        //Validate home page
        homePage.validateStructure();
        */
    }
}
