package posters.tests.smoke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.junit5.NeodymiumTest;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import posters.flows.OpenLoginPageFlow;
import posters.pageobjects.pages.user.LoginPage;
import posters.tests.AbstractTest;
import posters.tests.testdata.dataobjects.User;

@Owner("AE")
@Severity(SeverityLevel.NORMAL)
@Tag("functionality")
@Tag("registered")
public class UserLoginTest extends AbstractTest
{
    @DataItem
    private User user;

    private LoginPage loginPage;

    @BeforeEach
    public void setup()
    {
        loginPage = prepareTest();
    }

    private LoginPage prepareTest()
    {
        // go to login page
        loginPage = OpenLoginPageFlow.flow();
        loginPage.setLanguage();
        loginPage.validateStructure();

        // validate that nobody is logged in
        loginPage.checkIfNoUserIsLoggedIn();

        return new LoginPage().isExpectedPage();
    }

    @NeodymiumTest
    @DataSet(1)
    public void testSuccessfulLogin()
    {
        var homePage = loginPage.sendLoginForm(user);
        homePage.header.userMenu.validateStructure();
        homePage.validateSuccessfulLogin(user.getFirstName(), user.getLastName());
    }

    @NeodymiumTest
    @DataSet(2)
    public void testLoginWithWrongPasswort()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.validateFalseLogin(user.getEmail());
    }

    @NeodymiumTest
    @DataSet(3)
    public void testLoginWithEmailFailure()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.validateFalseLogin(user.getEmail());
    }

    @NeodymiumTest
    @DataSet(4)
    @DataSet(5)
    public void testLoginWithoutRequiredFields()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.validateMissingLoginInfo(user.getEmail());
        //loginPage.errorMessage.validateNoErrorMessageOnPage();
    }
}