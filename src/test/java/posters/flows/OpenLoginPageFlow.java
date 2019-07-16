package posters.flows;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;

public class OpenLoginPageFlow
{
    @Step("open login page flow")
    public static LoginPage flow()
    {
        // initialize the session and go to home page
        HomePage homePage = OpenHomePageFlow.flow();

        // open login page and check for expected page
        LoginPage loginPage = homePage.userMenu.openLogin();
        loginPage.isExpectedPage();

        return loginPage;
    };
}
