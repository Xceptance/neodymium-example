package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.AccountOverviewPage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;

public class UserMenu extends AbstractComponent
{

    private SelenideElement userMenu = $("#user-menu");

    private SelenideElement showUserMenu = $("#show-user-menu");

    @Override
    @Step("ensure availability user menu")
    public void isComponentAvailable()
    {
        showUserMenu.should(exist);
    }

    /// ========== user menu navigation ========== ///

    @Step("open user menu")
    public void openUserMenu()
    {
        showUserMenu.click(ClickOptions.usingJavaScript());
        userMenu.waitUntil(visible, 9000);
    }

    @Step("close user menu")
    public void closeUserMenu()
    {
        $("#top-demo-disclaimer").click(ClickOptions.usingJavaScript());
        userMenu.waitUntil(not(visible), 9000);
    }

    @Step("open register page from user menu")
    public RegisterPage openRegisterPage()
    {
        openUserMenu();
        userMenu.find("#go-to-registration").click(ClickOptions.usingJavaScript());
        return new RegisterPage().isExpectedPage();
    }

    @Step("open login page from user menu")
    public LoginPage openLoginPage()
    {
        openUserMenu();
        userMenu.find("#go-to-login").click(ClickOptions.usingJavaScript());
        return new LoginPage().isExpectedPage();
    }

    @Step("open account page from user menu")
    public AccountOverviewPage openAccountOverviewPage()
    {
        openUserMenu();
        userMenu.find("#go-to-account-overview").click(ClickOptions.usingJavaScript());
        return new AccountOverviewPage().isExpectedPage();
    }

    @Step("perform logout")
    public HomePage logout()
    {
        openUserMenu();
        userMenu.find("#go-to-logout").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }

    /// ========== validate user menu ========== ///

    @Step("validate that nobody is logged in")
    public void validateNotLoggedIn()
    {
        userMenu.find("#go-to-login").exists();
    }

    @Step("validate that somebody is logged in")
    public boolean validateIsLoggedIn()
    {
        return userMenu.find(".first-name").exists();
    }

    @Step("validate that '{firstName}' is displayed in user menu")
    public void validateLoggedInName(String firstName)
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
        if (validateIsLoggedIn())
        {
            // if customer is logged in
            userMenu.find("#go-to-account-overview").shouldHave(exactText(Neodymium.localizedText("header.userMenu.accountOverview"))).shouldBe(visible);
            userMenu.find("#go-to-logout").shouldHave(exactText(Neodymium.localizedText("header.userMenu.logout"))).shouldBe(visible);
            userMenu.find(".icon-info-large").shouldBe(visible);
            userMenu.find(".icon-log-out").shouldBe(visible);
        }
        else
        {
            // if customer is not logged in
            userMenu.find("#go-to-registration").shouldHave(exactText(Neodymium.localizedText("header.userMenu.createAccount"))).shouldBe(visible);
            userMenu.find("#go-to-login").shouldHave(exactText(Neodymium.localizedText("header.userMenu.signIn"))).shouldBe(visible);
            userMenu.find(".icon-user-add-outline").shouldBe(visible);
            userMenu.find(".icon-log-in").shouldBe(visible);
        }

        closeUserMenu();
    }
}
