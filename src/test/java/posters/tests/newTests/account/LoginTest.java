package posters.tests.newTests.account;

import org.junit.Before;
import org.junit.Test;

import posters.dataobjects.User;
import posters.flows.OpenPageFlow;
import posters.flows.RegisterAndLoginFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.tests.AbstractTest;

public class LoginTest extends AbstractTest
{
    private User user;

    @Before
    public void dataSetup()
    {
        user = User.createRandomUser();
    }

    @Test
    public void testSuccessfulLogin()
    {
        LoginPage loginPage = RegisterAndLoginFlow.registerUser(user);

        loginPage.validateStructure();

        HomePage homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @Test
    public void testLoginWithWrongEmail()
    {
        LoginPage loginPage = RegisterAndLoginFlow.registerUser(user);

        loginPage.sendFormWithData("wrong@email.com", user.getPassword());
        loginPage.validateWrongEmail("wrong@email.com");
    }

    @Test
    public void testLoginWithIncorrectPassword()
    {
        LoginPage loginPage = RegisterAndLoginFlow.registerUser(user);

        loginPage.sendFormWithData(user.getEmail(), "wrong_Password");
        loginPage.validateWrongPassword(user.getEmail());
    }

    @Test
    public void loginWithEmptyFields()
    {
        LoginPage loginPage = OpenPageFlow.openLoginPage();
        loginPage.sendFormWithData("", "");
        loginPage.isExpectedPage();
    }

    @Test
    public void testLoginWithUnregisteredUser()
    {
        LoginPage loginPage = OpenPageFlow.openLoginPage();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEmail());
    }
}
