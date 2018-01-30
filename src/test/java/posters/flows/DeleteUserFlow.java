/**
 * 
 */
package posters.flows;

import posters.dataObjects.User;
import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.user.AccountOverViewPage;
import posters.pageObjects.pages.user.DeleteAccountPage;
import posters.pageObjects.pages.user.LoginPage;
import posters.pageObjects.pages.user.PersonalDataPage;

/**
 * @author pfotenhauer
 */
public class DeleteUserFlow
{
    /**
     * @param user
     */
    public static LoginPage flow(User user)
    {
        HomePage homePage = new HomePage();
        // ensure that the user is logged in
        LoginPage loginPage;
        if (!homePage.isLoggedIn())
        {
            loginPage = homePage.userMenu.openLogin();
            homePage = loginPage.sendLoginform(user);
        }

        // goto account page
        AccountOverViewPage accountOverviewPage = homePage.userMenu.openAccountOverview();
        accountOverviewPage.validateStructure();

        // goto personal data page
        PersonalDataPage personalDataPage = accountOverviewPage.openPersonalData();
        personalDataPage.validateStructure();

        // goto account deletion page
        DeleteAccountPage deleteAccountPage = personalDataPage.openDeleteAccount();

        // delete the account
        homePage = deleteAccountPage.deleteAccount(user.getPassword());
        homePage.validateSuccessfulDeletedAccount();

        // verify that the account is not available anymore
        loginPage = homePage.userMenu.openLogin();
        loginPage.validateStructure();
        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEMail());

        return loginPage;
    }
}
