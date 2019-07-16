/**
 * 
 */
package posters.flows;

import io.qameta.allure.Step;
import posters.dataobjects.User;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.AccountOverviewPage;
import posters.pageobjects.pages.user.DeleteAccountPage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.PersonalDataPage;

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
        HomePage homePage = new HomePage();
        // ensure that the user is logged in
        LoginPage loginPage;
        if (!homePage.userMenu.isLoggedIn())
        {
            loginPage = homePage.userMenu.openLogin();
            homePage = loginPage.sendLoginform(user);
        }

        // go to account page
        AccountOverviewPage accountOverviewPage = homePage.userMenu.openAccountOverview();
        accountOverviewPage.validateStructure();

        // go to personal data page
        PersonalDataPage personalDataPage = accountOverviewPage.openPersonalData();
        personalDataPage.validateStructure();

        // go to account deletion page
        DeleteAccountPage deleteAccountPage = personalDataPage.openDeleteAccount();

        // delete the account
        homePage = deleteAccountPage.deleteAccount(user.getPassword());
        homePage.validateSuccessfulDeletedAccount();

        // verify that the account is not available anymore
        loginPage = homePage.userMenu.openLogin();
        loginPage.validateStructure();
        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEmail());

        return loginPage;
    }
}
