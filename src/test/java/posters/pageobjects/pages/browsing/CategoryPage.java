package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.components.Pagination;

public class CategoryPage extends AbstractBrowsingPage
{
    public Pagination pagination = new Pagination();
    
    private SelenideElement productOverview = $("#productOverview");
    
    private SelenideElement titleCategoryName = $("#titleCategoryName");

    @Override
    @Step("ensure this is a category page")
    public CategoryPage isExpectedPage()
    {
        super.isExpectedPage();
        productOverview.should(exist);
        return this;
    }

    /// ----- validate content category page ----- ///

    @Override
    @Step("validate category page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate poster count in headline is not 0
        $("#totalProductCount").shouldNotBe(exactText("0")).shouldBe(visible);

        // validate at least 1 poster is displayed
        $("#product0").shouldBe(visible);
    }

    @Step("validate category name {categoryName} is on category page")
    public void validateCategoryHeadline(String categoryName, int expectedResultCount)
    {
        if (categoryName.contains(".")) 
        {
            // if {categoryName} contains Neodymium localization
            titleCategoryName.should(matchText(Neodymium.localizedText(categoryName))).shouldBe(visible);
            titleCategoryName.should(matchText(Integer.toString(expectedResultCount))).shouldBe(visible);
        }
        else 
        { 
            // if {categoryName} is search input
            $("#titleSearchText").should(matchText(Neodymium.localizedText("CategoryPage.search.resultText"))).shouldBe(visible);
            $("#searchTextValue").shouldHave(exactText(categoryName)).shouldBe(visible);
            $("#totalProductCount").shouldHave(exactText(Integer.toString(expectedResultCount))).shouldBe(visible);
        }
    }

    @Step("validate category page of category '{categoryName}'")
    public void validate(String categoryName, int expectedResultCount)
    {
        validateStructure();
        validateCategoryHeadline(categoryName, expectedResultCount);
    }

    /// ----- product by position ----- ///

    @Step("get a product name by position")
    public String getProductNameByPosition(int position)
    {
        return $("#product" + (position - 1) + " h2").text();
    }

    @Step("click on a product by position")
    public ProductDetailPage clickProductByPosition(int position)
    {
        $("#product" + (position - 1)).scrollTo().click();
        return new ProductDetailPage().isExpectedPage();
    }
}
