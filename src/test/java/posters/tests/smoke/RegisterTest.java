package posters.tests.smoke;

import org.junit.After;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.module.statement.testdata.SuppressDataSets;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.DeleteUserFlow;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;

@Owner("Lisa Smith")
@Severity(SeverityLevel.CRITICAL)
@Tag("smoke")
@Tag("registered")
@SuppressDataSets
public class RegisterTest extends AbstractTest
{
    @Test
    @DataSet(1)
    public void testRegistering()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to register page and validate
        var registerPage = homePage.userMenu.openRegisterPage();
        registerPage.validateStructure();
        
        // go to login page and validate
        var loginPage = registerPage.sendRegisterForm(userData);
        loginPage.validateStructure();
        loginPage.validateSuccessfulRegistration();
        
        // send login form
        homePage = loginPage.sendLoginForm(userData);
        homePage.validateSuccessfulLogin(userData.getFirstName());
    }

    @After
    public void after()
    {
        DeleteUserFlow.flow(userData);
    }
}
