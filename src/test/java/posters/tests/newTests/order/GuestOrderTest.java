package posters.tests.newTests.order;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.OrderData;
import posters.flows.OpenPageFlow;
import posters.flows.OrderFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.tests.AbstractTest;

public class GuestOrderTest extends AbstractTest
{
    private OrderData orderData;

    @Before
    public void dataSetup()
    {
        orderData = DataUtils.get(OrderData.class);
    }

    @Test
    public void testGuestOrder()
    {
        OpenPageFlow.openHomePage();
        HomePage homepage = OrderFlow.placeOrderWithValidation(orderData);
        homepage.validateSuccessfulOrder();
    }

}
