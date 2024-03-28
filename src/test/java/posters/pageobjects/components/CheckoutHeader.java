package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class CheckoutHeader extends AbstractComponent
{
    public UserMenu userMenu = new UserMenu();
    
    @Override
    @Step("ensure availability checkout header")
    public void isComponentAvailable()
    {
        $(".progress-indicator").should(exist);
    }
    
    @Step("validate header")
    public void validateStructure()
    {
        $("#top-demo-disclaimer").shouldHave(exactText(Neodymium.localizedText("header.disclaimer"))).shouldBe(visible);
        $("#header-brand").shouldBe(visible);;
        userMenu.validateStructure();
    }
}
