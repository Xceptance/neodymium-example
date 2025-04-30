package posters.tests.smoke;

import static com.codeborne.selenide.Selenide.open;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.AllureAddons;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import net.bytebuddy.asm.Advice.Return;
import posters.flows.OpenLoginPageFlow;
import posters.pageobjects.pages.browsing.AdminPortalPage;
import posters.pageobjects.pages.browsing.AdminPortalLoginPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.LoginPage;
import posters.tests.AbstractTest;
import posters.tests.testdata.dataobjects.User;

@Severity(SeverityLevel.TRIVIAL)
@Owner("AB")
@Tag("smoke")
@TmsLink("end-of-script-developer-and-now")
@Issue("148")
@Link(url = "https://ask.xceptance.de/t/end-of-script-developer-and-now/148", type = "custom", name = "DemoLink")
@DisplayName("HomepageTest")
public class HomePageTest extends AbstractTest
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



    // homepage validation
    @NeodymiumTest
    @DataSet(1)
    public void testHomepageStudentPortal()
    {
        HomePage homePage = loginPage.sendLoginForm(user);
        homePage.waitForPageLoad();
        homePage.title.validateTitle(Neodymium.localizedText("homePage.title"));
        homePage.isExpectedPage();
        homePage.validateStructure();
        // homePage.validateSuccessfulLogin(user.getFirstName(), user.getLastName());

    }

    // registration process validation
    @NeodymiumTest
    @DataSet(1)
    public void testRegisterProjectValidation()
    {
        HomePage homePage = loginPage.sendLoginForm(user);

        homePage.waitForPageLoad();

        homePage.isExpectedPage();
        // homePage.validateSuccessfulLogin(user.getFirstName(), user.getLastName());
        homePage.openRegistrationTab();
        
        homePage.waitForPageLoad();

        homePage.openRegistrationModal();
        homePage.validateRegistrationModal();
        
    }

    @NeodymiumTest
    @DataSet(1)
    public void testRegisterProjectSubmitFiles()
    {
        HomePage homePage = loginPage.sendLoginForm(user);

        homePage.waitForPageLoad();
        
        homePage.isExpectedPage();
        // homePage.validateSuccessfulLogin(user.getFirstName(), user.getLastName());
        homePage.openRegistrationTab();

        homePage.waitForPageLoad();

        homePage.openRegistrationModal();
        homePage.fileUpload();
        homePage.submitFile();

        homePage.waitForSubmission();
        
        homePage.validateSubmissionAfterUpload();


    }


    @NeodymiumTest
    @DataSet(2)
    public void testDeclineProjectAdminPortal()
    {
        //HomePage homePage = new HomePage();
        open("https://attui-dev.mooo.com/index.php");
        AdminPortalLoginPage adminPortalLoginPage = new AdminPortalLoginPage();                
        AdminPortalPage adminPortalPage = adminPortalLoginPage.sendLoginForm(user.getEmail(), user.getPassword());
        adminPortalPage.isExpectedPage();
        adminPortalPage.validateStructure();
        adminPortalPage.declineProject();

    }

    @NeodymiumTest
    @DataSet(1)
    public void testApproveProjectAdminPortal()
    {
        HomePage homePage = loginPage.sendLoginForm(user);

        homePage.waitForPageLoad();
        
        homePage.isExpectedPage();
        // homePage.validateSuccessfulLogin(user.getFirstName(), user.getLastName());
        homePage.openRegistrationTab();

        homePage.waitForPageLoad();

        homePage.openRegistrationModal();
        homePage.fileUpload();
        homePage.submitFile();

        homePage.waitForSubmission();
        
        homePage.validateSubmissionAfterUpload();

        open("https://attui-dev.mooo.com/index.php");
        AdminPortalLoginPage adminPortalLoginPage = new AdminPortalLoginPage();                
        AdminPortalPage adminPortalPage = adminPortalLoginPage.sendLoginForm(user.getEmailAdmin(), user.getPasswordAdmin());
        adminPortalPage.isExpectedPage();
        adminPortalPage.validateStructure();
        adminPortalPage.approveProject();

    }

    @NeodymiumTest
    @DataSet(1)
    public void testConfirmDetailsAdminPortal()
    {
        open("https://attui-dev.mooo.com/index.php");
        AdminPortalLoginPage adminPortalLoginPage = new AdminPortalLoginPage();                
        AdminPortalPage adminPortalPage = adminPortalLoginPage.sendLoginForm(user.getEmailAdmin(), user.getPasswordAdmin());
        adminPortalPage.isExpectedPage();
        adminPortalPage.validateStructure();
        adminPortalPage.confirmDetails();        

    }
        
    @NeodymiumTest
    @DataSet(1)
    public void testValidateDetailsStudentPortal()
    {
        HomePage homePage = loginPage.sendLoginForm(user);
        homePage.waitForPageLoad();        
        homePage.isExpectedPage();
        // homePage.validateSuccessfulLogin(user.getFirstName(), user.getLastName());
        homePage.openRegistrationTab();

        homePage.waitForPageLoad();

        homePage.validateProjectDetails();

    }

    @NeodymiumTest
    @DataSet(1)
    public void testCleanUp()
    {
        open("https://attui-dev.mooo.com/index.php");
        AdminPortalLoginPage adminPortalLoginPage = new AdminPortalLoginPage();                
        AdminPortalPage adminPortalPage = adminPortalLoginPage.sendLoginForm(user.getEmailAdmin(), user.getPasswordAdmin());
        adminPortalPage.isExpectedPage();
        adminPortalPage.validateStructure();
        adminPortalPage.cancelProject();    

    }


}
