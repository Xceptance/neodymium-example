package posters.tests.smoke;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.junit5.NeodymiumTest;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
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
    @DataItem
    private User user;

    private LoginPage loginPage;

    @BeforeEach
    public void setup()
    {
        // go to login page
        loginPage = OpenLoginPageFlow.flow();
        loginPage.validateStructure();

        // validate that nobody is logged in
        loginPage.header.userMenu.openUserMenu();
        loginPage.header.userMenu.validateUserIsNotLoggedIn();
        loginPage.header.userMenu.closeUserMenu();

        loginPage = new LoginPage().isExpectedPage();
    }

    @NeodymiumTest
    @DataSet(id = "happy path US")
    @DataSet(id = "happy path DE")
    public void testSuccessfulLogin()
    {
        var accountOverviewPage = loginPage.sendLoginForm(user);
        accountOverviewPage.validateSuccessfulLogin(user.getFirstName());
    }

    @NeodymiumTest
    @DataSet(id = "wrong password US")
    @DataSet(id = "wrong password DE")
    public void testLoginWithWrongPassword()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.validateFalseLogin(user.getEmail());
    }

    @NeodymiumTest
    @DataSet(id = "wrong email US")
    @DataSet(id = "wrong email DE")
    public void testLoginWithEmailFailure()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.validateFalseLogin(user.getEmail());
    }
    
    @NeodymiumTest
    @DataSet(id = "wrong email format US")
    @DataSet(id = "wrong email format DE")
    public void testLoginWithWrongEmailFormat()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.validateFalseEmailFormat(user.getEmail());
    }

    @NeodymiumTest
    @DataSet(id = "missing email US")
    @DataSet(id = "missing email DE")
    @DataSet(id = "missing password US")
    @DataSet(id = "missing password DE")
    public void testLoginWithoutRequiredFields()
    {
        loginPage.sendFalseLoginForm(user);
        loginPage.errorMessage.validateNoErrorMessageOnPage();
    }
}
