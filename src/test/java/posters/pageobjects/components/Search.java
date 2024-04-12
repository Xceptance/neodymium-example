package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.NoHitsPage;

public class Search extends AbstractComponent
{
    private SelenideElement searchField = $("#header-search-text");

    @Override
    @Step("validate availability search bar")
    public void isComponentAvailable()
    {
        searchField.should(exist);
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
        return new NoHitsPage().isExpectedPage();
    }

    @Step("search for '{searchTerm}' with result")
    public CategoryPage categoryPageResult(String searchTerm)
    {
        search(searchTerm);
        return new CategoryPage().isExpectedPage();
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
