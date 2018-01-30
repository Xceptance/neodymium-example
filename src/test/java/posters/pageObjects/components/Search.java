package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import posters.pageObjects.pages.browsing.CategoryPage;
import posters.pageObjects.pages.browsing.NoHitsPage;

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
}
