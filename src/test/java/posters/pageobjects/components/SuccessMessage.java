package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class SuccessMessage extends AbstractComponent
{
    private SelenideElement successMessage = $(".alert-success");

    @Override
    @Step("ensure availability success message")
    public void ensureComponentAvailable()
    {
        successMessage.shouldBe(visible);
    }

    @Override
    @Step("check availability of success message")
    public boolean isAvailable()
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
