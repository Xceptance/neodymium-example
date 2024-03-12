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
import posters.tests.testdata.processes.OrderHistoryTestData;

@Owner("Lisa Smith")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class OrderHistoryTest extends AbstractTest
{   
    private OrderHistoryTestData orderHistoryTestData;

    @Before
    public void setup()
    {
        orderHistoryTestData = DataUtils.get(OrderHistoryTestData.class);
    }
   
    @DataSet(1)
    @DataSet(2)
    @Test
    public void testOrderHistory()
    {
        // use test data
        final String shippingCosts = Neodymium.dataValue("shippingCosts");

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
        accountOverviewPage = orderHistoryPage.header.userMenu.openAccountOverviewPage();
        
        // go to address overview page and validate
        var addressOverviewPage = accountOverviewPage.openMyAddresses();
        
        // add new addresses
        if (!orderHistoryTestData.getShipAddrEqualBillAddr())
        {
            var addNewShippingAddressPage = addressOverviewPage.openAddNewShippingAddressPage();
            addressOverviewPage = addNewShippingAddressPage.addressForm.addNewAddress(orderHistoryTestData.getShippingAddress());
            addressOverviewPage.validateSuccessfulSave();
            
            var addNewBillingAddressPage = addressOverviewPage.openAddNewBillingAddressPage();
            addressOverviewPage = addNewBillingAddressPage.addressForm.addNewAddress(orderHistoryTestData.getBillingAddress());
            addressOverviewPage.validateSuccessfulSave();
        }
        else
        {
            var addNewShippingAddressPage = addressOverviewPage.openAddNewShippingAddressPage();
            addressOverviewPage = addNewShippingAddressPage.addressForm.addNewAddress(orderHistoryTestData.getShippingAddress());
            addressOverviewPage.validateSuccessfulSave();
            
            var addNewBillingAddressPage = addressOverviewPage.openAddNewBillingAddressPage();
            addressOverviewPage = addNewBillingAddressPage.addressForm.addNewAddress(orderHistoryTestData.getShippingAddress());
            addressOverviewPage.validateSuccessfulSave();
        }

        // go to account overview page
        accountOverviewPage = addressOverviewPage.header.userMenu.openAccountOverviewPage();
        
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
        final var product1 = productDetailPage.addToCart(orderHistoryTestData.getsSizeProduct(), orderHistoryTestData.getStyleProduct());

        // go to cart page
        var cartPage = productDetailPage.header.miniCart.openCartPage();

        // go to shipping address page
        var shippingAddressPage = cartPage.openReturningCustomerShippingAddressPage();

        // go to billing address page
        var billingAddressPage = shippingAddressPage.selectShippingAddress(orderHistoryTestData.getShipAddrPos());

        // go to payment page
        var paymentPage = billingAddressPage.selectBillingAddress(orderHistoryTestData.getBillAddrPos());

        // go to place order page
        var placeOrderPage = paymentPage.selectCreditCard(orderHistoryTestData.getCreditCardPos());

        // go to order confirmation page
        var orderConfirmationPage = placeOrderPage.placeOrder();
        
        // go to account overview page
        accountOverviewPage = orderConfirmationPage.header.userMenu.openAccountOverviewPage();
        
        // go to order history page
        orderHistoryPage = accountOverviewPage.openOrderHistory();
        orderHistoryPage.validateOrder(1, 1, product1);
        
        // go to category page
        categoryPage = orderHistoryPage.header.topNav.clickCategory(Neodymium.localizedText(orderHistoryTestData.getTopCategory2()));

        // go to product detail page, add and store displayed product
        productDetailPage = categoryPage.clickProductByPosition(orderHistoryTestData.getResultPosition());
        final var product2 = productDetailPage.addToCart(orderHistoryTestData.getsSizeProduct(), orderHistoryTestData.getStyleProduct());
        
        // go to category page
        categoryPage = productDetailPage.header.topNav.clickCategory(Neodymium.localizedText(orderHistoryTestData.getTopCategory3()));

        // go to product detail page, add and store displayed product
        productDetailPage = categoryPage.clickProductByPosition(orderHistoryTestData.getResultPosition());
        final var product3 = productDetailPage.addToCart(orderHistoryTestData.getsSizeProduct(), orderHistoryTestData.getStyleProduct());

        // go to cart page
        cartPage = productDetailPage.header.miniCart.openCartPage();

        // go to shipping address page
        shippingAddressPage = cartPage.openReturningCustomerShippingAddressPage();

        // go to billing address page
        billingAddressPage = shippingAddressPage.selectShippingAddress(orderHistoryTestData.getShipAddrPos());

        // go to payment page
        paymentPage = billingAddressPage.selectBillingAddress(orderHistoryTestData.getBillAddrPos());

        // go to place order page
        placeOrderPage = paymentPage.selectCreditCard(orderHistoryTestData.getCreditCardPos());

        // go to order confirmation page
        orderConfirmationPage = placeOrderPage.placeOrder();
        
        // go to account overview page
        accountOverviewPage = orderConfirmationPage.header.userMenu.openAccountOverviewPage();
        
        // go to order history page
        orderHistoryPage = accountOverviewPage.openOrderHistory();
        orderHistoryPage.validateOrder(1, 1, product2);
        orderHistoryPage.validateOrder(1, 2, product3);
        orderHistoryPage.validateOrder(2, 1, product1); 
    }
    
    @After
    public void after()
    {     
        CartCleanUpFlow.flow();
        DeleteUserFlow.flow(orderHistoryTestData.getUser());
    }
}