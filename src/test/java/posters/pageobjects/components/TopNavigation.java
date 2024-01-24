package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.CategoryPage;

public class TopNavigation extends AbstractComponent
{
    private SelenideElement categoryMenu = $("#header-categories");

    @Override
    @Step("ensure availability top navigation")
    public void isComponentAvailable()
    {
        categoryMenu.should(exist);
    }
    
    // ========== category navigation ==========- //
    
    @Step("click on the top category '{topCategory}'")
    public CategoryPage clickCategory(String topCategory)
    {
        $$("#header-categories .nav-link[href*=\"/topCategory/\"]").findBy(exactText(topCategory)).click(ClickOptions.usingJavaScript());
        return new CategoryPage().isExpectedPage();
    }
    
    @Step("click on the '{subCategory}' sub category within the top category '{topCategory}'")
    public CategoryPage clickSubCategory(String topCategory, String subCategory)
    {
        $$(".nav-item.dropdown").findBy(exactText(topCategory)).hover();
        $$("#header-categories ul.dropdown-menu li").findBy(exactText(subCategory)).click(ClickOptions.usingJavaScript());
        return new CategoryPage().isExpectedPage();
    }
    
    // ========== validate top navigation ========== //
    
    @Step("validate top category name '{topCategory}'")
    public void validateNavComponent(String topCategory) 
    {
        $$(".nav-item").findBy(exactText(topCategory)).shouldBe(visible);
    }

    @Step("validate sub category name '{subCategory}'")
    public void validateSubNavComponent(String subCategory) 
    {
        $$(".nav-item li a.dropdown-item").findBy(exactText(subCategory)).shouldBe(visible);
    }
    
    @Step("validate structure top navigation")
    public void validateStructure() 
    {
        // validate navigation bar
        validateNavComponent(Neodymium.localizedText("header.topNavigation.1.title"));
        validateNavComponent(Neodymium.localizedText("header.topNavigation.2.title"));
        validateNavComponent(Neodymium.localizedText("header.topNavigation.3.title"));
        validateNavComponent(Neodymium.localizedText("header.topNavigation.4.title"));
        
        // validate sub navigation "World Of Nature"
        $$(".nav-item").findBy(exactText(Neodymium.localizedText("header.topNavigation.1.title"))).hover();
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.1.subCategory.1"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.1.subCategory.2"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.1.subCategory.3"));
        
        // validate sub navigation "Dining"
        $$(".nav-item").findBy(exactText(Neodymium.localizedText("header.topNavigation.2.title"))).hover();
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.2.subCategory.1"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.2.subCategory.2"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.2.subCategory.3"));
        
        // validate sub navigation "Transportation"
        $$(".nav-item").findBy(exactText(Neodymium.localizedText("header.topNavigation.3.title"))).hover();
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.3.subCategory.1"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.3.subCategory.2"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.3.subCategory.3"));
        
        // validate sub navigation "Panoramas"
        $$(".nav-item").findBy(exactText(Neodymium.localizedText("header.topNavigation.4.title"))).hover();
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.4.subCategory.1"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.4.subCategory.2"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.4.subCategory.3"));
        validateSubNavComponent(Neodymium.localizedText("header.topNavigation.4.subCategory.4"));
        
        // close sub navigation
        $("#top-demo-disclaimer").hover();
    }
}
