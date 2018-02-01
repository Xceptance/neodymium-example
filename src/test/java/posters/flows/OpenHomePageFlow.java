package posters.flows;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.util.Context;

import posters.pageobjects.pages.browsing.HomePage;

public class OpenHomePageFlow
{
    public static HomePage flow()
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        // open home page
        open(Context.get().configuration.url());
        HomePage homePage = new HomePage();
        homePage.isExpectedPage();
        return homePage;
    };
}
