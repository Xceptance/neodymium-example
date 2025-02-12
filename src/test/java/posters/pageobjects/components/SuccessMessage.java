package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SuccessMessage extends AbstractComponent<SuccessMessage>
{
    private SelenideElement successMessage = $(".alert-success");

    @Override
    @Step("ensure availability success message")
    public SuccessMessage assertComponentAvailable()
    {
        return super.assertComponentAvailable();
    }

    @Override
    @Step("check availability of success message")
    public boolean isComponentAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(successMessage, exist);
        return successMessage.exists();
    }

    @Step("validate visibility of success message '{message}'")
    public void validateSuccessMessage(String message)
    {
        successMessage.shouldHave(exactText(message)).shouldBe(visible);
        successMessage.find(".btn-close").shouldBe(visible);
    }
}
