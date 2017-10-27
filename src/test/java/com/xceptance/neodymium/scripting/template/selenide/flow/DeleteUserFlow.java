/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.flow;

import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.AccountOverViewPage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.DeleteAccountPage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.LoginPage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PersonalDataPage;

/**
 * @author pfotenhauer
 */
public class DeleteUserFlow extends AbstractFlow<LoginPage>
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
    @Override
    public LoginPage flow()
    {
        HomePage homePage = page(HomePage.class);
        AccountOverViewPage accountOverviewPage = homePage.userMenu().openAccountOverview();
        accountOverviewPage.validateStructure();
        PersonalDataPage personalDataPage = accountOverviewPage.openPersonalData();

        personalDataPage.validateStructure();
        DeleteAccountPage deleteAccountPage = personalDataPage.openDeleteAccount();
        homePage = deleteAccountPage.deleteAccount(user.getPassword());
        homePage.validateSuccessfulDeletedAccount();

        LoginPage loginPage = homePage.userMenu().openLogin();
        loginPage.validateStructure();
        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEMail());

        return loginPage;
    }
}
