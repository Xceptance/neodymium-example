package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class ErrorMessage extends AbstractComponent
{
    private SelenideElement errorMessage = $("#errorMessage");

    @Override
    @Step("ensure availability error message")
    public void isComponentAvailable()
    {
        errorMessage.should(exist);
    }

    @Step("validate that the error message {message} is visible")
    public void validateErrorMessage(String message)
    {
        errorMessage.shouldHave(exactText(Neodymium.localizedText(message))).shouldBe(visible);
    }

    @Step("validate that no error message is visible")
    public void validateNoErrorMessageOnPage()
    {
        errorMessage.shouldNotBe(visible);
    }
}
