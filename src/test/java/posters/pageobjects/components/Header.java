package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class Header extends AbstractComponent
{
    public Search search = new Search();

    public TopNavigation topNav = new TopNavigation();

    public UserMenu userMenu = new UserMenu();
    
    public MiniCart miniCart = new MiniCart();
        
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
        search.validateStructure();
        topNav.validateStructure();
        userMenu.validateStructure();
        miniCart.validateStructure();
    }
}
