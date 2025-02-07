package posters.pageobjects.components;

import com.xceptance.neodymium.util.Neodymium;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Header extends AbstractComponent
{
    public Search search = new Search();

    public TopNavigation topNav = new TopNavigation();

    public UserMenu userMenu = new UserMenu();

    public MiniCart miniCart = new MiniCart();

    public LocaleMenu localeMenu = new LocaleMenu();

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
        $("#top-demo-disclaimer").click();
        $("#header-brand").shouldBe(visible);
        search.validateStructure();
        topNav.validateStructure();
        userMenu.validateStructure();
        miniCart.validateStructure();
        localeMenu.validateStructure();
    }
}
