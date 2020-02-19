package posters.flows;

import io.qameta.allure.Step;
import posters.dataobjects.OrderData;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.pageobjects.pages.checkout.BillingAddressPage;
import posters.pageobjects.pages.checkout.BillingAddresssListPage;
import posters.pageobjects.pages.checkout.PaymentListPage;
import posters.pageobjects.pages.checkout.PaymentPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;
import posters.pageobjects.pages.checkout.ShippingAddressListPage;
import posters.pageobjects.pages.checkout.ShippingAddressPage;

public class OrderFlow
{
    @Step("place order with validation")
    public static HomePage placeOrderWithValidation(OrderData orderData)
    {
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

    @Step("place order with validation for user with saved data")
    public static HomePage placeOrderWithValidationForUserWithSavedData(OrderData orderData)
    {

        ShippingAddressListPage shippingAddressListPage = AddProductsToCartFlow.addToCart(orderData.getProducts()).miniCart.openCartPage()
                                                                                                                           .openShippingPageForRegisteredUserWithSavedAddress();
        shippingAddressListPage.validateStructure();

        BillingAddresssListPage billingAddresssListPage = shippingAddressListPage.openBillingAddresssListPage();
        billingAddresssListPage.validateStructure();

        PaymentListPage paymentListPage = billingAddresssListPage.openPaymentListPage();
        paymentListPage.validateStructure();

        PlaceOrderPage placeOrderPage = paymentListPage.selectCreditCard(1);
        placeOrderPage.validateStructure();

        placeOrderPage.validateOrder(orderData);
        return placeOrderPage.placeOrder();
    }
}
