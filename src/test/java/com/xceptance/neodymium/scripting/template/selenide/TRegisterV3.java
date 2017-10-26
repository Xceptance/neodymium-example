/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.datapool.UserPool;
import com.xceptance.neodymium.scripting.template.selenide.flow.FDeleteUser;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.scripting.template.selenide.page.PHome;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PLogin;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PRegister;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"
})
public class TRegisterV3 extends BasicTest
{
    public User user;

    @Before
    public void setup()
    {
        user = new UserPool().getRandomEntry();
    }

    @Test
    public void test()
    {
        // Page types to use
        PHome homePage;
        PLogin loginPage;
        PRegister registerPage;

        // Goto homepage
        homePage = new FOpenHomepage().flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu().validateNotLoggedIn();

        // Goto login form
        loginPage = homePage.userMenu().openLogin();
        loginPage.validateStructure();

        // Goto register form
        registerPage = loginPage.openRegister();
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
        new FDeleteUser(user).flow();
    }
}
