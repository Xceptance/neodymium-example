package posters.tests.smoke;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.AllureAddons;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;

@Severity(SeverityLevel.TRIVIAL)
@Owner("Joe Fix")
@Tag("smoke")
@TmsLink("end-of-script-developer-and-now")
@Issue("148")
@Link(url = "https://ask.xceptance.de/t/end-of-script-developer-and-now/148", type = "custom", name = "DemoLink")
@DisplayName("HomepageTest")
public class HomePageTest extends AbstractTest
{
    @NeodymiumTest
    @Description(value = "A basic description for home page validation.")
    public void testVisitingHomepage()
    {
        AllureAddons.addToReport("Find me in the report", "Some additional content. Could be the toString method of an complex object.");
        var homePage = OpenHomePageFlow.flow();
        homePage.header.localeMenu.changeLocale("de-DE");
        homePage.header.localeMenu.changeLocale("en-US");
        homePage.title.validateTitle(Neodymium.localizedText("homePage.title"));
        homePage.validateStructure();
    }
}
