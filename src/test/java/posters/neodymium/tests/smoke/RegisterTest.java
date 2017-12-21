/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import posters.dataObjects.User;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.user.LoginPage;
import posters.pageObjects.pages.user.RegisterPage;

/**
 * @author pfotenhauer
 */
public class RegisterTest extends BasicTest
{
    User user;

    @Before
    public void setup()
    {
        user = new User(data);
    }

    @Test
    public void testRegistering()
    {
        // Goto homepage
        step("Goto homepage");
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Assure not logged in status
        step("Assure not logged in status");
        homePage.userMenu().validateNotLoggedIn();

        // Goto login form
        step("Goto login form");
        LoginPage loginPage = homePage.userMenu().openLogin();
        loginPage.validateStructure();

        // Goto register form
        step("Goto register form");
        RegisterPage registerPage = loginPage.openRegister();
        registerPage.validateStructure();

        loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.validateSuccessfullRegistration();
        loginPage.validateStructure();

        homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @After
    public void after()
    {
        step("After Register - Delete User");
        new DeleteUserFlow(user).flow();
    }
}