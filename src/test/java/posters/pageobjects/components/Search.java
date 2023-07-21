package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.NoHitsPage;

public class Search extends AbstractComponent
{
    private static SelenideElement searchField = $("#s");

    public void isComponentAvailable()
    {
        searchField.should(exist);
    }

    @Step("search for '{searchTerm}' without result")
    public NoHitsPage noResult(String searchTerm)
    {
        search(searchTerm);
        return new NoHitsPage();
    }

    @Step("search for '{searchTerm}' with result")
    public CategoryPage categoryPageResult(String searchTerm)
    {
        search(searchTerm);
        return new CategoryPage().isExpectedPage();
    }

    @Step("search for '{searchTerm}'")
    public void search(String searchTerm)
    {
        openSearch();
        searchField.val(searchTerm).pressEnter();
    }

    @Step("open search field")
    public void openSearch()
    {
        $("#btnSearch").scrollTo().exists();
    }

    @Step("validate that {searchTerm} is still visible after")
    public void validateSearchTerm(String searchTerm)
    {
        openSearch();
        searchField.should(visible);
        searchField.should(exactValue(searchTerm));
    }
    
    @Step("validate structure search bar")
    public static void validateStructure() 
    {
        // validate search-bar, button and icon of button
        searchField.shouldBe(visible);
        $("#btnSearch").shouldBe(visible);
        $(".icon-search").shouldBe(visible);
    }
}
