package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;

public class CheckoutHeader extends AbstractComponent
{
    private SelenideElement progressIndicator = $(".progress-indicator");

    public UserMenu userMenu = new UserMenu();

    @Override
    @Step("ensure availability checkout header")
    public void assertComponentAvailable()
    {
        Assert.assertTrue(SelenideAddons.optionalWaitUntilCondition(progressIndicator, exist));

    }

    @Step("validate header")
    public void validateStructure()
    {
        $("#top-demo-disclaimer").shouldHave(exactText(Neodymium.localizedText("header.disclaimer"))).shouldBe(visible);
        $("#header-brand").shouldBe(visible);

        userMenu.validateStructure();
    }
}
