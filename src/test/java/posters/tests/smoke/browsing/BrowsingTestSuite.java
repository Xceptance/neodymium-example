package posters.tests.smoke.browsing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
    {
      HomePageTest.class, SearchTest.class, BrowseTest.class, BrowseRandomVisualAssertTest.class, AddToCartTest.class
    })
public class BrowsingTestSuite
{

}
