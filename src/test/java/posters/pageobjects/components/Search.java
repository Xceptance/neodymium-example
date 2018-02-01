package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.NoHitsPage;

public class Search extends AbstractComponent
{
    private SelenideElement searchField = $("#searchForm > #s");

    public void isComponentAvailable()
    {
        searchField.should(exist);
    }

    public NoHitsPage noResult(String searchTerm)
    {
        search(searchTerm);
        return new NoHitsPage();
    }

    public CategoryPage categoryPageResult(String searchTerm)
    {
        search(searchTerm);
        return new CategoryPage();
    }

    public void search(String searchTerm)
    {
        openSearch();
        searchField.val(searchTerm).pressEnter();
    }

    public void openSearch()
    {
        $("#header-search-trigger").scrollTo().click();
    }

    public void validateSearchTerm(String searchTerm)
    {
        // Validate the entered search phrase is still visible in the input
        openSearch();
        // Validate the entered search phrase is still visible in the input
        searchField.should(visible);
        // Validate the entered search phrase is still visible in the input
        searchField.should(exactValue(searchTerm));
    }
}
