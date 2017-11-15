package posters.neodymium.flow;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import posters.pageObjects.pages.browsing.HomePage;

public class OpenHomePageFlow extends AbstractFlow<HomePage>
{

    @Override
    public HomePage flow()
    {
        clearBrowserCookies();
        HomePage homePage = open("https://localhost:8443/posters/", HomePage.class);
        homePage.isExpectedPage();
        return homePage;
    };
}
