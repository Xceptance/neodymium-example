/**
 * 
 */
package posters.tests.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.User;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;
import posters.tests.BasicTest;

/**
 * @author pfotenhauer
 */
public class RegisterTest extends BasicTest
{
    User user;

    @Before
    public void setup()
    {
        user = DataUtils.get(User.class);
    }

    @Test
    public void testRegistering()
    {
        // Goto homepage
        step("Goto homepage");
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Assure not logged in status
        step("Assure not logged in status");
        homePage.userMenu.validateNotLoggedIn();

        // Goto login form
        step("Goto login form");
        LoginPage loginPage = homePage.userMenu.openLogin();
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
        DeleteUserFlow.flow(user);
    }
}
