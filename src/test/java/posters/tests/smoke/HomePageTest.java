package posters.tests.smoke;

import org.junit.Test;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Severity(SeverityLevel.TRIVIAL)
@Owner("Joe Fix")
@Tag("smoke")
public class HomePageTest extends AbstractTest
{
    @Test
    public void testVisitingHomepage()
    {
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();
    }
}
