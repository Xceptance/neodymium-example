/**
 * 
 */
package posters.flows;

import static com.codeborne.selenide.Selenide.page;

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

    private User user;

    /**
     * @param user
     */
    public DeleteUserFlow(User user)
    {
        this.user = user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.flow.BasicFlow#flow()
     */
    public LoginPage flow()
    {
        HomePage homePage = page(HomePage.class);
        LoginPage loginPage;
        if (!homePage.isLoggedIn())
        {
            loginPage = homePage.userMenu().openLogin();
            homePage = loginPage.sendLoginform(user);
        }

        AccountOverViewPage accountOverviewPage = homePage.userMenu().openAccountOverview();
        accountOverviewPage.validateStructure();
        PersonalDataPage personalDataPage = accountOverviewPage.openPersonalData();

        personalDataPage.validateStructure();
        DeleteAccountPage deleteAccountPage = personalDataPage.openDeleteAccount();
        homePage = deleteAccountPage.deleteAccount(user.getPassword());
        homePage.validateSuccessfulDeletedAccount();

        loginPage = homePage.userMenu().openLogin();
        loginPage.validateStructure();
        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEMail());

        return loginPage;
    }
}
