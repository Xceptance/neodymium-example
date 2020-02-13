package posters.flows;

import com.xceptance.neodymium.util.AllureAddons;

import io.qameta.allure.Step;
import posters.dataobjects.OrderData;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.pageobjects.pages.checkout.NewBillingAddressPage;
import posters.pageobjects.pages.checkout.NewPaymentPage;
import posters.pageobjects.pages.checkout.NewShippingAddressPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;

public class OrderFlow
{
    @Step("place order with validation")
    public static HomePage placeOrderWithValidation(OrderData orderData)
    {
        AllureAddons.addToReport("order data", orderData);
        ProductdetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());

        NewShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openNewShippingPage();
        shippingPage.validateStructure();
        NewBillingAddressPage billingPage = shippingPage.sendShippingAddressForm(orderData.getShippingAddress(), orderData.getBillingAddress() == null);
        if (orderData.getBillingAddress() != null)
        {
            billingPage.validateStructure();
            billingPage.sendBillingAddressForm(orderData.getBillingAddress());
        }

        NewPaymentPage paymentPage = new NewPaymentPage();
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

        NewShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openNewShippingPage();
        NewBillingAddressPage billingPage = shippingPage.sendShippingAddressForm(orderData.getShippingAddress(), orderData.getBillingAddress() == null);
        if (orderData.getBillingAddress() != null)
        {
            billingPage.sendBillingAddressForm(orderData.getBillingAddress());
        }

        PlaceOrderPage placeOrderPage = new NewPaymentPage().sendPaymentForm(orderData.getPayment());

        return placeOrderPage.placeOrder();
    }
}
