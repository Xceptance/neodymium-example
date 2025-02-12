package posters.pageobjects.pages.browsing;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.components.Pagination;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CategoryPage extends AbstractProductListingPage<CategoryPage>
{
    public Pagination pagination = new Pagination();

    private SelenideElement titleCategoryName = $("#title-category-name");

    @Override
    @Step("ensure this is a category page")
    public CategoryPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is a category page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(titleCategoryName, exist);
        return titleCategoryName.exists();
    }

    @Override
    @Step("validate category page structure")
    public CategoryPage validateStructure()
    {
        super.validateStructure();
        return this;
    }

    /**
     * @param categoryName
     *     name of specific category of top navigation
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
