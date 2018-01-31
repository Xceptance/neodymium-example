/**
 * 
 */
package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import posters.pageObjects.pages.user.AccountOverViewPage;
import posters.pageObjects.pages.user.LoginPage;
import posters.pageObjects.pages.user.RegisterPage;
import posters.settings.Settings;

/**
 * @author pfotenhauer
 */
public class UserMenu extends AbstractComponent
{

    private final SelenideElement userMenu = $("#userMenu");

    private final SelenideElement showUserMenu = $("#showUserMenu");

    public void isComponentAvailable()
    {
        showUserMenu.should(exist);
    }

    /**
     * 
     */
    public void validateNotLoggedIn()
    {
        $("#userMenu .goToLogin").should(exist);
    }

    public void openUserMenu()
    {
        // Click the mini cart icon
        showUserMenu.scrollTo().click();
        // Wait for mini cart to appear
        // Wait for the mini cart to show
        userMenu.waitUntil(visible, Settings.timeout);
    }

    /**
     * 
     */
    public void closeUserMenu()
    {
        // Click the mini cart icon again
        showUserMenu.scrollTo().click();
        // Move the mouse out of the area
        $("a#brand").hover();
        // Wait for mini cart to disappear
        // Wait for the mini cart to disappear
        userMenu.waitUntil(not(visible), Settings.timeout);
    }

    /**
     * 
     */
    public LoginPage openLogin()
    {
        openUserMenu();
        userMenu.find(".goToLogin").scrollTo().click();
        return new LoginPage();
    }

    /**
     * 
     */
    public AccountOverViewPage openAccountOverview()
    {
        openUserMenu();
        userMenu.find(".goToAccountOverview").scrollTo().click();
        return new AccountOverViewPage();
    }

    /**
     * @return
     */
    public RegisterPage openRegister()
    {
        openUserMenu();
        userMenu.find("a.goToRegistration").scrollTo().click();
        return new RegisterPage();
    }

    /**
     * @param firstName
     */
    public void validateLoggedInName(String firstName)
    {
        // Click on the mini user menu symbol
        openUserMenu();
        // Asserts the Menu shows your first name.
        userMenu.find(".firstName").shouldHave(exactText(firstName));
        closeUserMenu();
        // Makes sure the mini menu element has the "logged" class active instead of the "not-logged" class.
        showUserMenu.find("span.glyphicon").shouldHave(cssClass("logged")).shouldHave(exactText(""));
    }

    /**
     * @return
     */
    public boolean isLoggedIn()
    {
        return userMenu.find(".goToAccountOverview").exists();
    }
}
