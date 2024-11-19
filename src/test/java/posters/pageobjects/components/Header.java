package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class Header extends AbstractComponent
{


    public UserMenu userMenu = new UserMenu();
    
        
    @Override
    @Step("validate availability header")
    public void isComponentAvailable()
    {
        $("#header-navigation-bar").should(exist);
    }

    @Step("validate header")
    public void validateStructure()
    {
        $("#top-demo-disclaimer").shouldHave(exactText(Neodymium.localizedText("header.disclaimer"))).shouldBe(visible);
        $("#header-brand").shouldBe(visible);
        userMenu.validateStructure();
    }
}
