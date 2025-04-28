package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;

public class ErrorMessage extends AbstractComponent
{
    private SelenideElement errorMessage = $(".alert-danger");

    @Override
    @Step("ensure availability error message")
    public void assertComponentAvailable()
    {
        Assert.assertTrue(SelenideAddons.optionalWaitUntilCondition(errorMessage, exist));
    }

    @Step("validate visibility of error message '{message}'")
    public void validateErrorMessage(String message)
    {
        errorMessage.shouldHave(exactText(message)).shouldBe(visible);
        errorMessage.find(".btn-close").shouldBe(visible);
    }

    @Step("validate that no error message is visible")
    public void validateNoErrorMessageOnPage()
    {
        errorMessage.shouldNotBe(visible);
    }
}
