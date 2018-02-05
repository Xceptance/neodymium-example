package posters.tests.smoke;

import org.junit.Test;

import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.tests.BasicTest;

/**
 * @author pfotenhauer
 */
public class HomePageTest extends BasicTest
{
    @Test
    public void testVisitingHomepage()
    {
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();
    }
}
