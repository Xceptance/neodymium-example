package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.CategoryPage;

public class TopNavigation extends AbstractComponent
{
    private SelenideElement categoryMenu = $("#categoryMenu");

    @Override
    @Step("ensure availability top navigation")
    public void isComponentAvailable()
    {
        categoryMenu.should(exist);
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
    
    // ----- validate top navigation ----- //
    
    @Step("validate name of components in navigation")
    public void validateNavComponent(String topCategory) 
    {
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText(topCategory))).shouldBe(visible);
    }

    @Step("validate name of components in sub navigation")
    public void validateSubNavComponent(String subCategory) 
    {
        $$(".navi ul.dropdown-menu li").findBy(exactText(Neodymium.localizedText(subCategory))).shouldBe(visible);
    }
    
    @Step("validate structure top navigation")
    public void validateStructure() 
    {
        // validate navigation bar
        validateNavComponent("header.topNavigation.1.title");
        validateNavComponent("header.topNavigation.2.title");
        validateNavComponent("header.topNavigation.3.title");
        validateNavComponent("header.topNavigation.4.title");
        
        // validate sub navigation "World Of Nature"
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText("header.topNavigation.1.title"))).hover();
        validateSubNavComponent("header.topNavigation.1.subCategory.1");
        validateSubNavComponent("header.topNavigation.1.subCategory.2");
        validateSubNavComponent("header.topNavigation.1.subCategory.3");
        
        // validate sub navigation "Dining"
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText("header.topNavigation.2.title"))).hover();
        validateSubNavComponent("header.topNavigation.2.subCategory.1");
        validateSubNavComponent("header.topNavigation.2.subCategory.2");
        validateSubNavComponent("header.topNavigation.2.subCategory.3");
        
        // validate sub navigation "Transportation"
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText("header.topNavigation.3.title"))).hover();
        validateSubNavComponent("header.topNavigation.3.subCategory.1");
        validateSubNavComponent("header.topNavigation.3.subCategory.2");
        validateSubNavComponent("header.topNavigation.3.subCategory.3");
        
        // validate sub navigation "Panoramas"
        $$(".has-dropdown").findBy(exactText(Neodymium.localizedText("header.topNavigation.4.title"))).hover();
        validateSubNavComponent("header.topNavigation.4.subCategory.1");
        validateSubNavComponent("header.topNavigation.4.subCategory.2");
        validateSubNavComponent("header.topNavigation.4.subCategory.3");
        validateSubNavComponent("header.topNavigation.4.subCategory.4");
        
        // close sub navigation
        $("#globalNavigation").hover();
    }
}
