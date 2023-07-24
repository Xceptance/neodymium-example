package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Random;

import org.openqa.selenium.By;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.CategoryPage;

public class TopNavigation extends AbstractComponent
{
    SelenideElement categoryMenu = $("#categoryMenu");

    @Override
    @Step("ensure availability top navigation")
    public void isComponentAvailable()
    {
        categoryMenu.should(exist);
    }
    
    // ----- validate content top navigation ----- //
    
    @Step("validate structure top navigation")
    public static void validateStructure() 
    {
        // validate navigation bar
        validateNavComponent("header.worldOfNature");
        validateNavComponent("header.dining");
        validateNavComponent("header.transportation");
        validateNavComponent("header.panoramas");
        
        // validate sub navigation "World Of Nature"
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText("header.worldOfNature"))).hover();
        validateSubNavComponent("header.animals");
        validateSubNavComponent("header.flowers");
        validateSubNavComponent("header.trees");
        
        // validate sub navigation "Dining"
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText("header.dining"))).hover();
        validateSubNavComponent("header.coldCuts");
        validateSubNavComponent("header.mainDishes");
        validateSubNavComponent("header.sweets");
        
        // validate sub navigation "Transportation"
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText("header.transportation"))).hover();
        validateSubNavComponent("header.airTravel");
        validateSubNavComponent("header.classicCars");
        validateSubNavComponent("header.railways");
        
        // validate sub navigation "Panoramas"
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText("header.panoramas"))).hover();
        validateSubNavComponent("header.architecture");
        validateSubNavComponent("header.fireworks");
        validateSubNavComponent("header.landscapes");
        validateSubNavComponent("header.xxlPanoramas");
        
        // close sub navigation
        $("#globalNavigation").hover();
    }
    
    @Step("validate name of components in navigation")
    public static void validateNavComponent(String topCategory) 
    {
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText(topCategory))).shouldBe(visible);
    }

    @Step("validate name of components in sub navigation")
    public static void validateSubNavComponent(String subCategory) 
    {
        $$(".navi ul.dropdown-menu li").findBy(exactText(Neodymium.localizedText(subCategory))).shouldBe(visible);
    }
    
    // ----- category navigation ------ //
    
    @Step("click on '{categoryName}' category")
    public CategoryPage clickCategory(String topCategory)
    {
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText(topCategory))).scrollTo().click();
        return new CategoryPage().isExpectedPage();
    }
    
    @Step("click on a '{subCategoryName}' subcategory within '{categoryName}'")
    public CategoryPage clickSubCategory(String topCategory, String subCategory)
    {
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText(topCategory))).hover();
        $$(".navi ul.dropdown-menu li").findBy(exactText(Neodymium.localizedText(subCategory))).click();
        return new CategoryPage().isExpectedPage();
    }
    
    // ----------------------------------------------------------------

    // TODO - check if needed
    @Step ("get the category name")
    public static String getCategoryNameByPosition(int categoryPosition) 
    {
        return $("#categoryMenu li:nth-of-type(" + categoryPosition + ") a").attr("title");
    }
    
    // TODO - check if needed
    @Step("get the subcategory name")
    public static String getSubCategoryNameByPosition(int categoryPosition, int subCategoryPosition)
    {
        return $("#categoryMenu li:nth-of-type(" + categoryPosition + ") .dropdown li:nth-of-type(" + subCategoryPosition + ") a").attr("title");
    }

    // TODO - improve
    @Step("click a subcategory")
    public CategoryPage clickSubCategoryByPosition(int categoryPosition, int subCategoryPosition)
    {
        // Open the category page
        SelenideElement topCat = $$("#categoryMenu .has-dropdown").get(categoryPosition - 1);
        topCat.hover();
        // Clicks the subcategory with position @{subCategoryPosition}
        // belonging to the category with position @{categoryPosition}
        topCat.find(".dropdown-menu a", subCategoryPosition - 1).click();
        return new CategoryPage().isExpectedPage();
    }

    // TODO - check if needed
    @Step("click on a product by name '{productName}'")
    public CategoryPage clickSubcategoryByName(String subCategoryName)
    {
        categoryMenu.find(".dropdown-menu li > a[title='" + subCategoryName + "']").parent().parent().parent().hover();
        categoryMenu.find(By.linkText(subCategoryName)).click();

        return new CategoryPage().isExpectedPage();
    }

    // TODO - check if needed
    @Step("get a random sub category name ")
    public String getRandomSubcategoryName(Random random)
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

        return getSubCategoryNameByPosition(categoryPositionX, categoryPositionY);
    }
}
