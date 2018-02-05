/**
 * 
 */
package posters.tests.unit;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;

import posters.dataobjects.User;
import posters.flows.OpenLoginPageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768",
  "FF_1024x768"
})
public class LoginTest extends AbstractTest
{

    @Test
    public void testSuccessfullLogin()
    {
        // Page types to use
        HomePage homePage;
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.com", "topsecret");

        // Goto homepage
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu.validateNotLoggedIn();

        homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @Test
    public void testLoginWithWrongPassword()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.com", "notsecret");

        // Goto homepage
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu.validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongPassword(user.getEmail());
    }

    @Test
    public void testLoginWithExtendedPassword()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.com", "topsecret123");

        // Goto homepage
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu.validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongPassword(user.getEmail());
    }

    @Test
    public void testLoginWithExtendedEmail()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.company", "topsecret");

        // Goto homepage
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu.validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEmail());
    }

    @Test
    public void testLoginWithNoEmail()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "", "topsecret");

        // Goto homepage
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu.validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.errorMessage.validateNoErrorMessageOnPage();
    }

    @Test
    public void testLoginWithNoPassword()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.com", "");

        // Goto homepage
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu.validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.errorMessage.validateNoErrorMessageOnPage();
    }

    @Test
    public void testLoginWithUnregisteredUser()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("Jens", "Doe", "jens@doe.com", "topsecret");

        // Goto homepage
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu.validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEmail());
    }

}
