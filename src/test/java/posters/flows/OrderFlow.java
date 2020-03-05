package posters.flows;

import com.codeborne.selenide.Selenide;

import io.qameta.allure.Step;
import posters.dataobjects.OrderData;
import posters.pageobjects.pages.browsing.CartPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductDetailPage;
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
        PlaceOrderPage placeOrderPage = openPlaceOrderPage(orderData, () -> {
            ShippingAddressPage shippingAddressPage = new ShippingAddressPage();
            shippingAddressPage.validateStructure();
        }, () -> {
            BillingAddressPage billingAddressPage = new BillingAddressPage();
            billingAddressPage.validateStructure();
        }, () -> {
            PaymentPage paymentPage = new PaymentPage();
            paymentPage.validateStructure();
        });

        placeOrderPage.validateStructure();
        placeOrderPage.validateOrder(orderData);
        return placeOrderPage.placeOrder();
    }

    @Step("place order without validation")
    public static HomePage placeOrderWithoutValidation(OrderData orderData)
    {
        return openPlaceOrderPage(orderData, () -> {
        }, () -> {
        }, () -> {
        }).placeOrder();
    }

    @Step("place order without validation returning bach after each step")
    public static PlaceOrderPage openPlaceOrderPageReturningBackAfterEachStep(OrderData orderData)
    {
        return openPlaceOrderPage(orderData, () -> {
            ShippingAddressPage shippingAddressPage = new ShippingAddressPage();
            shippingAddressPage.sendCorrectForm();
            Selenide.back();
            shippingAddressPage.isExpectedPage();
            shippingAddressPage.validateEnteredData(orderData.getShippingAddress());
        }, () -> {
            BillingAddressPage billingAddressPage = new BillingAddressPage();
            billingAddressPage.sendCorrectForm();
            Selenide.back();
            billingAddressPage.isExpectedPage();
            billingAddressPage.validateEnteredData(orderData.getBillingAddress());
        }, () -> {
            PaymentPage paymentPage = new PaymentPage();
            paymentPage.sendCorrectForm();
            Selenide.back();
            paymentPage.isExpectedPage();
            paymentPage.validateEnteredData(orderData.getPayment());
        });
    }

    private static PlaceOrderPage openPlaceOrderPage(OrderData orderData, Runnable additionalActionShippingPage,
                                                     Runnable additionalActionBillingPage,
                                                     Runnable additionalActionPaymentPage)
    {
        ProductDetailPage pdp = AddProductsToCartFlow.addToCart(orderData.getProducts());

        ShippingAddressPage shippingPage = pdp.miniCart.openCartPage().openShippingPage();
        shippingPage.isExpectedPage();
        shippingPage.fillForm(orderData.getShippingAddress());
        additionalActionShippingPage.run();
        shippingPage.sendCorrectForm();

        if (orderData.getBillingAddress() != null)
        {
            BillingAddressPage billingPage = new BillingAddressPage();
            billingPage.isExpectedPage();
            billingPage.fillForm(orderData.getBillingAddress());
            additionalActionBillingPage.run();
            billingPage.sendCorrectForm();
        }

        PaymentPage paymentPage = new PaymentPage();
        paymentPage.isExpectedPage();
        paymentPage.fillForm(orderData.getPayment());
        additionalActionPaymentPage.run();
        return paymentPage.sendCorrectForm();
    }

    public static HomePage placeOrderWithValidationForUserWithSavedData(OrderData orderData)
    {
        return placeOrderForUserWithSavedData(orderData, () -> {
            ShippingAddressListPage shippingAddressListPage = new ShippingAddressListPage().validateStructure();
            shippingAddressListPage.selectAddressByPosition(1);
        }, () -> {
            BillingAddresssListPage billingAddresssListPage = new BillingAddresssListPage().validateStructure();
            billingAddresssListPage.selectAddressByPosition(1);
        }, () -> {
            PaymentListPage paymentListPage = new PaymentListPage().validateStructure();
            paymentListPage.selectAddressByPosition(1);
        });
    }

    public static HomePage placeOrderWithValidationForUserWithSecondSavedData(OrderData orderData)
    {
        return placeOrderForUserWithSavedData(orderData, () -> {
            ShippingAddressListPage shippingAddressListPage = new ShippingAddressListPage();
            shippingAddressListPage.newShippingAddressOverlay.fillForm(orderData.getSecondShippingAddress()).sendForm();
        }, () -> {
            BillingAddresssListPage billingAddresssListPage = new BillingAddresssListPage();
            billingAddresssListPage.newAddressOverlay.fillForm(orderData.getSecondBillingAddress()).sendForm();
        }, () -> {
            PaymentListPage paymentListPage = new PaymentListPage();
            paymentListPage.newPaymentOverlay.fillForm(orderData.getSecondPayment()).sendForm();
        });
    }

    @Step("place order with validation for user with saved data")
    public static HomePage placeOrderForUserWithSavedData(OrderData orderData, Runnable shippingPageAction,
                                                          Runnable billingPageAction,
                                                          Runnable paymentPageAction)
    {

        CartPage cartPage = AddProductsToCartFlow.addToCart(orderData.getProducts()).miniCart.openCartPage();

        if (orderData.getShippingAddress().isSavedInAccount())
        {
            cartPage.openShippingPageForRegisteredUserWithSavedAddress();
            shippingPageAction.run();
        }
        else
        {
            cartPage.openShippingPage().fillForm(orderData.getShippingAddress()).sendCorrectForm();
        }
        if (orderData.getBillingAddress() != null)
        {
            if (orderData.getBillingAddress().isSavedInAccount())
            {
                billingPageAction.run();
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

        PlaceOrderPage placeOrderPage = new PlaceOrderPage();
        if (orderData.getPayment().isSavedInAccount())
        {
            paymentPageAction.run();
        }
        else
        {
            placeOrderPage = new PaymentPage().fillForm(orderData.getPayment()).sendCorrectForm();
        }
        return placeOrderPage.validateOrder(orderData).placeOrder();
    }
}
