package posters.tests.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.module.statement.testdata.SuppressDataSets;
import com.xceptance.neodymium.util.DataUtils;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.tests.testdata.dataobjects.User;
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
    private User user;

    @Before
    public void setup()
    {
        user = DataUtils.get(User.class);
    }

    @Test
    @DataSet(2)
//    @DataSet(4)
//    @DataSet(6)
    public void testRegistering()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to login page and validate
        var loginPage = homePage.userMenu.openLoginPage();
        loginPage.validateStructure();

        // go to register page and validate
        var registerPage = loginPage.openRegister();
        registerPage.validateStructure();

        loginPage = registerPage.sendRegisterForm(user);
        loginPage.validateSuccessfulRegistration();
        
        homePage = loginPage.sendLoginform(user);
        homePage.validateSuccessfulLogin(user);
    }

    @After
    public void after()
    {
        DeleteUserFlow.flow(user);
    }
}
