package posters.flows;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.AccountOverviewPage;
import posters.tests.testdata.dataobjects.User;

public class DeleteUserFlow
{
    @Step("delete '{user}' flow")
    public static void flow(User user)
    {
        HomePage homePage = new HomePage();

        // check if the user is logged in if the login failed
        if (!homePage.header.userMenu.isUserLoggedIn())
        {
            var loginPage = homePage.header.userMenu.openLoginPage();
            homePage = loginPage.sendLoginForm(user);

            // TODO check error message if account doesn't exist and return since then no account needs to be deleted
        }

        AccountOverviewPage accountOverviewPage = homePage.header.userMenu.openAccountOverviewPage();

        // go to personal data page and validate
        var personalDataPage = accountOverviewPage.openPersonalData();
        personalDataPage.validateStructure();
        personalDataPage.validatePersonalData(user);

        // go to account deletion page and validate
        var deleteAccountPage = personalDataPage.openDeleteAccountPage();
        deleteAccountPage.validateStructure();

        // delete the account and validate success message
        homePage = deleteAccountPage.deleteAccount(user.getPassword());
        homePage.validateSuccessfulDeletedAccount();

        // verify that the account is not available anymore
        var loginPage = homePage.header.userMenu.openLoginPage();
        loginPage.sendFalseLoginForm(user);
        loginPage.validateFalseLogin(user.getEmail());

        // go to homePage
        homePage = loginPage.openHomePage();
    }
}
