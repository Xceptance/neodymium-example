/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.component.CTopNav;

/**
 * @author pfotenhauer
 */
public class CategoryPage extends BasicPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#isAwaitedPage()
     */
    @Override
    protected boolean isAwaitedPage()
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
    public void validate()
    {
        // Amount of results
        // Assures the amount of posters displayed in the headline is not 0.
        $("#totalProductCount").shouldNotBe(exactText("0"));
        // Results
        // Assures there's at least one product shown
        $("#product0").shouldBe(visible);

    }

    public CTopNav topNav()
    {
        return new CTopNav();
    }

    /**
     * @param row
     * @param column
     * @return
     */
    public ProductPage clickProductByIndex(int row, int column)
    {
        // Open the product detail page
        // Clicks a product by index. Because of the html code, this requires x and y coordinates.
        $("#productOverview > .row:nth-child(" + row + ") li:nth-of-type(" + column + ") h4.pName").click();
        return page(ProductPage.class);
    }

    /**
     * @return
     */
    public String getProducNametByIndex(int row, int column)
    {
        return $("#productOverview > .row:nth-child(" + row + ") li:nth-of-type(" + column + ") h4.pName").text();
    }
}
