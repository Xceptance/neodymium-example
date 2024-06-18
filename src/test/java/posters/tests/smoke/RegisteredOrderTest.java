package posters.tests.smoke;

import org.junit.After;
import org.junit.Test;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.CartCleanUpFlow;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.processes.RegisteredOrderTestData;

@Owner("Lisa Smith")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class RegisteredOrderTest extends AbstractTest
{
    @DataItem
    private RegisteredOrderTestData registeredOrderTestData;
    
    @DataItem
    private String shippingCosts;

    @Test
    public void testOrderingAsRegisteredUser()
    {
     // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to register page
        var registerPage = homePage.header.userMenu.openRegisterPage();

        // send register form
        var loginPage = registerPage.sendRegisterForm(registeredOrderTestData.getUser());

        // send login form
        var accountOverviewPage = loginPage.sendLoginForm(registeredOrderTestData.getUser());
        accountOverviewPage.validateSuccessfulLogin(registeredOrderTestData.getUser().getFirstName());
        accountOverviewPage.validateStructure();

        // go to address overview page and validate
        var addressOverviewPage = accountOverviewPage.openMyAddresses();
        addressOverviewPage.validateStructure();

        var addNewShippingAddressPage = addressOverviewPage.openAddNewShippingAddressPage();
        
        // add new shipping address
        addNewShippingAddressPage.validateStructure();
        addressOverviewPage = addNewShippingAddressPage.addressForm.addNewAddress(registeredOrderTestData.getAddress());
        addressOverviewPage.validateSuccessfulSave();
        
        // add new billing address
        var addNewBillingAddressPage = addressOverviewPage.openAddNewBillingAddressPage();
        addNewBillingAddressPage.validateStructure();
        addressOverviewPage = addNewBillingAddressPage.addressForm.addNewAddress(registeredOrderTestData.getAddress());
        addressOverviewPage.validateSuccessfulSave();

        // go to account overview page
        accountOverviewPage = addressOverviewPage.openAccountOverviewPage();

        // go to payment settings page and validate
        var paymentOverviewPage = accountOverviewPage.openPaymentSettings();
        paymentOverviewPage.validateStructure();

        // add new payment
        var addNewCreditCardPage = paymentOverviewPage.openAddNewCreditCardPage();
        addNewCreditCardPage.validateStructure();
        paymentOverviewPage = addNewCreditCardPage.addNewCreditCard(registeredOrderTestData.getCreditCard());
        paymentOverviewPage.validateSuccessfulSave();

        // go to category page
        var categoryPage = paymentOverviewPage.header.topNav.clickCategory(Neodymium.localizedText(registeredOrderTestData.getTopCategory()));

        // go to product detail page, add and store displayed product
        final var testDataProduct = registeredOrderTestData.getProduct();
        var productDetailPage = categoryPage.clickProductByName(testDataProduct.getName());
        productDetailPage.addToCart(testDataProduct.getSize(), testDataProduct.getStyle());

        // go to cart page
        var cartPage = productDetailPage.header.miniCart.openCartPage();
        cartPage.updateProductCount(testDataProduct.getName(), testDataProduct.getAmount());
        final var product = cartPage.getProduct(1);

        // go to shipping address page
        var shippingAddressPage = cartPage.openReturningCustomerShippingAddressPage();
        shippingAddressPage.validateStructure();
        shippingAddressPage.validateAddressContainer(registeredOrderTestData.getAddress());

        // go to billing address page
        var billingAddressPage = shippingAddressPage.selectShippingAddress(registeredOrderTestData.getAddress());
        billingAddressPage.validateStructure();
        billingAddressPage.validateAddressContainer(registeredOrderTestData.getAddress());

        // go to payment page
        var paymentPage = billingAddressPage.selectBillingAddress(registeredOrderTestData.getAddress());
        paymentPage.validateStructure();
        paymentPage.validateCreditCardContainer(registeredOrderTestData.getCreditCard());

        // go to place order page
        var placeOrderPage = paymentPage.selectCreditCard(registeredOrderTestData.getCreditCard());
        placeOrderPage.validateOrderOverview(registeredOrderTestData.getAddress(), registeredOrderTestData.getAddress(),
                                             registeredOrderTestData.getCreditCard());
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product);
        placeOrderPage.validatePriceSummary(shippingCosts);

        // go to order confirmation page
        var orderConfirmationPage = placeOrderPage.placeOrder();
        orderConfirmationPage.validateStructure();

        // go to homepage
        homePage = orderConfirmationPage.openHomePage();
    }

    @After
    public void after()
    {
        CartCleanUpFlow.flow();
        DeleteUserFlow.flow(registeredOrderTestData.getUser());
    }
}