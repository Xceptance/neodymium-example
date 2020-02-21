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

    private String productOverview = "#productOverview";

    private SelenideElement totalProductCount = $("#totalProductCount");

    private ElementsCollection products = $$("#productOverview li");

    @Override
    @Step("ensure this is a category page")
    public CategoryPage isExpectedPage()
    {
        $(productOverview).should(exist);
        return this;
    }

    @Step("validate category page structure")
    public CategoryPage validateStructure()
    {
        super.validateStructure();

        // Amount of results
        // Assures the amount of posters displayed in the headline is not 0.
        totalProductCount.shouldNotBe(exactText("0"));
        // Results
        // Assures there's at least one product shown
        products.shouldHave(sizeGreaterThan(0));
        return this;
    }

    @Step("validate category name \"{categoryName}\" on category page")
    public CategoryPage validateCategoryName(String categoryName)
    {
        // Category name
        // Ensures the category headline contains the category name
        $("#titleCategoryName").shouldBe(text(categoryName));
        return this;
    }

    /**
     * @param position
     * @return
     */
    @Step("click on a product by name \"{productName}\"")
    public ProductDetailPage clickProductByName(String productName)
    {
        // Open the product detail page
        // Click on the product's image and open the product overview page
        // Click the product link to open the product detail page
        $(productOverview + " .thumbnails .thumbnail a > img.pImage[title='" + productName + "']").scrollTo().click();
        ProductDetailPage pdp = new ProductDetailPage();
        pdp.isExpectedPage();
        return pdp;
    }

    /**
     * @param searchTerm
     * @param searchTermExpectedCount
     */
    @Step("validate search results for \"{searchTerm}\" on category page")
    public CategoryPage validateSearchHits(String searchTerm)
    {
        $("#titleSearchText").should(exist);
        // Validate the headline contains the search phrase

        $("#titleSearchText").should(matchText(Neodymium.localizedText("search.results.text") + ": '" + searchTerm + "' \\(\\d+.*\\)"));
        // Verify that the correct search term is displayed
        // Validate the entered search phrase is still visible in the input
        search.validateSearchTerm(searchTerm);
        // Assert the Headline displays the search term.
        $(".header-container #searchTextValue").shouldHave(exactText(searchTerm));

        // Verify there are search results

        // There is at least one product in the results
        products.shouldHave(sizeGreaterThan(0));
        // Verify that there is the specified amount of results
        // The amount of products shown in the headline matches the expected value
        totalProductCount.shouldHave(matchText("\\d+"));
        return this;
    }

    /**
     * @param productName
     */
    @Step("validate product \"{productName}\" is visible on category page")
    public CategoryPage validateProductVisible(String productName)
    {
        $(productOverview + " .thumbnails .thumbnail a > img.pImage[title='" + productName + "']").shouldBe(visible);
        return this;
    }

    /**
     * @param categoryName
     */
    @Step("validate category page of category \"{categoryName}\" and assert visually")
    public CategoryPage validateAndVisualAssert(String categoryName)
    {
        validateStructureAndVisual();
        validateCategoryName(categoryName);
        return this;
    }
}
