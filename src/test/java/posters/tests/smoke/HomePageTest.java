package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.util.AllureAddons;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
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
@TmsLink("end-of-script-developer-and-now")
@Issue("148")
@Link(url = "https://ask.xceptance.de/t/end-of-script-developer-and-now/148", type = "custom", name = "DemoLink")
@DisplayName("HomepageTest")
public class HomePageTest extends AbstractTest
{
    @Test
    @Description(value = "A basic description for home page validation.")
    public void testVisitingHomepage()
    {
        AllureAddons.addToReport("Find me in the report", "Some additional content. Could be the toString method of an complex object.");
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();
        homePage.title.validateTitle(Neodymium.localizedText("HomePage.title"));
    }
}
