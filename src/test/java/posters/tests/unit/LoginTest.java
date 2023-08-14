package posters.tests.unit;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.tests.AbstractTest;

@Owner("Tim Brown")
@Severity(SeverityLevel.NORMAL)
@Tag("functionality")
@Tag("registered")
public class LoginTest extends AbstractTest
{
    HomePage homePage;

    LoginPage loginPage;

    @Step("go to login page and validate")
    public void goToLoginPage()
    {
        // go to homepage
        homePage = OpenHomePageFlow.flow();
        homePage.userMenu.validateNotLoggedIn();

        // go to login page and validate
        loginPage = homePage.userMenu.openLoginPage();
        loginPage.validateStructure();
    }

    @Test
    @DataSet(1)
    public void testSuccessfulLogin()
    {
        // go to login page and validate
        goToLoginPage();

        // send login form
        homePage = loginPage.sendLoginForm(userData);
        homePage.validateSuccessfulLogin(userData.getFirstName());
    }

    @Test
    @DataSet(2)
    public void testLoginWithWrongPasswort()
    {
        // go to login page and validate
        goToLoginPage();

        loginPage.sendFalseLoginForm(userData);
        loginPage.validateWrongPassword(userData.getEmail());
    }

    @Test
    @DataSet(3)
    public void testLoginWithEmailFailure()
    {
        // go to login page and validate
        goToLoginPage();

        loginPage.sendFalseLoginForm(userData);
        loginPage.validateWrongEmail(userData.getEmail());
    }

    @Test
    @DataSet(4)
    @DataSet(5)
    public void testLoginWithoutRequiredFields()
    {
        // go to login page and validate
        goToLoginPage();

        loginPage.sendFalseLoginForm(userData);
        loginPage.errorMessage.validateNoErrorMessageOnPage();
    }
}