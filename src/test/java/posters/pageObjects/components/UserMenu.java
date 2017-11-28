/**
 * 
 */
package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import posters.pageObjects.pages.user.AccountOverViewPage;
import posters.pageObjects.pages.user.LoginPage;
import posters.pageObjects.pages.user.RegisterPage;
import posters.settings.Settings;

/**
 * @author pfotenhauer
 */
public class UserMenu extends AbstractComponent
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.component.BasicComponent#isComponentAvailable()
     */
    public void isComponentAvailable()
    {
        $("#showUserMenu").should(exist);
    }

    /**
     * 
     */
    @And("^I am not logged in$")
    public void validateNotLoggedIn()
    {
        $("#userMenu .goToLogin").should(exist);
    }

    public void openUserMenu()
    {
        // Click the mini cart icon
        $("#showUserMenu").scrollTo().click();
        // Wait for mini cart to appear
        // Wait for the mini cart to show
        $("#userMenu").waitUntil(visible, Settings.timeout);
    }

    /**
     * 
     */
    public void closeUserMenu()
    {
        // Click the mini cart icon again
        $("#showUserMenu").scrollTo().click();
        // Move the mouse out of the area
        $("a#brand").hover();
        // Wait for mini cart to disappear
        // Wait for the mini cart to disappear
        $("#userMenu").waitUntil(not(visible), Settings.timeout);
    }

    /**
     * 
     */
    @When("^I click the login button$")
    public LoginPage openLogin()
    {
        openUserMenu();
        $("#userMenu .goToLogin").scrollTo().click();
        return page(LoginPage.class);
    }

    /**
     * 
     */
    public AccountOverViewPage openAccountOverview()
    {
        openUserMenu();
        $("#userMenu .goToAccountOverview").scrollTo().click();
        return page(AccountOverViewPage.class);
    }

    /**
     * @return
     */
    @When("^I click the register button in the header$")
    public RegisterPage openRegister()
    {
        openUserMenu();
        $("#userMenu a.goToRegistration").scrollTo().click();
        return page(RegisterPage.class);
    }
}
