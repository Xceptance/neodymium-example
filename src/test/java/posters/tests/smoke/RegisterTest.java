package posters.tests.smoke;

import org.junit.After;
import org.junit.Test;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.common.testdata.DataSet;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.dataobjects.User;

@Owner("Lisa Smith")
@Severity(SeverityLevel.CRITICAL)
@Tag("smoke")
@Tag("registered")
public class RegisterTest extends AbstractTest
{
    @DataItem
    private User user;
    
    @Test
    @DataSet(2)
    public void testRegistering()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to register page and validate
        var registerPage = homePage.header.userMenu.openRegisterPage();
        registerPage.validateStructure();

        // go to login page and validate
        var loginPage = registerPage.sendRegisterForm(user);
        loginPage.validateStructure();
        loginPage.validateSuccessfulRegistration();

        // send login form
        var accountOverviewPage = loginPage.sendLoginForm(user);
        accountOverviewPage.validateSuccessfulLogin(user.getFirstName());
    }

    @After
    public void after()
    {
        DeleteUserFlow.flow(user);
    }
}
