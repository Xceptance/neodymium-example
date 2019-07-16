/**
 * 
 */
package posters.tests.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.dataobjects.User;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Owner("Lisa Smith")
@Severity(SeverityLevel.CRITICAL)
@Tag("smoke")
@Tag("registered")
@Browser("Chrome_1024x768")
@Browser("Firefox_1024x768")
public class RegisterTest extends AbstractTest
{
    User user;

    @Before
    public void setup()
    {
        user = DataUtils.get(User.class);
    }

    @Test
    @DataSet(2)
    @DataSet(4)
    @DataSet(id = "Jim's test")
    public void testRegistering()
    {
        // Go to homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Assure that nobody is logged in
        homePage.userMenu.validateNotLoggedIn();

        // Go to login form
        LoginPage loginPage = homePage.userMenu.openLogin();
        loginPage.validateStructure();

        // Go to register form
        RegisterPage registerPage = loginPage.openRegister();
        registerPage.validateStructure();

        loginPage = registerPage.sendRegisterForm(user, user.getPassword());
        loginPage.validateSuccessfulRegistration();
        loginPage.validateStructure();

        homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @After
    public void after()
    {
        DeleteUserFlow.flow(user);
    }
}
