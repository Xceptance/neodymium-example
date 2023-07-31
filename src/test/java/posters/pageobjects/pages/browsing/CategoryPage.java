package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Random;

import com.codeborne.selenide.ElementsCollection;
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
            titleCategoryName.should(matchText(Neodymium.localizedText(categoryName))).shouldBe(visible);
            titleCategoryName.shouldHave(matchText(Integer.toString(expectedResultCount))).shouldBe(visible);
        }
        else 
        { 
            $("#titleSearchText").should(matchText(Neodymium.localizedText("search.result.text"))).shouldBe(visible);
            $("#searchTextValue").should(exactText(categoryName)).shouldBe(visible);
            $("#totalProductCount").should(exactText(Integer.toString(expectedResultCount))).shouldBe(visible);
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


    // ----------------------------------------------------------

    
    // TODO - check if needed
    @Step("click on a product by position in grid")
    public ProductDetailPage clickProductByPosition(int row, int column)
    {
        // Open the product detail page
        // Clicks a product by position. Because of the html code, this requires x and y coordinates.
        SelenideElement rowContainer = $$("#products > .row").get(row - 1);
        rowContainer.find(".thumbnail", column - 1).scrollTo().click();
        return new ProductDetailPage().isExpectedPage();
    }

    // TODO - check if needed
    @Step("get a product name by position in grid")
    public String getProductNameByPosition(int row, int column)
    {
        SelenideElement rowContainer = productOverview.findAll("#products #pRows").get(row - 1);
        return rowContainer.find("h2.pName", column - 1).text();
    }

    // TODO - check if needed
    @Step("click on a product by name '{productName}'")
    public ProductDetailPage clickProductByName(String productName)
    {
        // Open the product detail page
        // Click on the product's image and open the product overview page
        // Click the product link to open the product detail page
        $("#productOverview .thumbnails .thumbnail a > img.pImage[title='" + productName + "']").scrollTo().click();
        return new ProductDetailPage().isExpectedPage();
    }

    // TODO - check if needed
    @Step("validate product '{productName}' is visible on category page")
    public void validateProductVisible(String productName)
    {
        $("#productOverview .thumbnails .thumbnail a > img.pImage[title='" + productName + "']").shouldBe(visible);
    }

    // TODO - check if needed
    @Step("validate category page of category '{categoryName}' and assert visually")
    public void validateAndVisualAssert(String categoryName, int expectedResultCount)
    {
        validateStructureAndVisual();
        validateCategoryHeadline(categoryName, expectedResultCount);
    }

    // TODO - check if needed
    public String getRandomProductDetailName(Random random)
    {
        ElementsCollection rows = productOverview.findAll(".thumbnails.row");
        int numberOfRows = rows.size();
        int productIndexX = random.nextInt(numberOfRows);

        SelenideElement row = rows.get(productIndexX);
        int numberOfColumns = row.findAll(".thumbnail").filter(visible).size();
        int productIndexY = random.nextInt(numberOfColumns);

        return getProductNameByPosition(productIndexX + 1, productIndexY + 1);
    }
}
