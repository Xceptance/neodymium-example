/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.FDeleteUser;
import com.xceptance.neodymium.scripting.template.selenide.flow.FOpenHomepage;
import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.scripting.template.selenide.page.PHome;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PLogin;
import com.xceptance.neodymium.scripting.template.selenide.page.user.PRegister;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;
import com.xceptance.neodymium.testdata.GenericFileReader;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"
})
public class TRegisterV1 extends BasicTest
{
    User user;

    @Parameter(0)
    public String firstname;

    @Parameter(1)
    public String lastname;

    @Parameter(2)
    public String email;

    @Parameter(3)
    public String password;

    @Parameters
    public static List<Object[]> data()
    {
        return GenericFileReader.readFile();
    }

    @Before
    public void setup()
    {
        user = new User(firstname, lastname, email, password);
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

    public void test2()
    {
        test();
    }

    @After
    public void after()
    {
        new FDeleteUser(user).flow();
    }
}
