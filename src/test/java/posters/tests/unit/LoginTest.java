/**
 * 
 */
package posters.tests.unit;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.module.statement.testdata.SuppressDataSets;
import com.xceptance.neodymium.util.DataUtils;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.dataobjects.User;
import posters.flows.OpenLoginPageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Browser("Chrome_1024x768")
@Browser("Firefox_1024x768")
@Owner("Tim Brown")
@Severity(SeverityLevel.NORMAL)
@Tag("functionality")
@Tag("registered")
public class LoginTest extends AbstractTest
{
    private User user;

    @Before
    public void setup()
    {
        user = DataUtils.get(User.class);
    }

    @Test
    @DataSet(1)
    public void testSuccessfulLogin()
    {
        LoginPage loginPage = prepareTest();

        HomePage homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @Test
    @DataSet(2)
    @DataSet(3)
    public void testLoginWithPasswordFailure()
    {
        LoginPage loginPage = prepareTest();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongPassword(user.getEmail());
    }

    @Test
    @DataSet(4)
    public void testLoginWithEmailFailure()
    {
        LoginPage loginPage = prepareTest();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEmail());
    }

    @Test
    @DataSet(5)
    @DataSet(6)
    public void testLoginWithoutRequiredFields()
    {
        LoginPage loginPage = prepareTest();

        loginPage.sendFalseLoginform(user);
        loginPage.errorMessage.validateNoErrorMessageOnPage();
    }

    @Test
    @SuppressDataSets
    public void testLoginWithUnregisteredUser()
    {
        final User user = new User("Jens", "Doe", "jens@doe.com", "topsecret");

        LoginPage loginPage = prepareTest();

        loginPage.sendFalseLoginform(user);
        loginPage.validateWrongEmail(user.getEmail());
    }

    private LoginPage prepareTest()
    {
        // Page types to use
        LoginPage loginPage;

        // Go to login page
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // Assure not logged in status
        loginPage.userMenu.validateNotLoggedIn();

        return loginPage;
    }
}
