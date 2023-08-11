package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.components.UserMenu;
import posters.tests.AbstractTest;

@Owner("Lisa Smith")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class RegisteredOrderTest extends AbstractTest
{
    @DataSet(1)
    //@DataSet(2)
    @Test
    public void testOrderingAsRegisteredUser()
    {
        final String shippingCosts = Neodymium.dataValue("shippingCosts");

        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to login page
        var loginPage = homePage.userMenu.openLoginPage();

        // send login form
        homePage = loginPage.sendLoginForm(registeredOrderTestData.getUser());
        homePage.validateSuccessfulLogin(registeredOrderTestData.getUser().getFirstName());

        // go to category page
        var categoryPage = homePage.topNav.clickCategory(registeredOrderTestData.getTopCategory());

        // go to product detail page, add and store displayed product
        var productDetailPage = categoryPage.clickProductByPosition(registeredOrderTestData.getResultPosition());
        productDetailPage.addToCart(registeredOrderTestData.getsSizeProduct(), registeredOrderTestData.getStyleProduct());
        final var product = productDetailPage.getProduct();

        // go to cart page
        var cartPage = productDetailPage.miniCart.openCartPage();

        // go to shipping address page
        var shippingAddressPage = cartPage.openReturningCustomerShippingAddressPage();
        shippingAddressPage.validateStructure();
        shippingAddressPage.validateAddressContainer(registeredOrderTestData.getShipAddrPos(), registeredOrderTestData.getShippingAddress());

        // go to billing address page
        var billingAddressPage = shippingAddressPage.selectShippingAddress(registeredOrderTestData.getShipAddrPos());
        billingAddressPage.validateStructure();

        if (!registeredOrderTestData.getShipAddrEqualBillAddr())
        {
            billingAddressPage.validateAddressContainer(registeredOrderTestData.getBillAddrPos(), registeredOrderTestData.getBillingAddress());
        }
        else
        {
            billingAddressPage.validateAddressContainer(registeredOrderTestData.getBillAddrPos(), registeredOrderTestData.getShippingAddress());
        }

        // go to payment page
        var paymentPage = billingAddressPage.selectBillingAddress(registeredOrderTestData.getBillAddrPos());
        paymentPage.validateStructure();
        paymentPage.validateCreditCardContainer(registeredOrderTestData.getCreditCardPos(), registeredOrderTestData.getCreditCard());

        // go to place order page
        var placeOrderPage = paymentPage.selectCreditCard(registeredOrderTestData.getCreditCardPos());

        if (!registeredOrderTestData.getShipAddrEqualBillAddr())
        {
            placeOrderPage.validateOrderOverview(registeredOrderTestData.getShippingAddress(), registeredOrderTestData.getBillingAddress(),
                                                 registeredOrderTestData.getCreditCard());
        }
        else
        {
            placeOrderPage.validateOrderOverview(registeredOrderTestData.getShippingAddress(), registeredOrderTestData.getShippingAddress(),
                                                 registeredOrderTestData.getCreditCard());
        }

        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product);
        placeOrderPage.validatePriceSummary(placeOrderPage.getSubtotal(), shippingCosts);

        // go to order confirmation page
        var orderConfirmationPage = placeOrderPage.placeOrder();
        orderConfirmationPage.validateStructure();

        // go to homepage
        homePage = orderConfirmationPage.openHomePage();
    }
}
