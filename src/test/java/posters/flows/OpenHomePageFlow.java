package posters.flows;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;

public class OpenHomePageFlow
{
    @Step("open home page flow")
    public static HomePage flow()
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        // open home page
        HomePage homePage = new HomePage();
        open(Neodymium.configuration().url());
        homePage.header.localeMenu.changeLocale("de-DE");
        homePage.header.localeMenu.changeLocale("en-US");
        return new HomePage().isExpectedPage();
    }
}
