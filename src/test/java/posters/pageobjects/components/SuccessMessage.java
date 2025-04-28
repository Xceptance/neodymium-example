package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;

public class SuccessMessage extends AbstractComponent
{
    private SelenideElement successMessage = $(".alert-success");

    @Override
    @Step("ensure availability success message")
    public void assertComponentAvailable()
    {
        Assert.assertTrue(SelenideAddons.optionalWaitUntilCondition(successMessage, exist));
    }

    @Step("validate visibility of success message '{message}'")
    public void validateSuccessMessage(String message)
    {
        successMessage.shouldHave(exactText(message)).shouldBe(visible);
        successMessage.find(".btn-close").shouldBe(visible);
    }
}
