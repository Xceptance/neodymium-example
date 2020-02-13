package posters.tests.newTests.account;

import org.junit.Before;
import org.junit.Test;

import posters.dataobjects.User;
import posters.flows.OpenPageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;
import posters.tests.AbstractTest;

public class RegistrationTest extends AbstractTest
{
    private User user;

    @Before
    public void dataSetup()
    {
        user = User.createRandomUser();
    }

    @Test
    public void testRegistration()
    {
        HomePage homePage = OpenPageFlow.openHomePage();
        RegisterPage registerPage = homePage.userMenu.openRegister();
        registerPage.validateStructure();

        LoginPage loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.validateSuccessfulRegistration();
    }

}
