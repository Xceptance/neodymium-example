package posters.tests.newTests.order;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.OrderData;
import posters.dataobjects.User;
import posters.flows.OrderFlow;
import posters.flows.RegisterAndLoginFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.tests.AbstractTest;

public class RegisteredOrderTest extends AbstractTest
{
    private OrderData orderData;

    private User user;

    @Before
    public void dataSetup()
    {
        orderData = DataUtils.get(OrderData.class);
        user = User.createRandomUser();
    }

    @DataSet(id = "Registered-Order-Test")
    @DataSet(id = "Registered-Order-Test-billing")
    @Test
    public void testRegisteredOrder()
    {
        HomePage homepage = RegisterAndLoginFlow.registerAndLogin(user);

        homepage = OrderFlow.placeOrderWithValidation(orderData);

        homepage.validateSuccessfulOrder();
    }

    @DataSet(id = "Registered-Checkout-With-Saved-Data-Test-shipping")
    @DataSet(id = "Registered-Checkout-With-Saved-Data-Test-billing")
    @DataSet(id = "Registered-Checkout-With-Saved-Data-Test-payment")
    @Test
    public void testOrderForRegisterdeUserWithSavedData()
    {
        HomePage homePage = RegisterAndLoginFlow.registerAndLogin(user);
        if (orderData.getShippingAddress().isSavedInAccount())
        {
            homePage.userMenu.openAccountOverview().openMyAddressesPage().addNewShippingAddress(orderData.getShippingAddress());
        }

        if (orderData.getBillingAddress() != null && orderData.getBillingAddress().isSavedInAccount())
        {
            homePage.userMenu.openAccountOverview().openMyAddressesPage().addNewBillingAddress(orderData.getBillingAddress());
        }

        if (orderData.getPayment().isSavedInAccount())
        {
            homePage.userMenu.openAccountOverview().openPaymentSettings().addPayment(orderData.getPayment());
        }

        HomePage homepage = OrderFlow.placeOrderWithValidationForUserWithSavedData(orderData);

        homepage.validateSuccessfulOrder();
    }

    @DataSet(id = "Registered-Checkout-With-Second-Saved-Data-Test-shipping")
    @DataSet(id = "Registered-Checkout-With-Second-Saved-Data-Test-billing")
    @DataSet(id = "Registered-Checkout-With-Second-Saved-Data-Test-payment")
    @Test
    public void testOrderForRegisterdeUserWithSecondSavedData()
    {

        HomePage homePage = RegisterAndLoginFlow.registerAndLogin(user);
        if (orderData.getShippingAddress().isSavedInAccount())
        {
            homePage.userMenu.openAccountOverview().openMyAddressesPage().addNewShippingAddress(orderData.getShippingAddress());
        }

        if (orderData.getBillingAddress() != null && orderData.getBillingAddress().isSavedInAccount())
        {
            homePage.userMenu.openAccountOverview().openMyAddressesPage().addNewBillingAddress(orderData.getBillingAddress());
        }

        if (orderData.getPayment().isSavedInAccount())
        {
            homePage.userMenu.openAccountOverview().openPaymentSettings().addPayment(orderData.getPayment());
        }
        orderData.makeSecondAddressesToMain();
        HomePage homepage = OrderFlow.placeOrderWithValidationForUserWithSecondSavedData(orderData);

        homepage.validateSuccessfulOrder();
    }
}
