/**
 * 
 */
package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

import posters.pageobjects.pages.browsing.CategoryPage;

/**
 * @author pfotenhauer
 */
public class TopNavigation extends AbstractComponent
{
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
        return new CategoryPage();
    }

    public String getSubCategoryNameByPosition(int categoryPosition, int subCategoryPosition)
    {
        return $("#categoryMenu > ul > li:nth-of-type(" + categoryPosition + ") ul.dropdown-menu li:nth-of-type(" + subCategoryPosition + ") a").attr("title");
    }

    public CategoryPage clickSubCategoryByPosition(int categoryPosition, int subCategoryPosition)
    {
        // Open the category page
        SelenideElement topCat = $$("#categoryMenu .header-menu-item").get(categoryPosition - 1);
        topCat.hover();
        // Clicks the subcategory with position @{subCategoryPosition}
        // belonging to the category with position @{categoryPosition}
        topCat.find(".dropdown-menu a", subCategoryPosition - 1).click();
        return new CategoryPage();
    }

    public CategoryPage clickSubCategoryByName(String categoryName, String subCategoryName)
    {
        // Open the category page
        $(By.linkText(categoryName)).hover();
        // Clicks the subcategory with position @{subCategoryPosition}
        // belonging to the category with position @{categoryPosition}
        $(By.linkText(subCategoryName)).click();
        return new CategoryPage();
    }

}
