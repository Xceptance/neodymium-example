/**
 * 
 */
package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import org.openqa.selenium.By;

import cucumber.api.java.en.When;
import posters.pageObjects.pages.browsing.CategoryPage;

/**
 * @author pfotenhauer
 */
public class TopNavigation extends AbstractComponent
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.component.BasicComponent#isComponentAvailable()
     */
    public void isComponentAvailable()
    {
        $("#categoryMenu").should(exist);
    }

    /**
     * @return
     */
    public CategoryPage clickCategory(String categoryName)
    {
        $(By.linkText(categoryName)).scrollTo().click();
        return page(CategoryPage.class);
    }

    public String getSubCategoryNameByPosition(int categoryPosition, int subCategoryPosition)
    {
        return $("#categoryMenu > ul > li:nth-of-type(" + categoryPosition
                 + ") ul.dropdown-menu li:nth-of-type(" + subCategoryPosition + ") a").attr("title");
    }

    public CategoryPage clickSubCategoryByPosition(int categoryPosition, int subCategoryPosition)
    {
        // Open the category page
        $("#categoryMenu > ul > li:nth-of-type(" + categoryPosition + ") a").hover();
        // Clicks the subcategory with position @{subCategoryPosition}
        // belonging to the category with position @{categoryPosition}
        $("#categoryMenu > ul > li:nth-of-type(" + categoryPosition + ") ul.dropdown-menu li:nth-of-type(" + subCategoryPosition + ") a").click();
        return page(CategoryPage.class);
    }

    @When("^I hover over \"([^\"]*)\" and click on \"([^\"]*)\"$")
    public CategoryPage clickSubCategoryByName(String categoryName, String subCategoryName)
    {
        // Open the category page
        $("#categoryMenu > ul > li > div[title='" + categoryName + "']").hover();
        // Clicks the subcategory with position @{subCategoryPosition}
        // belonging to the category with position @{categoryPosition}
        $("#categoryMenu > ul > li ul.dropdown-menu li > a[title='" + subCategoryName + "']").click();
        return page(CategoryPage.class);
    }

}
