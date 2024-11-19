package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.components.LoginHeader;

public class UserLoginPage extends AbstractBrowsingPage 
{
    public LoginHeader loginHeader = new LoginHeader();


    @Override
    @Step("ensure this is a home page")
    public UserLoginPage isExpectedPage()
    {
        super.isExpectedPage();
        $(".uk-section [src=\"imgs/logo.png\"]").should(exist);
        $(".uk-margin > .uk-grid .uk-width-3-4").should(exist);
        return this;
    }

        @Override
    @Step("validate structure home page")
    public void validateStructure()
    {

        // validate sale banner
        loginHeader.validateStructure();

        // validate intro
        $("#intro-text-homepage").shouldHave(exactText(Neodymium.localizedText("homePage.intro"))).shouldBe(visible);


        // validate shop all products button
        $(".btn-shop-all").shouldHave(exactText(Neodymium.localizedText("button.shopAllProducts"))).shouldBe(visible);
    }
}
