package posters.tests.smoke;

import static com.codeborne.selenide.Selenide.$;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.junit5.NeodymiumTest;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.internal.shadowed.jackson.annotation.JsonTypeInfo.Id;
import posters.flows.OpenHomePageFlow;
import posters.flows.OpenLoginPageFlow;
import posters.pageobjects.pages.user.LoginPage;
import posters.pageobjects.pages.user.RegisterPage;
import posters.tests.AbstractTest;
import posters.tests.testdata.dataobjects.User;
import posters.pageobjects.pages.browsing.HomePage;
@Owner("AD")
@Severity(SeverityLevel.CRITICAL)
@Tag("smoke")
@Tag("registered")
public class RegisterTest extends AbstractTest
{
    @DataItem
    private User user;
    LoginPage loginPage;
    RegisterPage registerPage;

    @BeforeEach
    public void setup()
    {
        registerPage = prepareTest();
    }

    private RegisterPage prepareTest()
    {
        // go to login page
        loginPage = OpenLoginPageFlow.flow();        
        loginPage.setLanguage();
        loginPage.validateStructure();

        // validate that nobody is logged in
        loginPage.checkIfNoUserIsLoggedIn();
        loginPage.openRegisterPage();

        return new RegisterPage().isExpectedPage();
    }


    @NeodymiumTest
    @DataSet(1)
    public void testRegisteringExistingEmail()
    {
        
        // go to regi ster page and validate
        registerPage = loginPage.openRegisterPage();
        registerPage.validateStructure();

        // try and register
        registerPage.sendRegisterForm(user);
        registerPage.validateRegisterEmailExists();

    }

    @NeodymiumTest
    @DataSet(2)
    public void testRegisteringMissingInfo()
    {
        
        // go to register page and validate
        registerPage = loginPage.openRegisterPage();
        registerPage.validateStructure();
        $("#uk-tab-66-tab-1").click();

        // try and register
        registerPage.sendRegisterForm(user);
        registerPage.validateRegisterMissingInfo();

    }
}
