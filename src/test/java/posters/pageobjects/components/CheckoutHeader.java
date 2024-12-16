package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutHeader extends AbstractComponent
{

    private SelenideElement progressIndicator = $(".progress-indicator");

    public UserMenu userMenu = new UserMenu();

    @Override
    @Step("ensure availability checkout header")
    public void ensureComponentAvailable()
    {
        progressIndicator.should(exist);
    }

    @Override
    @Step("check availability of checkout header")
    public boolean isAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(progressIndicator, exist);
        return progressIndicator.exists();
    }

    @Step("validate header")
    public void validateStructure()
    {
        $("#top-demo-disclaimer").shouldHave(exactText(Neodymium.localizedText("header.disclaimer"))).shouldBe(visible);
        $("#header-brand").shouldBe(visible);
        ;
        userMenu.validateStructure();
    }
}
