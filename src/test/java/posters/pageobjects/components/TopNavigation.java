/**
 * 
 */
package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Random;

import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
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

    @Step("get the subcategory name")
    public String getSubCategoryNameByPosition(int categoryPosition, int subCategoryPosition)
    {
        return $("#categoryMenu > ul > li:nth-of-type(" + categoryPosition + ") ul.dropdown-menu li:nth-of-type(" + subCategoryPosition + ") a").attr("title");
    }

    @Step("click a subcategory")
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

    @Step("click on \"{categoryName}\" category")
    public CategoryPage clickCategory(String categoryName)
    {
        $(By.linkText(categoryName)).scrollTo().click();
        return new CategoryPage();
    }

    @Step("click on a \"{subCategoryName}\" subcategory within \"{categoryName}\"")
    public CategoryPage clickSubCategoryByName(String categoryName, String subCategoryName)
    {
        // Open the category page
        $(By.linkText(categoryName)).hover();
        // Clicks the subcategory with position @{subCategoryPosition}
        // belonging to the category with position @{categoryPosition}
        $(By.linkText(subCategoryName)).click();
        return new CategoryPage();
    }

    @Step("get a random subcategory")
    public CategoryPage getRandomSubcategory(Random random)
    {
        // compute random horizontal category position (for posters: random value from 1 to 4)
        // ["World of nature", "Dining", "Transportation", "Panoramas"]
        int categoryPositionX = random.nextInt(3) + 1;

        // compute random vertical category position (for posters: random value from 1 to 4)
        // if (categoryPositionX == 4) == true then categoryPositionY has range from 1 to 4
        // otherwise from 1 to 3, because any category has 3 subcategories instead of category 4, which has 4
        int categoryPositionY;
        if (categoryPositionX == 4)
            categoryPositionY = random.nextInt(3) + 1;
        else
            categoryPositionY = random.nextInt(2) + 1;

        CategoryPage categoryPage = clickSubCategoryByPosition(categoryPositionX, categoryPositionY);

        return categoryPage;
    }

    /**
     * @param position
     * @return
     */
    @Step("click on a product by name \"{productName}\"")
    public CategoryPage getSubcategoryByName(String categoryName)
    {
        // Open the product detail page
        // Click on the product's image and open the product overview page
        // Click the product link to open the product detail page
        $(".dropdown-menu li > a[title='" + categoryName + "']").scrollTo().click();
        return new CategoryPage();
    }
}
