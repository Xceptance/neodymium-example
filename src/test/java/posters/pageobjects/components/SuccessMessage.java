package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

public class SuccessMessage extends AbstractComponent
{
    private SelenideElement successMessage = $("#successMessage");

    @Override
    @Step("ensure availability success message")
    public void isComponentAvailable()
    {
        successMessage.should(exist);
    }
    
    /// ========== validate success message ========== ///

    @Step("validate visibility of success message '{message}'")
    public void validateSuccessMessage(String message)
    {
        successMessage.find("strong").shouldHave(exactText(message)).shouldBe(visible);
        successMessage.find(".close").shouldHave(exactText("×")).shouldBe(visible);
    }
}
