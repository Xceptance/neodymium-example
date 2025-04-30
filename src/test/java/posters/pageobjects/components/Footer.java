package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class Footer extends AbstractComponent
{
    private SelenideElement footer = $("#loginWin .footerInBox");

    @Override
    @Step("ensure availability footer")
    public void isComponentAvailable()
    {
        footer.should(exist);
    }
    
    @Step("validate footer")
    public void validateStructure()
    {
        // validate footer text
        footer.shouldHave(text(Neodymium.localizedText("footer.text")));
    }
}
