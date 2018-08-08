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
        open(Neodymium.configuration().url());
        HomePage homePage = new HomePage();
        homePage.isExpectedPage();
        return homePage;
    };
}
