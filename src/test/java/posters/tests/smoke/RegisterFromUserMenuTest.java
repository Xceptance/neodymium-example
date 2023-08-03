package posters.tests.smoke;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
@Severity(SeverityLevel.NORMAL)
@Tag("smoke")
@Tag("registered")
public class RegisterFromUserMenuTest extends AbstractTest
{
    private User user;

    @Before
    public void setup()
    {
        user = DataUtils.get(User.class);
    }

    @Test
    public void testRegisteringFromUserMenu()
    {
        // Go to homepage
        var homePage = OpenHomePageFlow.flow();
        homePage.validateStructure();

        // Assure not logged in status
        homePage.userMenu.validateNotLoggedIn();

        // Go to register form
        var registerPage = homePage.userMenu.openRegister();
        registerPage.validateStructure();

        var loginPage = registerPage.sendRegisterForm(user);
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
