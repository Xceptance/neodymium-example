package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

import posters.pageObjects.pages.browsing.CategoryPage;
import posters.pageObjects.pages.browsing.NoHitsPage;

public class Search extends AbstractComponent
{
    public void isComponentAvailable()
    {
        $("#searchForm > #s").should(exist);
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
        $("#searchForm > #s").val(searchTerm).pressEnter();
    }

    public void openSearch()
    {
        $("#header-search-trigger").scrollTo().click();
    }
}
