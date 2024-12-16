package posters.pageobjects.components;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.NoHitsPage;
import posters.pageobjects.pages.browsing.SearchResultPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class Search extends AbstractComponent
{
    private SelenideElement searchField = $("#header-search-text");

    @Override
    @Step("validate availability search bar")
    public void ensureComponentAvailable()
    {
        searchField.should(exist);
    }

    @Override
    @Step("check availability of search bar")
    public boolean isAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(searchField, exist);
        return searchField.exists();
    }

    /// ========== search navigation ========== ///

    @Step("open search field")
    public void openSearch()
    {
        searchField.click(ClickOptions.usingJavaScript());
    }

    @Step("search for '{searchTerm}'")
    public void search(String searchTerm)
    {
        openSearch();
        searchField.val(searchTerm).pressEnter();
    }

    @Step("validate that '{searchTerm}' is still visible after search")
    public void validateSearchTerm(String searchTerm)
    {
        openSearch();
        searchField.shouldHave(exactValue(searchTerm));
    }

    @Step("search for '{searchTerm}' without result")
    public NoHitsPage noHitsPageResult(String searchTerm)
    {
        search(searchTerm);
        return new NoHitsPage().reached();
    }

    @Step("search for '{searchTerm}' with result")
    public SearchResultPage searchResult(String searchTerm)
    {
        search(searchTerm);
        return new SearchResultPage().reached();
    }

    /// ========== validate search ========== ///

    @Step("validate search bar")
    public void validateStructure()
    {
        searchField.shouldBe(visible);
        $("#header-search-button").shouldBe(visible);
        $(".icon-search").shouldBe(visible);
    }
}
