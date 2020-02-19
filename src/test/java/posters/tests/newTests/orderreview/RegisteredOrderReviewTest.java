package posters.tests.newTests.orderreview;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.OrderData;
import posters.dataobjects.User;
import posters.flows.OrderFlow;
import posters.flows.RegisterAndLoginFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.MyAddressesPage;
import posters.pageobjects.pages.user.OrderHistoryPage;
import posters.pageobjects.pages.user.PaymentSettingsPage;
import posters.tests.AbstractTest;

public class RegisteredOrderReviewTest extends AbstractTest
{
    private OrderData orderData;

    private User user;

    @Before
    public void dataSetup()
    {
        orderData = DataUtils.get(OrderData.class);
        user = User.createRandomUser();
    }

    @Ignore
    @Test
    public void testRegisteredOrder()
    {
        HomePage homepage = RegisterAndLoginFlow.registerAndLogin(user);

        homepage = OrderFlow.placeOrderWithoutValidation(orderData);

        OrderHistoryPage orderHistoryPage = homepage.userMenu.openAccountOverview().openOrderHistory();
        orderHistoryPage.validateStructure();
        orderHistoryPage.validateOrder(orderData);
    }

    @Test
    public void testSavedDataAfterOrder()
    {
        HomePage homepage = RegisterAndLoginFlow.registerAndLogin(user);

        homepage = OrderFlow.placeOrderWithoutValidation(orderData);

        MyAddressesPage myAddressesPage = homepage.userMenu.openAccountOverview().openMyAddressesPage();
        myAddressesPage.validateShippingAddress(orderData.getShippingAddress());
        myAddressesPage.validateBillingAddress(orderData.getBillingAddress() != null ? orderData.getBillingAddress() : orderData.getShippingAddress());

        PaymentSettingsPage paymentSettingsPage = myAddressesPage.userMenu.openAccountOverview().openPaymentSettings();
        paymentSettingsPage.validatePayment(orderData.getPayment());
    }
}
