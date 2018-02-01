/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.After;
import org.junit.Test;

import posters.dataobjects.User;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;

/**
 * @author pfotenhauer
 */
public class RegisterFromUserMenuTest extends BasicTest
{
    final User user = new User("Jane", "Doe", "jane@doe.com", "topsecret");

    @Test
    public void testRegisteringFromUserMenu()
    {
        // Goto homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu.validateNotLoggedIn();

        // Goto register form
        RegisterPage registerPage = homePage.userMenu.openRegister();
        registerPage.validateStructure();

        LoginPage loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.validateSuccessfullRegistration();
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
