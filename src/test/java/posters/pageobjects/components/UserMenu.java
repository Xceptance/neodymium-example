package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
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
        showUserMenu.scrollTo().click();
        userMenu.waitUntil(visible, Neodymium.configuration().selenideTimeout());
    }

    @Step("close user menu")
    public void closeUserMenu()
    {
        $("#brand").hover();
        userMenu.waitUntil(not(visible), Neodymium.configuration().selenideTimeout());
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
        userMenu.find(".goToLogin").should(exist);
    }

    @Step("validate that somebody is logged in")
    public boolean isLoggedIn()
    {
        return userMenu.find(".goToAccountOverview").exists();
    }
    
    
    // ---------------------------------------------------------------------
    


    // TODO - check if needed
    @Step("open login page from user menu")
    public LoginPage openLogin()
    {
        openUserMenu();
        userMenu.find(".goToLogin").scrollTo().click();
        return new LoginPage().isExpectedPage();
    }

    // TODO - check if needed
    @Step("open account page from user menu")
    public AccountOverviewPage openAccountOverview()
    {
        openUserMenu();
        userMenu.find(".goToAccountOverview").scrollTo().click();
        return new AccountOverviewPage().isExpectedPage();
    }

    // TODO - check if needed
    @Step("open register page from user menu")
    public RegisterPage openRegister()
    {
        openUserMenu();
        userMenu.find(".goToRegistration").scrollTo().click();
        return new RegisterPage().isExpectedPage();
    }

    // TODO - check if needed
    @Step("validate that '{firstName}' is logged in")
    public void validateLoggedInName(String firstName)
    {
        // Click on the mini user menu symbol
        openUserMenu();
        // Asserts the Menu shows your first name.
        userMenu.find(".firstName").shouldHave(exactText(firstName));
        closeUserMenu();
        // Makes sure the mini menu element has the "logged" class active instead of the "not-logged" class.
        showUserMenu.find(".icon-user2").shouldHave(cssClass("logged")).shouldHave(exactText(""));
    }
}
