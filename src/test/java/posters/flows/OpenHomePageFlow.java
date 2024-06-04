package posters.flows;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;
import static com.codeborne.selenide.Selenide.open;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.CategoryPage;
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
        return new HomePage().isExpectedPage();
    }

    @Step("open category page flow")
    public static CategoryPage openCategory(String categoryName, String categoryId)
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        // open category page
        open(Neodymium.configuration().url() + "topCategory/" + categoryName.replaceAll("\\s", "%20") + "?categoryId=" + categoryId);
        return new CategoryPage().isExpectedPage();
    }

    @Step("open search results page flow")
    public static CategoryPage openSearchResults(String term)
    {
        // clear cookies to ensure a new session
        clearBrowserCookies();

        // open category page
        open(Neodymium.configuration().url() + "search?searchText=" + term);
        return new CategoryPage().isExpectedPage();
    }
}
