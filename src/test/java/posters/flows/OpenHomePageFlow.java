package posters.flows;

import com.xceptance.neodymium.util.Neodymium;
import io.qameta.allure.Step;
import posters.pageobjects.components.Header;
import posters.pageobjects.pages.browsing.HomePage;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

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

        changeLocale(homePage.header);

        return new HomePage().isExpectedPage();
    }

    public static void changeLocale(Header header)
    {
        String locale = Neodymium.configuration().locale();
        if ("de_DE".equals(locale))
        {
            header.localeMenu.changeLocale("de-DE");
        }
        else
        {
            header.localeMenu.changeLocale("de-DE");
            header.localeMenu.changeLocale("en-US");
        }
    }
}
