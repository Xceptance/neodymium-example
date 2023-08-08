package posters.flows;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.User;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;

public class DeleteUserFlow
{
    @Step("delete user flow")
    public static LoginPage flow(User user)
    {
        HomePage homePage = new HomePage();

        // go to account page and validate
        var accountOverviewPage = homePage.userMenu.openAccountOverviewPage();
        accountOverviewPage.validateStructure();

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
        var loginPage = homePage.userMenu.openLoginPage();
        loginPage.sendFalseLoginForm(user);
        loginPage.validateWrongEmail(user.getEmail());
        
        return loginPage.isExpectedPage();
    }
}
