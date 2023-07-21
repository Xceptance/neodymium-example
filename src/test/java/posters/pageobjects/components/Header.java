package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class Header extends AbstractComponent
{   
    // validate availability header
    public void isComponentAvailable()
    {
        $("#globalNavigation").should(exist);
    }
    
    // extended by Jonas
    @Step("validate structure header")
    public void validateStructure() 
    {
        // validate availability header
        isComponentAvailable();
        
        // validate company logo
        $("#brand").shouldBe(visible);
        
        // validate searchbar
        Search.validateStructure();
        
        // validate top navigation
        TopNavigation.validateStructure();
        
        // validate user menu
        UserMenu.validateStructure();
        
        // validate shopping cart menu
        MiniCart.validateStructure();
        
        // validate sale banner
        SaleBanner.validateStructure();
    }
}
