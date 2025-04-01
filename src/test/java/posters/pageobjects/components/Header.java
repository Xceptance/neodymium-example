package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class Header extends AbstractComponent<Header>
{

    private SelenideElement header = $("#header-navigation-bar");

    public Search search = new Search();

    public TopNavigation topNav = new TopNavigation();

    public UserMenu userMenu = new UserMenu();

    public MiniCart miniCart = new MiniCart();

    public LocaleMenu localeMenu = new LocaleMenu();

    @Override
    @Step("validate availability header")
    public Header assertComponentAvailable()
    {
        return super.assertComponentAvailable();
    }

    @Override
    @Step("check availability of header")
    public boolean isComponentAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(header, exist);
        return header.exists();
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
