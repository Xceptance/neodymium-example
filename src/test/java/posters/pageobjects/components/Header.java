package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;

public class Header extends AbstractComponent
{   
    @Override
    @Step("validate availability header")
    public void isComponentAvailable()
    {
        $("#globalNavigation").should(exist);
    }
    
    /// ----- validate header ----- ///
    
    @Step("validate structure header")
    public void validateStructure() 
    {   
        $("#brand").shouldBe(visible);

        Search.validateStructure();
        TopNavigation.validateStructure();
        UserMenu.validateStructure();
        MiniCart.validateStructure();
        SaleBanner.validateStructure();
    }
}
