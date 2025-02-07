package posters.tests.smoke;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.Neodymium;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
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

    // If you want a different name for a test data key, you can define the desired name 
    // and then assign the value to the specified name using the test data key as parameter 
    // in the DataItem annotation, as shown below.
    @DataItem("shippingCosts")
    private String shippingCostsValue;

    @NeodymiumTest
    public void testOrderingAsRegisteredUser()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to register page
        var registerPage = homePage.header.userMenu.openRegisterPage();

        // send register form
        var loginPage = registerPage.sendRegisterForm(registeredOrderTestData.getUser());

        // send login form
        homePage = loginPage.sendLoginForm(registeredOrderTestData.getUser());
        var accountOverviewPage = homePage.header.userMenu.openAccountOverviewPage();
        accountOverviewPage.validateStructure();

        // go to address overview page and validate
        var addressOverviewPage = accountOverviewPage.openMyAddresses();
        addressOverviewPage.validateStructure();

        var addNewShippingAddressPage = addressOverviewPage.openAddNewShippingAddressPage();

        // add new shipping address
        addNewShippingAddressPage.validateStructure();
        addressOverviewPage = addNewShippingAddressPage.addressForm.addNewAddress(registeredOrderTestData.getAddress());
        //addressOverviewPage.validateSuccessfulSave();

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
        placeOrderPage.validatePriceSummary(shippingCostsValue);

        // go to order confirmation page
        var orderConfirmationPage = placeOrderPage.placeOrder();
        orderConfirmationPage.validateStructure();

        // go to homepage
        homePage = orderConfirmationPage.openHomePage();
    }

    @AfterEach
    public void after()
    {
        CartCleanUpFlow.flow();
        DeleteUserFlow.flow(registeredOrderTestData.getUser());
    }
}
