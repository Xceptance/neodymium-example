package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
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
    public void validateStructure()
    {
        // validate about xceptance
        $("#footer .icon-twitter").shouldBe(visible);
        $("#footer .icon-facebook").shouldBe(visible);
        $("#footer .icon-linkedin").shouldBe(visible);
        $("#footer .icon-dribbble").shouldBe(visible);
        
        // validate copyright text
        footer.shouldHave(text(Neodymium.localizedText("footer.copyright")));
    }
}
