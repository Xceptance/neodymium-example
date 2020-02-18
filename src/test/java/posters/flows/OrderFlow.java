package posters.flows;

import com.xceptance.neodymium.util.AllureAddons;

import io.qameta.allure.Step;
import posters.dataobjects.OrderData;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.pageobjects.pages.checkout.BillingAddressPage;
import posters.pageobjects.pages.checkout.PaymentPage;
import posters.pageobjects.pages.checkout.ShippingAddressPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;

public class OrderFlow
{
    @Step("place order with validation")
    public static HomePage placeOrderWithValidation(OrderData orderData)
    {
        AllureAddons.addToReport("order data", orderData);
        ProductdetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());

        ShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openShippingPage();
        shippingPage.validateStructure();
        BillingAddressPage billingPage = shippingPage.sendShippingAddressForm(orderData.getShippingAddress(), orderData.getBillingAddress() == null);
        if (orderData.getBillingAddress() != null)
        {
            billingPage.validateStructure();
            billingPage.sendBillingAddressForm(orderData.getBillingAddress());
        }

        PaymentPage paymentPage = new PaymentPage();
        paymentPage.validateStructure();
        PlaceOrderPage placeOrderPage = paymentPage.sendPaymentForm(orderData.getPayment());

        placeOrderPage.validateStructure();
        placeOrderPage.validateOrder(orderData);
        return placeOrderPage.placeOrder();
    }

    @Step("place order without validation")
    public static HomePage placeOrderWithoutValidation(OrderData orderData)
    {
        AllureAddons.addToReport("order data", orderData);
        ProductdetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());

        ShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openShippingPage();
        BillingAddressPage billingPage = shippingPage.sendShippingAddressForm(orderData.getShippingAddress(), orderData.getBillingAddress() == null);
        if (orderData.getBillingAddress() != null)
        {
            billingPage.sendBillingAddressForm(orderData.getBillingAddress());
        }

        PlaceOrderPage placeOrderPage = new PaymentPage().sendPaymentForm(orderData.getPayment());

        return placeOrderPage.placeOrder();
    }
}
