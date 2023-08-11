package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.AccountOverviewPage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;

public class UserMenu extends AbstractComponent
{

    private final static SelenideElement userMenu = $("#userMenu");

    private static SelenideElement showUserMenu = $("#showUserMenu");

    @Override
    @Step("ensure availability user menu")
    public void isComponentAvailable()
    {
        showUserMenu.should(exist);
    }

    /// ----- user menu navigation ----- ///

    @Step("open user menu")
    public static void openUserMenu()
    {
        showUserMenu.hover();
        userMenu.waitUntil(visible, 9000);
    }

    @Step("close user menu")
    public static void closeUserMenu()
    {
        $(".top-menu").hover();
        userMenu.waitUntil(not(visible), 9000);
    }

    @Step("open register page from user menu")
    public RegisterPage openRegisterPage()
    {
        openUserMenu();
        userMenu.find(".goToRegistration").click();
        return new RegisterPage().isExpectedPage();
    }

    @Step("open login page from user menu")
    public LoginPage openLoginPage()
    {
        openUserMenu();
        userMenu.find(".goToLogin").click();
        return new LoginPage().isExpectedPage();
    }

    @Step("open account page from user menu")
    public AccountOverviewPage openAccountOverviewPage()
    {
        openUserMenu();
        userMenu.find(".goToAccountOverview").click();
        return new AccountOverviewPage().isExpectedPage();
    }

    @Step("perform logout")
    public HomePage logout()
    {
        openUserMenu();
        userMenu.find(".goToLogout").click();
        return new HomePage().isExpectedPage();
    }

    /// ----- validate user menu ----- ///

    @Step("validate that nobody is logged in")
    public void validateNotLoggedIn()
    {
        userMenu.find(".goToLogin").exists();
    }

    @Step("validate that somebody is logged in")
    public static boolean validateIsLoggedIn()
    {
        return userMenu.find(".firstName").exists();
    }

    @Step("validate that '{firstName}' is logged in")
    public void validateLoggedInName(String firstName)
    {
        openUserMenu();
        userMenu.find(".firstName").shouldHave(exactText(firstName));
        closeUserMenu();
    }
    
    @Step("validate logged in user menu")
    public static void validateStructure()
    {
        openUserMenu();
        
        // validate user icon
        $(".icon-user2").shouldBe(visible);

        // validate title
        userMenu.find(".userMenuHeader").shouldHave(text(Neodymium.localizedText("header.userMenu.title"))).shouldBe(visible);

        // validate buttons
        if (validateIsLoggedIn())
        {
            // if customer is logged in
            userMenu.find(".goToAccountOverview").shouldHave(exactText(Neodymium.localizedText("header.userMenu.accountOverview"))).shouldBe(visible);
            userMenu.find(".goToLogout").shouldHave(exactText(Neodymium.localizedText("header.userMenu.logout"))).shouldBe(visible);
            userMenu.find(".icon-info-large").shouldBe(visible);
            userMenu.find(".icon-log-out").shouldBe(visible);
        }
        else
        {
            // if customer is not logged in
            userMenu.find(".goToRegistration").shouldHave(exactText(Neodymium.localizedText("header.userMenu.createAccount"))).shouldBe(visible);
            userMenu.find(".goToLogin").shouldHave(exactText(Neodymium.localizedText("header.userMenu.signIn"))).shouldBe(visible);
            userMenu.find(".icon-user-add-outline").shouldBe(visible);
            userMenu.find(".icon-log-in").shouldBe(visible);
        }

        closeUserMenu();
    }
}
