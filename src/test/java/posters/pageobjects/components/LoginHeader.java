package posters.pageobjects.components;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.time.Duration;

import com.codeborne.selenide.ClickOptions;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class LoginHeader  extends AbstractComponent 
{
    public UserMenu userMenu = new UserMenu();

    @Override
    public void isComponentAvailable()
    {
        $("#loginWin > div > div > div > div > div >  div.uk-text-center.uk-grid").should(exist);
    }

    @Step("validate sale banner")
    public void validateStructure()
    {
        $(".uk-section [src=\"imgs/logo.png\"]").should(exist);
        $(".uk-margin > .uk-grid .uk-width-3-4").shouldHave(text(Neodymium.localizedText("loginPage.heading")));
    }
}





