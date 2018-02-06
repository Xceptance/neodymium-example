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

    public void isComponentAvailable()
    {
        successMessage.should(exist);
    }

    @Step("validate that the success message \"{message}\" is visible")
    public void validateSuccessMessage(String message)
    {
        // Wait until javascript makes the success message visible
        // Waits until javascript makes the success message visible.
        successMessage.shouldBe(visible);
        // Makes sure the correct text is displayed.
        successMessage.shouldHave(exactText("× " + message));
    }
}
