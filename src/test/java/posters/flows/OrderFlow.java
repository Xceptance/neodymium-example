package posters.flows;

import io.qameta.allure.Step;
import posters.dataobjects.OrderData;
import posters.flows.command.Command;
import posters.flows.command.EnterDataAndProceedWithValidation;
import posters.flows.command.EnterDataAndProceedWithoutValidation;
import posters.flows.command.GoBackForValidationCommand;
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
        PlaceOrderPage placeOrderPage = openPlaceOrderPage(orderData, new EnterDataAndProceedWithValidation());

        placeOrderPage.validateStructure();
        placeOrderPage.validateOrder(orderData);
        return placeOrderPage.placeOrder();
    }

    @Step("place order without validation")
    public static HomePage placeOrderWithoutValidation(OrderData orderData)
    {
        return openPlaceOrderPage(orderData, new EnterDataAndProceedWithoutValidation()).placeOrder();
    }

    @Step("place order without validation")
    public static PlaceOrderPage openPlaceOrderPageReturningBackAfterEachStep(OrderData orderData)
    {
        return openPlaceOrderPage(orderData, new GoBackForValidationCommand());
    }

    public static PlaceOrderPage openPlaceOrderPage(OrderData orderData, Command flow)
    {
        ProductdetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());

        ShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openShippingPage();

        BillingAddressPage billingAddressPage = (BillingAddressPage) flow.execute(shippingPage, orderData.getShippingAddress());

        PaymentPage paymentPage = new PaymentPage();
        if (orderData.getBillingAddress() != null)
        {
            paymentPage = (PaymentPage) flow.execute(billingAddressPage, orderData.getBillingAddress());
        }

        return (PlaceOrderPage) flow.execute(paymentPage, orderData.getPayment());
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
