package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.junit.Assert;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.User;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;

public class LoginPage extends AbstractBrowsingPage
{
    private SelenideElement loginForm = $("#formLogin");

    private SelenideElement emailField = $("#email");

    private SelenideElement passwordField = $("#password");

    private SelenideElement signInButton = $("#btnSignIn");

    private SelenideElement registerLink = $("#linkRegister");

    @Override
    @Step("ensure this is a login page")
    public LoginPage isExpectedPage()
    {
        super.isExpectedPage();
        loginForm.should(exist);
        return this;
    }

    /// ========== validate content login page ========== ///
    
    @Override
    @Step("validate login page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        loginForm.find("legend").shouldHave(exactText(Neodymium.localizedText("loginPage.title"))).shouldBe(visible);
        
        // validate fill in headlines
        $$("#formLogin .form-group label").findBy(exactText(Neodymium.localizedText("fillIn.inputDescription.yourEmail"))).shouldBe(visible);
        $$("#formLogin .form-group label").findBy(exactText(Neodymium.localizedText("fillIn.inputDescription.yourPassword"))).shouldBe(visible);
        
        // validate fill in placeholder
        emailField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.email")))).shouldBe(visible);
        passwordField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.password")))).shouldBe(visible);
        
        // validate "required fields" string
        $(".me-auto").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);
      
        // validate sign in button
        signInButton.shouldHave(exactText(Neodymium.localizedText("button.signIn")));
        
        // validate new account creation
        $(".header-container").shouldHave(exactText(Neodymium.localizedText("loginPage.newCustomer")));
        registerLink.shouldHave(exactText(Neodymium.localizedText("loginPage.createNewAccount")));
    }
    
    @Step("validate successful registration message")
    public void validateSuccessfulRegistration()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulRegistration"));
    }

    @Step("validate invalid email for login error message")
    public void validateWrongEmail(String email)
    {
        errorMessage.validateErrorMessage(Neodymium.localizedText("errorMessage.emailDoesNotExist"));
        Assert.assertEquals(emailField.val(), email);
    }
    
    @Step("validate invalid password for login error message")
    public void validateWrongPassword(String email)
    {
        errorMessage.validateErrorMessage(Neodymium.localizedText("errorMessage.incorrectPassword"));
        Assert.assertEquals(emailField.val(), email);
    }
    
    /// ========== login page navigation ========== ///
    
    @Step("open register page from login page")
    public RegisterPage openRegister()
    {
        registerLink.click();
        return new RegisterPage().isExpectedPage();
    }
    
    @Step("open homepage from login page")
    public HomePage openHomePage()
    {
        $("#header-brand").click();
        return new HomePage().isExpectedPage();
    }
    
    @Step("send login form")
    public void sendFormWithData(String email, String password)
    {
        // fill out the login form
        emailField.val(email);
        passwordField.val(password);

        // click on the Sign In button.
        signInButton.click();
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
