package posters.tests.smoke;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.common.testdata.SuppressDataSets;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.DataUtils;

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
@SuppressDataSets
public class RegisterTest extends AbstractTest
{
    private User user;

    @BeforeEach
    public void setup()
    {
        user = DataUtils.get(User.class);
    }

    @NeodymiumTest
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

    @AfterEach
    public void after()
    {
        DeleteUserFlow.flow(user);
    }
}
