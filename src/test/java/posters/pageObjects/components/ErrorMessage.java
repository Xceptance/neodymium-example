package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ErrorMessage extends AbstractComponent
{

    public void isComponentAvailable()
    {
        $("#errorMessage").should(exist);
    }

    public void validateErrorMessage(String message)
    {
        // Wait until javascript makes the error message visible
        // Waits until javascript makes the error message visible.
        $("#errorMessage").shouldBe(visible);
        // Makes sure the correct text is displayed.
        $("#errorMessage").shouldHave(exactText("× " + message));
    }

    /**
     * 
     */
    public void validateNoErrorMessageOnPage()
    {
        // Check that the error message is not visible.
        $("#errorMessage").shouldNotBe(visible);
    }
}
