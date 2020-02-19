package posters.tests.newTests.order;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.OrderData;
import posters.dataobjects.User;
import posters.flows.OrderFlow;
import posters.flows.RegisterAndLoginFlow;
import posters.flows.RegisterUserAndAddAddressFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.MyAddressesPage;
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

    @Test
    public void testRegisteredOrder()
    {
        HomePage homepage = RegisterAndLoginFlow.registerAndLogin(user);

        homepage = OrderFlow.placeOrderWithValidation(orderData);

        homepage.validateSuccessfulOrder();
    }

    @DataSet(id = "Registered-Order-Test")
    @Test
    public void testOrderForRegisterdeUserWithSavedData()
    {
        MyAddressesPage myAddressesPage = RegisterUserAndAddAddressFlow.addShippingAddress(user, orderData.getShippingAddress());
        myAddressesPage.addNewBillingAddress(orderData.getBillingAddress() != null ? orderData.getBillingAddress() : orderData.getShippingAddress());
        myAddressesPage.userMenu.openAccountOverview().openPaymentSettings().addPayment(orderData.getPayment());

        HomePage homepage = OrderFlow.placeOrderWithValidationForUserWithSavedData(orderData);

        homepage.validateSuccessfulOrder();
    }
}
