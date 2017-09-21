/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.flow;

import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.scripting.template.selenide.page.PHome;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PAccountOverView;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PDeleteAccount;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PLogin;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PPersonalData;

/**
 * @author pfotenhauer
 */
public class FDeleteUser implements BasicFlow<PLogin>
{

    private User user;

    /**
     * @param user
     */
    public FDeleteUser(User user)
    {
        this.user = user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.flow.BasicFlow#flow()
     */
    @Override
    public PLogin flow()
    {
        PHome homePage = page(PHome.class);
        PAccountOverView accountOverviewPage;
        PPersonalData personalDataPage;
        PDeleteAccount deleteAccountPage;
        PLogin loginPage;

        accountOverviewPage = homePage.userMenu().openAccountOverview();
        accountOverviewPage.validateStructure();
        personalDataPage = accountOverviewPage.openPersonalData();

        personalDataPage.validateStructure();
        deleteAccountPage = personalDataPage.openDeleteAccount();
        homePage = deleteAccountPage.deleteAccount(user.getPassword());
        homePage.validateSuccessfulDeletedAccount();

        loginPage = homePage.userMenu().openLogin();
        loginPage.validateStructure();
        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEMail());

        return loginPage;
    }
}
