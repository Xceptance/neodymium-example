package posters.flows;

import static com.codeborne.selenide.Selenide.page;

import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.user.LoginPage;

public class OpenLoginPageFlow
{

    public LoginPage flow()
    {
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.userMenu().openLogin();
        return page(LoginPage.class);
    };
}
