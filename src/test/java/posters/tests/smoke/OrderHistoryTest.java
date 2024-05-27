package posters.tests.smoke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.CartCleanUpFlow;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.processes.OrderHistoryTestData;

@Owner("Lisa Smith")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class OrderHistoryTest extends AbstractTest
{
    private OrderHistoryTestData orderHistoryTestData;

    @BeforeEach
    public void setup()
    {
        orderHistoryTestData = DataUtils.get(OrderHistoryTestData.class);
    }

    @NeodymiumTest
    public void testOrderHistory()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to register page
        var registerPage = homePage.header.userMenu.openRegisterPage();

        // send register form
        var loginPage = registerPage.sendRegisterForm(orderHistoryTestData.getUser());

        // send login form
        var accountOverviewPage = loginPage.sendLoginForm(orderHistoryTestData.getUser());
        accountOverviewPage.validateSuccessfulLogin(orderHistoryTestData.getUser().getFirstName());

        // go to order history page and validate
        var orderHistoryPage = accountOverviewPage.openOrderHistory();
        orderHistoryPage.validateStructure();

        // go to account overview page
        accountOverviewPage = orderHistoryPage.openAccountOverviewPage();

        // go to address overview page and validate
        var addressOverviewPage = accountOverviewPage.openMyAddresses();

        // add new addresses
        var addNewShippingAddressPage = addressOverviewPage.openAddNewShippingAddressPage();
        addressOverviewPage = addNewShippingAddressPage.addressForm.addNewAddress(orderHistoryTestData.getAddress());
        addressOverviewPage.validateSuccessfulSave();

        var addNewBillingAddressPage = addressOverviewPage.openAddNewBillingAddressPage();
        addressOverviewPage = addNewBillingAddressPage.addressForm.addNewAddress(orderHistoryTestData.getAddress());
        addressOverviewPage.validateSuccessfulSave();

        // go to account overview page
        accountOverviewPage = addressOverviewPage.openAccountOverviewPage();

        // go to payment settings page and validate
        var paymentOverviewPage = accountOverviewPage.openPaymentSettings();

        // add new payment
        var addNewCreditCardPage = paymentOverviewPage.openAddNewCreditCardPage();
        paymentOverviewPage = addNewCreditCardPage.addNewCreditCard(orderHistoryTestData.getCreditCard());
        paymentOverviewPage.validateSuccessfulSave();

        // go to category page
        var categoryPage = paymentOverviewPage.header.topNav.clickCategory(Neodymium.localizedText(orderHistoryTestData.getTopCategory1()));

        // go to product detail page, add and store displayed product
        var productDetailPage = categoryPage.clickProductByPosition(orderHistoryTestData.getResultPosition());
        productDetailPage.addToCart(orderHistoryTestData.getsSizeProduct16x12(), orderHistoryTestData.getStyleProductMatte());

        // go to cart page
        var cartPage = productDetailPage.header.miniCart.openCartPage();
        cartPage.updateProductCount(1, orderHistoryTestData.getUpdateProductAmount());
        final var product1 = cartPage.getProduct(1);

        // go to shipping address page
        var shippingAddressPage = cartPage.openReturningCustomerShippingAddressPage();

        // go to billing address page
        var billingAddressPage = shippingAddressPage.selectShippingAddress(orderHistoryTestData.getShippingAddressPosition());

        // go to payment page
        var paymentPage = billingAddressPage.selectBillingAddress(orderHistoryTestData.getBillingAddressPosition());

        // go to place order page
        var placeOrderPage = paymentPage.selectCreditCard(orderHistoryTestData.getCreditCardPosition());
        final String orderTotal1 = placeOrderPage.getTotalOrderPrice();

        // go to order confirmation page
        var orderConfirmationPage = placeOrderPage.placeOrder();

        // go to account overview page
        accountOverviewPage = orderConfirmationPage.header.userMenu.openAccountOverviewPage();

        // go to order history page
        orderHistoryPage = accountOverviewPage.openOrderHistory();
        orderHistoryPage.validateOrder(1, 1, orderTotal1, product1);

        // go to category page
        categoryPage = orderHistoryPage.header.topNav.clickCategory(Neodymium.localizedText(orderHistoryTestData.getTopCategory2()));

        // go to product detail page, add and store displayed product
        productDetailPage = categoryPage.clickProductByPosition(orderHistoryTestData.getResultPosition());
        productDetailPage.addToCart(orderHistoryTestData.getsSizeProduct32x24(), orderHistoryTestData.getStyleProductGloss());

        // go to category page
        categoryPage = productDetailPage.header.topNav.clickCategory(Neodymium.localizedText(orderHistoryTestData.getTopCategory3()));

        // go to product detail page, add and store displayed product
        productDetailPage = categoryPage.clickProductByPosition(orderHistoryTestData.getResultPosition());
        productDetailPage.addToCart(orderHistoryTestData.getsSizeProduct64x48(), orderHistoryTestData.getStyleProductMatte());

        // go to cart page
        cartPage = productDetailPage.header.miniCart.openCartPage();
        cartPage.updateProductCount(2, orderHistoryTestData.getUpdateProductAmount());
        final var product2 = cartPage.getProduct(1);
        final var product3 = cartPage.getProduct(2);

        // go to shipping address page
        shippingAddressPage = cartPage.openReturningCustomerShippingAddressPage();

        // go to billing address page
        billingAddressPage = shippingAddressPage.selectShippingAddress(orderHistoryTestData.getShippingAddressPosition());

        // go to payment page
        paymentPage = billingAddressPage.selectBillingAddress(orderHistoryTestData.getBillingAddressPosition());

        // go to place order page
        placeOrderPage = paymentPage.selectCreditCard(orderHistoryTestData.getCreditCardPosition());
        final var orderTotal2 = placeOrderPage.getTotalOrderPrice();

        // go to order confirmation page
        orderConfirmationPage = placeOrderPage.placeOrder();

        // go to account overview page
        accountOverviewPage = orderConfirmationPage.header.userMenu.openAccountOverviewPage();

        // go to order history page
        orderHistoryPage = accountOverviewPage.openOrderHistory();
        orderHistoryPage.validateOrder(1, 1, orderTotal2, product2);
        orderHistoryPage.validateOrder(1, 2, orderTotal2, product3);
        orderHistoryPage.validateOrder(2, 1, orderTotal1, product1);
    }

    @AfterEach
    public void after()
    {
        CartCleanUpFlow.flow();
        DeleteUserFlow.flow(orderHistoryTestData.getUser());
    }
}