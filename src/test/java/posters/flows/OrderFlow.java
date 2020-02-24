package posters.flows;

import io.qameta.allure.Step;
import posters.dataobjects.Address;
import posters.dataobjects.AddressContainer;
import posters.dataobjects.CreditCard;
import posters.dataobjects.OrderData;
import posters.flows.command.AddSecondAddress;
import posters.flows.command.Command;
import posters.flows.command.EnterDataAndProceedWithValidation;
import posters.flows.command.EnterDataAndProceedWithoutValidation;
import posters.flows.command.GoBackForValidationCommand;
import posters.flows.command.SelectAddressAndValidatePage;
import posters.pageobjects.pages.browsing.CartPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductDetailPage;
import posters.pageobjects.pages.checkout.BillingAddressPage;
import posters.pageobjects.pages.checkout.BillingAddresssListPage;
import posters.pageobjects.pages.checkout.PaymentListPage;
import posters.pageobjects.pages.checkout.PaymentPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;
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

    @Step("place order without validation returning bach after each step")
    public static PlaceOrderPage openPlaceOrderPageReturningBackAfterEachStep(OrderData orderData)
    {
        return openPlaceOrderPage(orderData, new GoBackForValidationCommand());
    }

    private static PlaceOrderPage openPlaceOrderPage(OrderData orderData, Command flow)
    {
        ProductDetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());

        ShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openShippingPage();

        BillingAddressPage billingAddressPage = (BillingAddressPage) flow.execute(shippingPage, orderData.getShippingAddress());

        PaymentPage paymentPage = new PaymentPage();
        if (orderData.getBillingAddress() != null)
        {
            paymentPage = (PaymentPage) flow.execute(billingAddressPage, orderData.getBillingAddress());
        }

        return (PlaceOrderPage) flow.execute(paymentPage, orderData.getPayment());
    }

    public static HomePage placeOrderWithValidationForUserWithSavedData(OrderData orderData)
    {
        return placeOrderForUserWithSavedData(orderData, new SelectAddressAndValidatePage());
    }

    public static HomePage placeOrderWithValidationForUserWithSecondSavedData(OrderData orderData)
    {
        return placeOrderForUserWithSavedData(orderData, new AddSecondAddress());
    }

    @Step("place order with validation for user with saved data")
    public static HomePage placeOrderForUserWithSavedData(OrderData orderData, Command flow)
    {

        CartPage cartPage = AddProductsToCartFlow.addToCart(orderData.getProducts()).miniCart.openCartPage();

        if (orderData.getShippingAddress().isSavedInAccount())
        {
            flow.execute(cartPage.openShippingPageForRegisteredUserWithSavedAddress(), orderData.getShippingAddress());
        }
        else
        {
            cartPage.openShippingPage().fillForm(orderData.getShippingAddress()).sendCorrectForm();
        }
        if (orderData.getBillingAddress() != null)
        {
            if (orderData.getBillingAddress().isSavedInAccount())
            {
                flow.execute(new BillingAddresssListPage(), orderData.getBillingAddress());
            }
            else
            {
                new BillingAddressPage().fillForm(orderData.getBillingAddress()).sendCorrectForm();
            }
        }
        else if (orderData.getShippingAddress().isSavedInAccount() && orderData.getSecondShippingAddress() == null)
        {
            new BillingAddressPage().fillForm(orderData.getShippingAddress()).sendCorrectForm();
        }

        PlaceOrderPage placeOrderPage;
        if (orderData.getPayment().isSavedInAccount())
        {
            placeOrderPage = (PlaceOrderPage) flow.execute(new PaymentListPage(), orderData.getPayment());
        }
        else
        {
            placeOrderPage = new PaymentPage().fillForm(orderData.getPayment()).sendCorrectForm();
        }
        return placeOrderPage.validateOrder(orderData).placeOrder();
    }

    @Step("place order with validation for user with saved data")
    public static HomePage placeOrderAndAddSecondAddressForUserWithSavedData(OrderData orderData, AddressContainer secondAddress)
    {
        {

            CartPage cartPage = AddProductsToCartFlow.addToCart(orderData.getProducts()).miniCart.openCartPage();

            if (orderData.getShippingAddress().isSavedInAccount())
            {
                cartPage.openShippingPageForRegisteredUserWithSavedAddress().addNewShippingAddressFromListPage.openBillingAddressListPage(orderData.getShippingAddress());
            }
            else
            {
                cartPage.openShippingPage().fillForm(orderData.getShippingAddress()).sendCorrectForm();
            }
            if (orderData.getBillingAddress() != null)
            {
                if (orderData.getBillingAddress().isSavedInAccount())
                {
                    new BillingAddresssListPage().addNewAddressFromListPage.sendBillingAddressForm((Address) secondAddress);
                }
                else
                {
                    new BillingAddressPage().fillForm(orderData.getBillingAddress()).sendCorrectForm();
                }
            }
            else if (orderData.getShippingAddress().isSavedInAccount())
            {
                new BillingAddressPage().fillForm(orderData.getShippingAddress()).sendCorrectForm();
            }

            PlaceOrderPage placeOrderPage;
            if (orderData.getPayment().isSavedInAccount())
            {
                placeOrderPage = new PaymentListPage().newPaymentOverlay.addPayment((CreditCard) secondAddress);
            }
            else
            {
                placeOrderPage = new PaymentPage().fillForm(orderData.getPayment()).sendCorrectForm();
            }
            return placeOrderPage.validateOrder(orderData).placeOrder();
        }
    }
}
