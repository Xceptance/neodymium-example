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
        $("#mainWin #portalHeader").should(exist);
    }

    @Step("validate header")
    public void validateStructure()
    {
        $("#mainWin #portalHeader #portalHeader1").shouldBe(visible);
        $("#mainWin #portalHeader #portalHeader2").shouldBe(visible);
        $("#mainWin #portalHeader #portalHeader3").shouldBe(visible);
        userMenu.validateStructure();
    }
}
