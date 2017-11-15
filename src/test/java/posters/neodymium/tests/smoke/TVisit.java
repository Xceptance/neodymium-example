package posters.neodymium.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;

import posters.neodymium.flow.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.HomePage;

@Browser(
{
  "Chrome_1024x768"
})
public class TVisit extends BasicTest
{
    @Test
    public void test()
    {
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validateStructure();
        homePage.footer().validate();
    }
}
