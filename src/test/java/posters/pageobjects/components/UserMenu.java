package posters.pageobjects.components;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.AccountOverviewPage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class UserMenu extends AbstractComponent
{
    private SelenideElement userMenu = $("#user-menu");

    private SelenideElement showUserMenu = $("#show-user-menu");

    @Override
    @Step("ensure availability user menu")
    public void ensureComponentAvailable()
    {
        showUserMenu.should(exist);
    }

    @Override
    @Step("check availability of user menu")
    public boolean isAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(showUserMenu, exist);
        return showUserMenu.exists();
    }

    /// ========== user menu navigation ========== ///

    @Step("open user menu")
    public void openUserMenu()
    {
        showUserMenu.click(ClickOptions.usingJavaScript());
        userMenu.shouldBe(visible, Duration.ofMillis(9000));
    }

    @Step("close user menu")
    public void closeUserMenu()
    {
        $("#top-demo-disclaimer").click(ClickOptions.usingJavaScript());
        userMenu.shouldNotBe(visible, Duration.ofMillis(9000));
    }

    @Step("open register page from user menu")
    public RegisterPage openRegisterPage()
    {
        openUserMenu();
        userMenu.find("#go-to-registration").click(ClickOptions.usingJavaScript());
        return new RegisterPage().reached();
    }

    @Step("open login page from user menu")
    public LoginPage openLoginPage()
    {
        openUserMenu();
        userMenu.find("#go-to-login").click(ClickOptions.usingJavaScript());
        return new LoginPage().reached();
    }

    @Step("open account page from user menu")
    public AccountOverviewPage openAccountOverviewPage()
    {
        openUserMenu();
        userMenu.find("#go-to-account-overview").click(ClickOptions.usingJavaScript());
        return new AccountOverviewPage().reached();
    }

    @Step("perform logout")
    public HomePage logout()
    {
        openUserMenu();
        userMenu.find("#go-to-logout").click(ClickOptions.usingJavaScript());
        return new HomePage().reached();
    }

    /// ========== validate user menu ========== ///

    @Step("validate that nobody is logged in")
    public void checkIfNoUserIsLoggedIn()
    {
        userMenu.find("#go-to-login").exists();
    }

    @Step("validate that somebody is logged in")
    public boolean checkIfUserIsLoggedIn()
    {
        return userMenu.find(".first-name").exists();
    }

    @Step("validate that '{firstName}' is displayed in user menu")
    public void validateLoggedInUserName(String firstName)
    {
        openUserMenu();
        userMenu.find(".first-name").shouldHave(exactText(firstName));
        closeUserMenu();
    }

    @Step("validate logged in user menu")
    public void validateStructure()
    {
        openUserMenu();

        // validate user icon
        $(".icon-user2").shouldBe(visible);

        // validate title
        userMenu.find(".header-user-menu-heading").shouldHave(text(Neodymium.localizedText("header.userMenu.title"))).shouldBe(visible);

        // validate buttons
        if (checkIfUserIsLoggedIn())
        {
            // if customer is logged in
            userMenu.find("#go-to-account-overview").shouldHave(exactText(Neodymium.localizedText("button.accountOverview"))).shouldBe(visible);
            userMenu.find("#go-to-logout").shouldHave(exactText(Neodymium.localizedText("button.logout"))).shouldBe(visible);
            userMenu.find(".icon-info-large").shouldBe(visible);
            userMenu.find(".icon-log-out").shouldBe(visible);
        }
        else
        {
            // if customer is not logged in
            userMenu.find("#go-to-registration").shouldHave(exactText(Neodymium.localizedText("button.createAccount"))).shouldBe(visible);
            userMenu.find("#go-to-login").shouldHave(exactText(Neodymium.localizedText("button.signIn"))).shouldBe(visible);
            userMenu.find(".icon-user-add-outline").shouldBe(visible);
            userMenu.find(".icon-log-in").shouldBe(visible);
        }

        closeUserMenu();
    }
}
