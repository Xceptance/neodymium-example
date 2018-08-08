package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class Footer extends AbstractComponent
{
    private SelenideElement footer = $("#footer");

    public void isComponentAvailable()
    {
        footer.should(exist);
    }

    @Step("validate the footer")
    public void validate()
    {
        isComponentAvailable();
        // Asserts the footer contains the correct text.
        footer.shouldHave(exactText(Neodymium.localizedText("footer.text")));
    }
}
