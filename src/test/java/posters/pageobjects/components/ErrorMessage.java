package posters.pageobjects.components;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class ErrorMessage extends AbstractComponent
{
    private SelenideElement errorMessage = $(".alert-danger");

    @Override
    @Step("ensure availability error message")
    public void ensureComponentAvailable()
    {
        errorMessage.shouldBe(visible);
    }

    @Override
    @Step("check availability of error message")
    public boolean isAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(errorMessage, exist);
        return errorMessage.exists();
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
