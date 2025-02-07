package posters.tests.smoke;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
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

    @NeodymiumTest
    @DataSet(id = "register test US")
    @DataSet(id = "register test DE")
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

        // send login form and validate the user is logged in
        homePage = loginPage.sendLoginForm(user);
    }

    @AfterEach
    public void after()
    {
        DeleteUserFlow.flow(user);
    }
}
