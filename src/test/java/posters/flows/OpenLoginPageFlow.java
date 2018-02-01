package posters.flows;

import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;

public class OpenLoginPageFlow
{
    public static LoginPage flow()
    {
        // initialize the session and goto home page
        HomePage homePage = OpenHomePageFlow.flow();

        // open login page and check for expected page
        LoginPage loginPage = homePage.userMenu.openLogin();
        loginPage.isExpectedPage();

        return loginPage;
    };
}
