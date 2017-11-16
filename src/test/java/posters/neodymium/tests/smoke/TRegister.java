/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;

import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.neodymium.dataObjects.User;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.user.LoginPage;
import posters.pageObjects.pages.user.RegisterPage;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"
})
public class TRegister extends BasicTest
{
    User user;

    @Before
    public void setup()
    {
        user = new User(data);
    }

    @Test
    public void test()
    {
        // Goto homepage
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu().validateNotLoggedIn();

        // Goto login form
        LoginPage loginPage = homePage.userMenu().openLogin();
        loginPage.validateStructure();

        // Goto register form
        RegisterPage registerPage = loginPage.openRegister();
        registerPage.validateStructure();

        loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.validateSuccessfulLRegistration();
        loginPage.validateStructure();

        homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @After
    public void after()
    {
        new DeleteUserFlow(user).flow();
    }
}
