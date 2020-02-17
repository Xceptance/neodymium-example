package posters.tests.newTests.account;

import org.junit.Before;
import org.junit.Test;

import posters.dataobjects.User;
import posters.flows.RegisterAndLoginFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.PersonalDataPage;
import posters.tests.AbstractTest;

public class PersonalDataTest extends AbstractTest
{
    private User user;

    @Before
    public void dataSetup()
    {
        user = User.createRandomUser();
    }

    @Test
    public void validateAndChangeUserNameAndEmail()
    {
        HomePage homePage = RegisterAndLoginFlow.registerAndLogin(user);

        PersonalDataPage personalDataPage = homePage.userMenu.openAccountOverview().openPersonalData();
        personalDataPage.validateStructure();
        personalDataPage.validateUserData(user);

        user = User.createRandomUser();
        user.setFirstName("John");
        user.setLastName("Dorian");

        personalDataPage.changeNameOrEmail(user);
        personalDataPage.validateUserData(user);
    }

    @Test
    public void validateChangeEmailToExistingEmail()
    {
        HomePage homePage = RegisterAndLoginFlow.registerAndLogin(user);

        PersonalDataPage personalDataPage = homePage.userMenu.openAccountOverview().openPersonalData();

        user.setEmail("john@doe.com");

        personalDataPage.changeNameOrEmail(user);
        personalDataPage.validateErrorMessageAccountExists();
    }

    @Test
    public void validateChangeUserPassword()
    {
        HomePage homePage = RegisterAndLoginFlow.registerAndLogin(user);

        PersonalDataPage personalDataPage = homePage.userMenu.openAccountOverview().openPersonalData();
        personalDataPage.changePassword("newPassword", user.getPassword());
        user.setPassword("newPassword");

        personalDataPage.userMenu.logout();

        LoginPage loginPage = homePage.userMenu.openLogin();
        loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }
}
