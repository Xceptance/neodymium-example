package posters.pageobjects.pages.browsing;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class SearchResultPage extends AbstractProductListingPage
{
    private SelenideElement titleSearchText = $("#title-search-text");

    @Override
    @Step("ensure this is a search results page")
    public SearchResultPage reached()
    {
        super.reached();
        titleSearchText.should(exist);
        return this;
    }

    @Override
    @Step("check if this is a search result page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(titleSearchText, exist);
        return titleSearchText.exists();
    }

    @Override
    @Step("validate search results page structure")
    public void validateStructure()
    {
        super.validateStructure();

        $("#search-text-value").shouldBe(visible);
    }

    /**
     * @param searchTerm
     *     name of search term
     */
    @Step("validate search term '{searchTerm}' on search result page")
    public void validateHeadline(String searchTerm)
    {
        titleSearchText.should(matchText(Neodymium.localizedText("categoryPage.searchResultText"))).shouldBe(visible);
        $("#search-text-value").shouldHave(exactText(searchTerm)).shouldBe(visible);
    }

    @Step("validate search result page of search term '{searchTerm}'")
    public void validate(String searchTerm, int expectedResultCount)
    {
        validateStructure();
        validateHeadline(searchTerm);
        validateResultCount(expectedResultCount);
    }
}
