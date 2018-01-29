package posters.flows;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import posters.pageObjects.pages.browsing.HomePage;
import posters.settings.Settings;

public class OpenHomePageFlow
{
    public static HomePage flow()
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        // open home page
        HomePage homePage = open(Settings.homePageUrl, HomePage.class);
        homePage.isExpectedPage();
        return homePage;
    };
}
