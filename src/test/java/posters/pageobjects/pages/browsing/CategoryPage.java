package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.pageobjects.components.Pagination;

public class CategoryPage extends AbstractProductListingPage
{
    public Pagination pagination = new Pagination();

    private SelenideElement titleCategoryName = $("#title-category-name");

    @Override
    @Step("ensure this is a category page")
    public CategoryPage isExpectedPage()
    {
        super.isExpectedPage();
        titleCategoryName.should(exist);
        return this;
    }

    @Override
    @Step("validate category page structure")
    public void validateStructure()
    {
        super.validateStructure();
    }

    /**
     * @param categoryName name of specific category of top navigation
     */
    @Step("validate category name '{categoryName}' on category page")
    public void validateHeadline(String categoryName)
    {
        titleCategoryName.should(matchText(categoryName)).shouldBe(visible);
    }
    
    @Step("validate search results page of search term '{searchTerm}'")
    public void validate(String searchTerm, int expectedResultCount)
    {
        validateStructure();
        validateHeadline(searchTerm);
        validateResultCount(expectedResultCount);
    }
}
