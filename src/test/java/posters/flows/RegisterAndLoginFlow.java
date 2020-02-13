package posters.flows;

import posters.dataobjects.User;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;

public class RegisterAndLoginFlow
{
    public static HomePage registerAndLogin(User user)
    {
        RegisterPage registerPage = OpenPageFlow.openRegistrationPage();

        LoginPage loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        return loginPage.sendLoginform(user);
    }

    public static LoginPage registerUser(User user)
    {
        RegisterPage registerPage = OpenPageFlow.openRegistrationPage();

        registerPage.userMenu.validateNotLoggedIn();

        return registerPage.sendRegisterForm(user, user.getPassword());
    }
}
