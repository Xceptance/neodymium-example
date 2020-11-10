package posters.tests.smoke.order;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import posters.tests.smoke.browsing.AddToCartTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
      AddToCartTest.class, GuestOrderTest.class, RegisteredOrderTest.class,
    })
public class OrderTestSuite
{

}
