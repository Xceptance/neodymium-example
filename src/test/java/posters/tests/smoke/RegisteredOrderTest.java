package posters.tests.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
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
import posters.tests.testdata.processes.RegisteredOrderTestData;

@Owner("Lisa Smith")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class RegisteredOrderTest extends AbstractTest
{   
    private RegisteredOrderTestData registeredOrderTestData;

    @Before
    public void setup()
    {
        registeredOrderTestData = DataUtils.get(RegisteredOrderTestData.class);
    }
   
    @DataSet(1)
    @DataSet(2)
    @Test
    public void testOrderingAsRegisteredUser()
    {
        // use test data
        final String shippingCosts = Neodymium.dataValue("shippingCosts");

        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to register page
        var registerPage = homePage.header.userMenu.openRegisterPage();

        // send register form
        var loginPage = registerPage.sendRegisterForm(registeredOrderTestData.getUser());

        // send login form
        homePage = loginPage.sendLoginForm(registeredOrderTestData.getUser());
        homePage.validateSuccessfulLogin(registeredOrderTestData.getUser().getFirstName());
        
        // go to account overview page and validate
        var accountOverviewPage = homePage.header.userMenu.openAccountOverviewPage();
        accountOverviewPage.validateStructure();
        
        // go to address overview page and validate
        var addressOverviewPage = accountOverviewPage.openMyAddresses();
        addressOverviewPage.validateStructure();
        
        // add new addresses
        if (!registeredOrderTestData.getShipAddrEqualBillAddr())
        {
            var addNewShippingAddressPage = addressOverviewPage.openAddNewShippingAddressPage();
            addNewShippingAddressPage.validateStructure();
            addressOverviewPage = addNewShippingAddressPage.addNewShippingAddress(registeredOrderTestData.getShippingAddress());
            addressOverviewPage.validateSuccessfulSave();
            
            var addNewBillingAddressPage = addressOverviewPage.openAddNewBillingAddressPage();
            addNewBillingAddressPage.validateStructure();
            addressOverviewPage = addNewBillingAddressPage.addNewBillingAddress(registeredOrderTestData.getBillingAddress());
            addressOverviewPage.validateSuccessfulSave();
        }
        else
        {
            var addNewShippingAddressPage = addressOverviewPage.openAddNewShippingAddressPage();
            addNewShippingAddressPage.validateStructure();
            addressOverviewPage = addNewShippingAddressPage.addNewShippingAddress(registeredOrderTestData.getShippingAddress());
            addressOverviewPage.validateSuccessfulSave();
            
            var addNewBillingAddressPage = addressOverviewPage.openAddNewBillingAddressPage();
            addNewBillingAddressPage.validateStructure();
            addressOverviewPage = addNewBillingAddressPage.addNewBillingAddress(registeredOrderTestData.getShippingAddress());
            addressOverviewPage.validateSuccessfulSave();
        }

        // go to account overview page
        accountOverviewPage = addressOverviewPage.header.userMenu.openAccountOverviewPage();
        
        // go to payment settings page and validate
        var paymentOverviewPage = accountOverviewPage.openPaymentSettings();
        paymentOverviewPage.validateStructure();
        
        // add new payment
        var addNewCreditCardPage = paymentOverviewPage.openAddNewCreditCardPage();
        addNewCreditCardPage.validateStructure();
        paymentOverviewPage = addNewCreditCardPage.addNewCreditCard(registeredOrderTestData.getCreditCard());
        paymentOverviewPage.validateSuccessfulSave();
        
        // go to homepage
        homePage = paymentOverviewPage.openHomePage();
        
        // go to category page
        var categoryPage = homePage.header.topNav.clickCategory(Neodymium.localizedText(registeredOrderTestData.getTopCategory()));

        // go to product detail page, add and store displayed product
        var productDetailPage = categoryPage.clickProductByPosition(registeredOrderTestData.getResultPosition());
        productDetailPage.addToCart(registeredOrderTestData.getsSizeProduct(), registeredOrderTestData.getStyleProduct());
        final var product = productDetailPage.getProduct();

        // go to cart page
        var cartPage = productDetailPage.header.miniCart.openCartPage();

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
            placeOrderPage.validateOrderOverview(registeredOrderTestData.getShippingAddress(), registeredOrderTestData.getBillingAddress(), registeredOrderTestData.getCreditCard());
        }
        else
        {
            placeOrderPage.validateOrderOverview(registeredOrderTestData.getShippingAddress(), registeredOrderTestData.getShippingAddress(), registeredOrderTestData.getCreditCard());
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
    
    @After
    public void after()
    {     
        CartCleanUpFlow.flow();
        DeleteUserFlow.flow(registeredOrderTestData.getUser());
    }
}
