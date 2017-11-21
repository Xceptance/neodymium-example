package posters.neodymium.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;

import posters.flows.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.HomePage;

@Browser(
{
  "Chrome_1024x768"
})
public class TVisit extends BasicTest
{
    @Test
    public void testVisitingHomepage()
    {
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validateStructure();
        homePage.footer().validate();
    }
}
