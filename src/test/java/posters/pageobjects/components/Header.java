package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;

public class Header extends AbstractComponent
{
    public Search search = new Search();

    public TopNavigation topNav = new TopNavigation();

    public UserMenu userMenu = new UserMenu();
    
    public MiniCart miniCart = new MiniCart();
    
    public SaleBanner saleBanner = new SaleBanner();
    
    @Override
    @Step("validate availability header")
    public void isComponentAvailable()
    {
        $("#header-navigation-bar").should(exist);
    }

    /// ----- validate header at homepage----- ///
    @Step("validate header")
    public void validateStructureHomePage()
    {
        $("#header-brand").shouldBe(visible);

        search.validateStructure();
        topNav.validateStructure();
        userMenu.validateStructure();
        miniCart.validateStructure();
        saleBanner.validateStructure();
    }

    /// ----- validate header----- ///
    @Step("validate header")
    public void validateStructure()
    {
        $("#header-brand").shouldBe(visible);

        search.validateStructure();
        topNav.validateStructure();
        userMenu.validateStructure();
        miniCart.validateStructure();
    }
}
