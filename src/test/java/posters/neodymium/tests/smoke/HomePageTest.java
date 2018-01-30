package posters.neodymium.tests.smoke;

import org.junit.Test;

import posters.flows.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.HomePage;

/**
 * @author pfotenhauer
 */
public class HomePageTest extends BasicTest
{
    @Test
    public void testVisitingHomepage()
    {
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validateStructure();
        homePage.footer.validate();
    }
}
