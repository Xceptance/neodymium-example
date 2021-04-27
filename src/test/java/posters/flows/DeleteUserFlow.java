package posters.flows;

import io.qameta.allure.Step;
import posters.dataobjects.User;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;

/**
 * @author pfotenhauer
 */
public class DeleteUserFlow
{
    /**
     * @param user
     */
    @Step("delete user flow")
    public static LoginPage flow(User user)
    {
        var homePage = new HomePage();

        // ensure that the user is logged in
        var loginPage = new LoginPage();
        if (!homePage.userMenu.isLoggedIn())
        {
            loginPage = homePage.userMenu.openLogin();
            homePage = loginPage.sendLoginform(user);
        }

        // go to account page
        var accountOverviewPage = homePage.userMenu.openAccountOverview();
        accountOverviewPage.validateStructure();

        // go to personal data page
        var personalDataPage = accountOverviewPage.openPersonalData();
        personalDataPage.validateStructure();

        // go to account deletion page
        var deleteAccountPage = personalDataPage.openDeleteAccount();

        // delete the account
        homePage = deleteAccountPage.deleteAccount(user.getPassword());
        homePage.validateSuccessfulDeletedAccount();

        // verify that the account is not available anymore
        loginPage = homePage.userMenu.openLogin();
        loginPage.validateStructure();
        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEmail());

        return loginPage.isExpectedPage();
    }
}
