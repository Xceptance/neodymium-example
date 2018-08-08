/**
 * 
 */
package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Random;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.components.Pagination;

/**
 * @author pfotenhauer
 */
public class CategoryPage extends AbstractBrowsingPage
{
    public Pagination pagination = new Pagination();

    private SelenideElement productOverview = $("#productOverview");

    @Override
    @Step("ensure this is a category page")
    public void isExpectedPage()
    {
        productOverview.should(exist);
    }

    @Step("validate category name \"{categoryName}\" on category page")
    public void validateCategoryName(String categoryName)
    {
        // Category name
        // Ensures the category headline contains the category name
        $("#titleCategoryName").shouldBe(text(categoryName));
    }

    @Step("validate category page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // Amount of results
        // Assures the amount of posters displayed in the headline is not 0.
        $("#totalProductCount").shouldNotBe(exactText("0"));
        // Results
        // Assures there's at least one product shown
        $("#product0").shouldBe(visible);
    }

    /**
     * @param row
     * @param column
     * @return
     */
    @Step("click on a product by position in grid")
    public ProductdetailPage clickProductByPosition(int row, int column)
    {
        // Open the product detail page
        // Clicks a product by position. Because of the html code, this requires x and y coordinates.
        SelenideElement rowContainer = $$("#productOverview > .row").get(row - 1);
        rowContainer.find(".thumbnail", column - 1).scrollTo().click();
        return new ProductdetailPage();
    }

    /**
     * @param row
     * @param column
     * @return
     */
    @Step("get a product name by position in grid")
    public String getProductNameByPosition(int row, int column)
    {
        SelenideElement rowContainer = productOverview.findAll(".thumbnails.row").get(row - 1);
        return rowContainer.find("h4.pName", column - 1).text();
    }

    /**
     * @param position
     * @return
     */
    @Step("click on a product by position")
    public ProductdetailPage clickProductByPosition(int position)
    {
        final int index = position - 1;
        // Open the product detail page
        // Click on the product's image and open the product overview page
        // Click the product link to open the product detail page
        $("#product" + index + " img").scrollTo().click();
        return new ProductdetailPage();
    }

    /**
     * @param position
     * @return
     */
    @Step("click on a product by name \"{productName}\"")
    public ProductdetailPage clickProductByName(String productName)
    {
        // Open the product detail page
        // Click on the product's image and open the product overview page
        // Click the product link to open the product detail page
        $("#productOverview .thumbnails .thumbnail a > img.pImage[title='" + productName + "']").scrollTo().click();
        return new ProductdetailPage();
    }

    /**
     * @param position
     * @return
     */
    @Step("get a product name by position")
    public String getProductNameByPosition(int position)
    {
        final int index = position - 1;
        // Get the product name
        return $("#product" + index + " .pInfo .pName").text();
    }

    /**
     * @param searchTerm
     * @param searchTermExpectedCount
     */
    @Step("validate search results for \"{searchTerm}\" on category page")
    public void validateSearchHits(String searchTerm, int searchTermExpectedCount)
    {
        $("#titleSearchText").should(exist);
        // Validate the headline contains the search phrase
        // \\(" + searchTermExpectedCount + " *\\)
        $("#titleSearchText").should(matchText(Neodymium.localizedText("search.results.text") + ": '" + searchTerm + "' \\(" + searchTermExpectedCount
                                               + ".*\\)"));
        // Verify that the correct search term is displayed
        // Validate the entered search phrase is still visible in the input
        search.validateSearchTerm(searchTerm);
        // Assert the Headline displays the search term.
        $(".header-container #searchTextValue").shouldHave(exactText(searchTerm));
        // Verify there are search results
        // There is at least one row of results
        $("#productOverview ul.row").shouldBe(exist);
        // There is at least one product in the results
        $$("#productOverview li").shouldHave(sizeGreaterThan(0));
        // Verify that there is the specified amount of results
        // The amount of products shown in the headline matches the expected value
        $("#totalProductCount").shouldHave(exactText(Integer.toString(searchTermExpectedCount)));
    }

    /**
     * @param productName
     */
    @Step("validate product \"{productName}\" is visible on category page")
    public void validateProductVisible(String productName)
    {
        $("#productOverview .thumbnails .thumbnail a > img.pImage[title='" + productName + "']").shouldBe(visible);
    }

    /**
     * @param categoryName
     */
    @Step("validate category page of category \"{categoryName}\"")
    public void validate(String categoryName)
    {
        validateStructure();
        validateCategoryName(categoryName);
    }

    /**
     * @param categoryName
     */
    @Step("validate category page of category \"{categoryName}\" and assert visually")
    public void validateAndVisualAssert(String categoryName)
    {
        validateStructureAndVisual();
        validateCategoryName(categoryName);
    }

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
