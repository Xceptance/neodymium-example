package posters.flows;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import cucumber.api.java.en.And;
import posters.pageObjects.pages.browsing.HomePage;
import posters.settings.Settings;

public class OpenHomePageFlow
{

    @And("^I am on the homepage of the Posters shop$")
    public HomePage flow()
    {
        clearBrowserCookies();
        HomePage homePage = open(Settings.homePageUrl, HomePage.class);
        homePage.isExpectedPage();
        return homePage;
    };
}
