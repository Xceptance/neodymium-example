package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutHeader extends AbstractComponent<CheckoutHeader>
{

    private SelenideElement progressIndicator = $(".progress-indicator");

    public UserMenu userMenu = new UserMenu();

    @Override
    @Step("ensure availability checkout header")
    public CheckoutHeader assertComponentAvailable()
    {
        return super.assertComponentAvailable();
    }

    @Override
    @Step("check availability of checkout header")
    public boolean isComponentAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(progressIndicator, exist);
        return progressIndicator.exists();
    }

    @Step("validate header")
    public void validateStructure()
    {
        $("#top-demo-disclaimer").shouldHave(exactText(Neodymium.localizedText("header.disclaimer"))).shouldBe(visible);
        $("#header-brand").shouldBe(visible);

        userMenu.validateStructure();
    }
}
