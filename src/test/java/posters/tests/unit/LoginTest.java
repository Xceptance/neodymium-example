package posters.tests.unit;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenLoginPageFlow;
import posters.pageobjects.pages.user.LoginPage;
import posters.tests.AbstractTest;
import posters.tests.testdata.dataobjects.User;

@Owner("Tim Brown")
@Severity(SeverityLevel.NORMAL)
@Tag("functionality")
@Tag("registered")
public class LoginTest extends AbstractTest
{
    private User user;

    private LoginPage loginPage;

    @Before
    public void setup()
    {
        user = DataUtils.get(User.class);

        loginPage = prepareTest();
    }

    private LoginPage prepareTest()
    {
        // go to login page
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // validate that nobody is logged in
        loginPage.header.userMenu.validateNotLoggedIn();

        return new LoginPage().isExpectedPage();
    }

    @Test
    @DataSet(1)
    public void testSuccessfulLogin()
    {
        var homePage = loginPage.sendLoginForm(user);
        homePage.header.userMenu.validateStructure();
        homePage.validateSuccessfulLogin(user.getFirstName());
    }

    @Test
    @DataSet(2)
    public void testLoginWithWrongPasswort()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.validateFalseLogin(user.getEmail());
    }

    @Test
    @DataSet(3)
    public void testLoginWithEmailFailure()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.validateFalseLogin(user.getEmail());
    }

    @Test
    @DataSet(4)
    @DataSet(5)
    public void testLoginWithoutRequiredFields()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.errorMessage.validateNoErrorMessageOnPage();
    }
}