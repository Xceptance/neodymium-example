package posters.flows;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import posters.pageObjects.pages.browsing.HomePage;
import posters.settings.Settings;

public class OpenHomePageFlow
{

    public HomePage flow()
    {
        clearBrowserCookies();
        HomePage homePage = open(Settings.homePageUrl, HomePage.class);
        homePage.isExpectedPage();
        return homePage;
    };
}
