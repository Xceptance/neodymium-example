/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.tests.smoke;

import org.junit.After;
import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.DeleteUserFlow;
import com.xceptance.neodymium.scripting.template.selenide.flow.OpenHomePageFlow;
import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.LoginPage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.RegisterPage;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"
})
public class TRegisterFromUserMenu extends BasicTest
{
    final User user = new User("Jane", "Doe", "jane@doe.com", "topsecret");

    @Test
    public void test()
    {
        // Goto homepage
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu().validateNotLoggedIn();

        // Goto register form
        RegisterPage registerPage = homePage.userMenu().openRegister();
        registerPage.validateStructure();

        LoginPage loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.validateSuccessfulLRegistration();
        loginPage.validateStructure();

        homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @After
    public void after()
    {
        new DeleteUserFlow(user).flow();
    }
}
