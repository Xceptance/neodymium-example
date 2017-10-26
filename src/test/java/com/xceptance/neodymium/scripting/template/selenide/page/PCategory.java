/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

/**
 * @author pfotenhauer
 */
public class PCategory extends BasicPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#isAwaitedPage()
     */
    @Override
    public boolean isExpectedPage()
    {
        return $("#productOverview").exists();
    }

    public void validateCategoryName(String categoryName)
    {
        // Category name
        // Ensures the category headline contains the category name
        $("#titleCategoryName").shouldBe(text(categoryName));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#validate()
     */
    @Override
    public void validateStructure()
    {
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
    public PProduct clickProductByPosition(int row, int column)
    {
        // Open the product detail page
        // Clicks a product by index. Because of the html code, this requires x and y coordinates.

        $("#productOverview > .row:nth-child(" + row + ") li:nth-of-type(" + column + ") h4.pName").scrollTo().click();
        return page(PProduct.class);
    }

    /**
     * @param row
     * @param column
     * @return
     */
    public String getProductNameByPosition(int row, int column)
    {
        return $("#productOverview > .row:nth-child(" + row + ") li:nth-of-type(" + column + ") h4.pName").text();
    }

    /**
     * @param index
     * @return
     */
    public PProduct clickProductByIndex(int index)
    {
        // Open the product detail page
        // Click on the product's image and open the product overview page
        // Click the product link to open the product detail page
        $("#product" + index + " img").scrollTo().click();
        return page(PProduct.class);

    }

    /**
     * @param index
     * @return
     */
    public String getProductNameByIndex(int index)
    {
        // Get the product name
        return $("#product" + index + " .pInfo .pName").text();
    }

    /**
     * @param searchTerm
     * @param searchTermExpectedCount
     */
    public void validateSearchHits(String searchTerm, int searchTermExpectedCount)
    {
        $("#titleSearchText").should(exist);
        // Validate the headline contains the search phrase
        // \\(" + searchTermExpectedCount + " *\\)
        $("#titleSearchText").should(matchText("Your results for your search: '" + searchTerm + "' \\(" + searchTermExpectedCount + ".*\\)"));
        // Verify that the correct search term is displayed
        // Validate the entered search phrase is still visible in the input
        this.search().openSearch();
        // Validate the entered search phrase is still visible in the input
        $("#searchForm #s").should(visible);
        // Validate the entered search phrase is still visible in the input
        $("#searchForm #s").should(exactValue(searchTerm));
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
     * @param categoryName
     */
    public void validate(String categoryName)
    {
        validateStructure();
        validateCategoryName(categoryName);
    }
}
