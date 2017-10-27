/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.tests.unit;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.OpenLoginPageFlow;
import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.LoginPage;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"
})
public class TLogin extends BasicTest
{

    @Test
    public void testSuccessfullLogin()
    {
        // Page types to use
        HomePage homePage;
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.com", "topsecret");

        // Goto homepage
        loginPage = new OpenLoginPageFlow().flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu().validateNotLoggedIn();

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
        loginPage = new OpenLoginPageFlow().flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu().validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongPassword(user.getEMail());
    }

    @Test
    public void testLoginWithExtendedPassword()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.com", "topsecret123");

        // Goto homepage
        loginPage = new OpenLoginPageFlow().flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu().validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongPassword(user.getEMail());
    }

    @Test
    public void testLoginWithExtendedEmail()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.company", "topsecret");

        // Goto homepage
        loginPage = new OpenLoginPageFlow().flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu().validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEMail());
    }

    @Test
    public void testLoginWithNoEmail()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "", "topsecret");

        // Goto homepage
        loginPage = new OpenLoginPageFlow().flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu().validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateNoErrorMessageOnPage();
    }

    @Test
    public void testLoginWithNoPassword()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("John", "Doe", "john@doe.com", "");

        // Goto homepage
        loginPage = new OpenLoginPageFlow().flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu().validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateNoErrorMessageOnPage();
    }

    @Test
    public void testLoginWithUnregisteredUser()
    {
        // Page types to use
        LoginPage loginPage;

        final User user = new User("Jane", "Doe", "jane@doe.com", "topsecret");

        // Goto homepage
        loginPage = new OpenLoginPageFlow().flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu().validateNotLoggedIn();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEMail());
    }

}
