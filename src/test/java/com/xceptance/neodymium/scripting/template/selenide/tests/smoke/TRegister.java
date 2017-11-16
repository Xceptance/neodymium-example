/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.tests.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.DeleteUserFlow;
import com.xceptance.neodymium.scripting.template.selenide.flow.OpenHomePageFlow;
import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.LoginPage;
import com.xceptance.neodymium.scripting.template.selenide.page.user.RegisterPage;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;

import io.qameta.allure.Step;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"// , "FF_1024x768"
})
public class TRegister extends BasicTest
{
    User user;

    @Before
    public void setup()
    {
        user = new User(data);
    }

    @Test
    @Step("Register")
    public void testRegister()
    {
        // Goto homepage
        step("Goto homepage");
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Assure not logged in status
        step("Assure not logged in status");
        homePage.userMenu().validateNotLoggedIn();

        // Goto login form
        step("Goto login form");
        LoginPage loginPage = homePage.userMenu().openLogin();
        loginPage.validateStructure();

        // Goto register form
        step("Goto register form");
        RegisterPage registerPage = loginPage.openRegister();
        registerPage.validateStructure();

        loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.validateSuccessfulLRegistration();
        loginPage.validateStructure();

        homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @After
    public void after()
    {
        step("After Register - Delete User");
        new DeleteUserFlow(user).flow();
    }
}
