package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class Header extends AbstractComponent
{

    private SelenideElement header = $("#header-navigation-bar");

    public Search search = new Search();

    public TopNavigation topNav = new TopNavigation();

    public UserMenu userMenu = new UserMenu();

    public MiniCart miniCart = new MiniCart();

    @Override
    @Step("validate availability header")
    public void ensureComponentAvailable()
    {
        header.should(exist);
    }

    @Override
    @Step("check availability of header")
    public boolean isAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(header, exist);
        return header.exists();
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
