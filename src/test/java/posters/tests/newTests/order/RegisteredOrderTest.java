package posters.tests.newTests.order;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.OrderData;
import posters.dataobjects.User;
import posters.flows.OpenPageFlow;
import posters.flows.OrderFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;
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
        RegisterPage registerPage = OpenPageFlow.openRegistrationPage();
        LoginPage loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.sendLoginform(user);

        HomePage homepage = OrderFlow.placeOrderWithValidation(orderData);

        homepage.validateSuccessfulOrder();
    }
}