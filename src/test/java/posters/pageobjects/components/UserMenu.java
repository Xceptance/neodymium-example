package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import java.time.Duration;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.UserLoginPage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;

public class UserMenu extends AbstractComponent
{
    private SelenideElement userMenu = $(".rightTopMenu#portalHeader3");

    @Override
    @Step("ensure availability user menu")
    public void isComponentAvailable()
    {
        userMenu.should(exist);
    }

    /// ========== user menu navigation ========== ///



    @Step("open login page from user menu")
    public LoginPage openLoginPage()
    {
        userMenu.find("#go-to-login").click(ClickOptions.usingJavaScript());
        return new LoginPage().isExpectedPage();
    }

    @Step("open login page from user menu")
    public UserLoginPage openUserLoginPage()
    {
        userMenu.find("#go-to-login").click(ClickOptions.usingJavaScript());
        return new UserLoginPage().isExpectedPage();
    }



    @Step("perform logout")
    public HomePage logout()
    {
        userMenu.find("#go-to-logout").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }

    /// ========== validate user menu ========== ///

    @Step("validate that nobody is logged in")
    public void checkIfNoUserIsLoggedIn()
    {
        userMenu.find("#loginWin").exists();
    }

    @Step("validate that somebody is logged in")
    public boolean checkIfUserIsLoggedIn()
    {
        return userMenu.find("#welcomeMsg").exists();
    }

    @Step("validate that '{firstName}' is displayed in user menu")
    public void validateLoggedInUserName(String firstName, String lastName)
    {
        userMenu.find("#welcomeMsg").shouldHave(exactText(Neodymium.localizedText("homePage.welcomeMsg")+firstName+" "+lastName));
    }

    @Step("validate logged in user menu")
    public void validateStructure()
    {

        // validate user menu
        $(".rightTopMenu#portalHeader3").shouldBe(visible);
        $("#welcomeMsg").shouldBe(visible);
        userMenu.find("[uk-icon=\"user\"]").closest("a").shouldHave(exactText(" My profile")).shouldBe(visible);
        userMenu.find("[uk-icon=\"lock\"]").closest("a").shouldHave(exactText(" Logout")).shouldBe(visible);
        

    }
}
