package posters.tests.newTests.checkout;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.OrderData;
import posters.flows.AddProductsToCartFlow;
import posters.flows.OpenPageFlow;
import posters.flows.OrderFlow;
import posters.pageobjects.pages.browsing.ProductDetailPage;
import posters.pageobjects.pages.checkout.BillingAddressPage;
import posters.pageobjects.pages.checkout.PaymentPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;
import posters.pageobjects.pages.checkout.ShippingAddressPage;
import posters.tests.AbstractTest;

public class CheckoutTest extends AbstractTest
{
    private OrderData orderData;

    @Before
    public void dataSetUp()
    {
        orderData = DataUtils.get(OrderData.class);
    }

    @DataSet(id = "Break-Checkout-Test")
    @Test
    public void testBreakCheckout()
    {
        OpenPageFlow.openHomePage();
        PlaceOrderPage placeOrderPage = OrderFlow.openPlaceOrderPageReturningBackAfterEachStep(orderData);
        placeOrderPage.validateOrder(orderData);
    }

    @DataSet(id = "Checkout-Without-Required-Fields-In-Shipping-Test")
    @Test
    public void testCantProceedCheckoutWithoutRequiredFieldsInShipping()
    {
        OpenPageFlow.openHomePage();
        ProductDetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());

        ShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openShippingPage();
        shippingPage.fillForm(orderData.getShippingAddress()).sendIncorrectForm();
        shippingPage.isExpectedPage();
    }

    @DataSet(id = "Checkout-Without-Required-Fields-In-Billing-Test")
    @Test
    public void testCantProceedCheckoutWithoutRequiredFieldsInBilling()
    {
        OpenPageFlow.openHomePage();
        ProductDetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());
        ShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openShippingPage();

        BillingAddressPage billingAddressPage = shippingPage.fillForm(orderData.getShippingAddress()).sendCorrectForm();
        billingAddressPage.fillForm(orderData.getBillingAddress()).sendIncorrectForm();
        billingAddressPage.isExpectedPage();
    }

    @DataSet(id = "Checkout-Without-Required-Fields-In-Payment-Test")
    @Test
    public void testCantProceedCheckoutWithoutRequiredFieldsInPayment()
    {
        OpenPageFlow.openHomePage();
        ProductDetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());
        ShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openShippingPage();
        BillingAddressPage billingAddressPage = shippingPage.fillForm(orderData.getShippingAddress()).sendCorrectForm();

        PaymentPage paymentPage = new PaymentPage();
        if (orderData.getBillingAddress() != null)
        {
            paymentPage = billingAddressPage.fillForm(orderData.getBillingAddress()).sendCorrectForm();
        }

        paymentPage.sendIncorrectForm();
        paymentPage.isExpectedPage();
    }
}
