package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

public class ErrorMessage extends AbstractComponent
{
    private SelenideElement errorMessageLogin = $("#loginForm .uk-width-3-4 .formReturn");
    private SelenideElement errorMessageRegister = $("#registerForm .formReturn");

    @Override
    @Step("ensure availability error message")
    public void isComponentAvailable()
    {
        errorMessageLogin.shouldBe(visible);
    }
    
    @Step("validate visibility of error message '{message}'")
    public void validateErrorMessageLogin(String message)
    {
        errorMessageLogin.shouldHave(exactText(message)).shouldBe(visible);
    }    
    
    @Step("validate visibility of error message for registration '{message}'")
    public void validateErrorMessageRegister(String message)
    {
        errorMessageRegister.shouldHave(exactText(message)).shouldBe(visible);
    }

    @Step("validate that no error message is visible")
    public void validateNoErrorMessageOnPage()
    {
        errorMessageLogin.shouldBe(visible);
    }
}
