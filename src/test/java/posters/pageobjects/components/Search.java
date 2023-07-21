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

    @Override
    @Step("validate availability search bar")
    public void isComponentAvailable()
    {
        searchField.should(exist);
    }
    
    @Step("validate structure search bar")
    public static void validateStructure() 
    {
        searchField.shouldBe(visible);
        $("#btnSearch").shouldBe(visible);
        $(".icon-search").shouldBe(visible);
    }

    @Step("search for {searchTerm} without result")
    public NoHitsPage noHitsPageResult(String searchTerm)
    {
        search(searchTerm);
        return new NoHitsPage().isExpectedPage();
    }

    @Step("search for {searchTerm} with result")
    public CategoryPage categoryPageResult(String searchTerm)
    {
        search(searchTerm);
        return new CategoryPage().isExpectedPage();
    }

    @Step("search for {searchTerm}")
    public void search(String searchTerm)
    {
        openSearch();
        searchField.val(searchTerm).pressEnter();
    }

    @Step("open search field")
    public void openSearch()
    {
        searchField.scrollTo().click();
    }

    @Step("validate that {searchTerm} is still visible after")
    public void validateSearchTerm(String searchTerm)
    {
        openSearch();
        searchField.shouldHave(exactValue(searchTerm));
    }
}
