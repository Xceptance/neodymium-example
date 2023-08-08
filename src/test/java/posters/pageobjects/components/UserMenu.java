package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.user.AccountOverviewPage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;

public class UserMenu extends AbstractComponent
{

    private final SelenideElement userMenu = $("#userMenu");

    private static SelenideElement showUserMenu = $("#showUserMenu");

    @Override
    @Step("ensure availability user menu")
    public void isComponentAvailable()
    {
        showUserMenu.should(exist);
    }
    
    /// ----- user menu navigation ----- ///
    
    @Step("open user menu")
    public void openUserMenu()
    {
        showUserMenu.hover();
        userMenu.waitUntil(visible, 9000);
    }

    @Step("close user menu")
    public void closeUserMenu()
    {
        $("#brand").hover();
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
    
    /// ----- validate user menu ----- ///
    
    @Step("validate logged in user menu")
    public static void validateStructure()
    {
        // TODO - verify for logged on user
    }
    
    @Step("validate strings in user menu")
    private static void validateGuestUserMenu(String text)
    {
        $$("#userMenu li").findBy(exactText(Neodymium.localizedText(text))).shouldBe(visible);
    }
    
    @Step("validate guest user menu")
    public static void validateGuestUserMenu()
    {   
        // validate user icon
        $(".icon-user2").shouldBe(visible);
        
        // open user window
        showUserMenu.hover();
        
        // validate structure user window
        validateGuestUserMenu("header.userMenu.greeting");
        validateGuestUserMenu("header.userMenu.createAccount");
        validateGuestUserMenu("header.userMenu.signIn");
        $("#userMenu .icon-user-add-outline").shouldBe(visible);
        $("#userMenu .icon-log-in").shouldBe(visible);
        
        // close user window
        $("#globalNavigation").hover();
    }
    
    @Step("validate that nobody is logged in")
    public void validateNotLoggedIn()
    {
        userMenu.find(".goToLogin").exists();
    }

    @Step("validate that somebody is logged in")
    public boolean validateIsLoggedIn()
    {
        return userMenu.find(".goToAccountOverview").exists();
    }
    
    @Step("validate that '{firstName}' is logged in")
    public void validateLoggedInName(String firstName)
    {
        openUserMenu();
        userMenu.find(".firstName").shouldHave(exactText(firstName));
        closeUserMenu();
    }
}
