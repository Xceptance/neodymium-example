/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.After;
import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;

import posters.neodymium.flow.DeleteUserFlow;
import posters.neodymium.flow.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.objects.User;
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
public class TRegisterFromUserMenu extends BasicTest
{
    final User user = new User("Jane", "Doe", "jane@doe.com", "topsecret");

    @Test
    public void test()
    {
        // Goto homepage
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu().validateNotLoggedIn();

        // Goto register form
        RegisterPage registerPage = homePage.userMenu().openRegister();
        registerPage.validateStructure();

        LoginPage loginPage = registerPage.sendRegisterForm(user, user.getPassword());
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
