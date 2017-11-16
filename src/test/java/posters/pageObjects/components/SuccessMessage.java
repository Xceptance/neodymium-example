package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class SuccessMessage extends AbstractComponent
{

    public void isComponentAvailable()
    {
        $("#successMessage").should(exist);
    }

    public void validateSuccessMessage(String message)
    {
        // Wait until javascript makes the success message visible
        // Waits until javascript makes the success message visible.
        $("#successMessage").shouldBe(visible);
        // Makes sure the correct text is displayed.
        $("#successMessage").shouldHave(exactText("× " + message));
    }
}
