/**
 * 
 */
package posters.tests.smoke;

import org.junit.After;
import org.junit.Test;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.dataobjects.User;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Owner("Lisa Smith")
@Severity(SeverityLevel.NORMAL)
@Tag("smoke")
@Tag("registered")
public class RegisterFromUserMenuTest extends AbstractTest
{
    final User user = new User("Jane", "Doe", "jane@doe.com", "topsecret");

    @Test
    public void testRegisteringFromUserMenu()
    {
        // Go to homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu.validateNotLoggedIn();

        // Go to register form
        RegisterPage registerPage = homePage.userMenu.openRegister();
        registerPage.validateStructure();

        LoginPage loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.validateSuccessfulRegistration();
        loginPage.validateStructure();

        homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @After
    public void after()
    {
        DeleteUserFlow.flow(user);
    }
}
