package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

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
    
    @Step("validate visibility of error message '{message}'")
    public void validateErrorMessage(String message)
    {
        errorMessage.find("strong").shouldHave(exactText(message)).shouldBe(visible);
        errorMessage.find(".close").shouldHave(exactText("×")).shouldBe(visible);
    }

    @Step("validate that no error message is visible")
    public void validateNoErrorMessageOnPage()
    {
        errorMessage.shouldNotBe(visible);
    }
}
