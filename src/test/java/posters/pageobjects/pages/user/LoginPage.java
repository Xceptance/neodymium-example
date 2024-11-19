package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.name;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.junit.Assert;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.User;
import posters.pageobjects.components.LoginHeader;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;

public class LoginPage extends AbstractBrowsingPage
{
    public LoginHeader loginHeader = new LoginHeader();

    private SelenideElement loginForm = $("#loginWin .uk-card");

    private SelenideElement emailField = $("#login_email");

    private SelenideElement passwordField = $("#login_pwd");

    private SelenideElement language = $(".uk-margin [uk-icon=\"icon: world\"]");

    private SelenideElement signInButton = $("#loginForm button");

    private SelenideElement registerButton = $("#loginWin .uk-width-1-1 > button");
    private SelenideElement registerLabLink = $("#uk-tab-66-tab-0");
    private SelenideElement registerProjectLink = $("#uk-tab-66-tab-1");
    
    @Override
    @Step("ensure this is a login page")
    public LoginPage isExpectedPage()
    {
        super.isExpectedPage();
        loginForm.should(exist);
        $(".uk-section [src=\"imgs/logo.png\"]").should(exist);
        $(".uk-margin > .uk-grid .uk-width-3-4").should(exist);
        return this;
    }

    /// ========== validate content login page ========== ///
    
    @Override
    @Step("validate login page structure")
    public void validateStructure()
    {
        loginHeader.validateStructure();

        // validate title
       $("#uk-tab-40-tabpanel-0 > h4").shouldHave(exactText(Neodymium.localizedText("loginPage.title"))).shouldBe(visible);
        
       //input fields and language
       passwordField.should(exist);
       emailField.should(exist);
       language.should(exist);

        // forgotten password
        $("#loginForm a").shouldHave(exactText(Neodymium.localizedText("loginPage.forgotPassword"))).shouldBe(visible);
        
        // validate sign in button
        signInButton.shouldHave(exactText(Neodymium.localizedText("button.signIn")));
        registerButton.shouldHave(exactText(Neodymium.localizedText("button.createAccount")));

        // validate links
        registerLabLink.should(exist).click();
        registerLabLink.shouldHave(exactText(Neodymium.localizedText("loginPage.registerLabLinkText")));
        registerProjectLink.should(exist).click();
        registerProjectLink.shouldHave(exactText(Neodymium.localizedText("loginPage.registerProjectLinkText")));
    }
    
    @Step("validate successful registration message")
    public void validateSuccessfulRegistration()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulRegistration"));
    }
    
    @Step("validate invalid email or password for login error message")
    public void validateFalseLogin(String email)
    {
        errorMessage.validateErrorMessage(Neodymium.localizedText("errorMessage.errorFalseLogin"));
        Assert.assertEquals(emailField.val(), email);
    }

    @Step("validate missing email or password for login error message")
    public void validateMissingLoginInfo(String email)
    {
        if(email.isEmpty())
        {
            errorMessage.validateErrorMessage(Neodymium.localizedText("errorMessage.errorMissingEmail"));
            Assert.assertEquals(emailField.val(), email);
        }
        else
        {
            errorMessage.validateErrorMessage(Neodymium.localizedText("errorMessage.errorMissingPassword"));
        }
    }
    
    /// ========== login page navigation ========== ///
    
    @Step("open register page from login page")
    public RegisterPage openRegister()
    {
        registerLabLink.click(ClickOptions.usingJavaScript());
        return new RegisterPage().isExpectedPage();
    }
    
    @Step("open homepage from login page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
    
    @Step("send login form")
    public void sendFormWithData(String email, String password)
    {
        // fill out the login form
        emailField.val(email);
        passwordField.val(password);

        // click on the Sign In button.
        signInButton.click(ClickOptions.usingJavaScript());
    }
    
    @Step("fill and send login form with valid user '{user}'")
    public HomePage sendLoginForm(User user)
    {
        sendFormWithData(user.getEmail(), user.getPassword());
        return new HomePage().isExpectedPage();
    }
    
    @Step("fill and send login form with invalid user '{user}'")
    public LoginPage sendFalseLoginForm(User user)
    {
        sendFormWithData(user.getEmail(), user.getPassword());
        return new LoginPage().isExpectedPage();
    }
}
