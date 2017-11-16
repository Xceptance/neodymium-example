package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import cucumber.api.java.en.When;
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
        return page(NoHitsPage.class);
    }

    public CategoryPage categoryPageResult(String searchTerm)
    {
        search(searchTerm);
        return page(CategoryPage.class);
    }

    @When("^I search for \"([^\"]*)\"$")
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
