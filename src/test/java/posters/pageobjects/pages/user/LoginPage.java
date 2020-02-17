/**
 * 
 */
package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.User;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;

/**
 * @author pfotenhauer
 */
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
        loginForm.should(exist);
        return this;
    }

    @Override
    @Step("validate login page structure")
    public LoginPage validateStructure()
    {
        super.validateStructure();

        // Login headline
        // Make sure the Headline is there and starts with a capital letter followed by at least 3 more symbols.
        loginForm.find("h2").should(matchText("[A-Z].{3,}"));
        // Email field
        // Asserts the Email field has a label displaying the value.
        loginForm.find("label.control-label[for=email]").shouldHave(exactText(Neodymium.localizedText("AccountPages.yourEmail")));
        // Asserts the email field is present.
        emailField.shouldBe(visible);
        // Password field
        // Verifies the password field has a label displaying the value.
        loginForm.find("label.control-label[for=password]").shouldHave(exactText(Neodymium.localizedText("AccountPages.yourPassword")));
        // Asserts the password field is there.
        passwordField.shouldBe(visible);
        // Login button
        // asserts the login button displays the value.
        signInButton.shouldHave(exactText(Neodymium.localizedText("AccountPages.signIn")));
        // Register headline
        // Asserts the Headline for the Registration is there.
        $("#main .h3").shouldHave(exactText(Neodymium.localizedText("AccountPages.newCustomer")));
        // Registration page link
        // Asserts the Register link is there and shows the correct text.
        registerLink.shouldHave(exactText(Neodymium.localizedText("AccountPages.createNewAccount")));
        return this;
    }

    @Step("send login form")
    public LoginPage sendFormWithData(String email, String password)
    {
        // Input email
        // Fill the email field with the parameter.
        emailField.val(email);
        // Input password
        // Fill the password field with the parameter.
        passwordField.val(password);
        // Log in and open the homepage
        // Click on the Sign In button.
        signInButton.scrollTo().click();
        return this;
    }

    /**
     * @param email
     *            The email of the account you want to log into
     * @param password
     *            The password of the account you want to log into
     */
    @Step("send login form with valid data")
    public HomePage sendLoginform(String email, String password)
    {
        sendFormWithData(email, password);
        HomePage homePage = new HomePage();
        homePage.isExpectedPage();
        return homePage;
    }

    /**
     * @param user
     * @return
     */
    @Step("send login form with valid user data")
    public HomePage sendLoginform(User user)
    {
        return sendLoginform(user.getEmail(), user.getPassword());
    }

    /**
     * @param user
     */
    @Step("send login form with erroneous user data")
    public LoginPage sendFalseLoginform(User user)
    {
        sendFormWithData(user.getEmail(), user.getPassword());
        return this;
    }

    /**
     * @return
     */
    @Step("open register page from login page")
    public RegisterPage openRegister()
    {
        registerLink.scrollTo().click();
        RegisterPage registerPage = new RegisterPage();
        registerPage.isExpectedPage();
        return registerPage;
    }

    @Step("validate successful registration message")
    public LoginPage validateSuccessfulRegistration()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("AccountPages.validation.successfulAccountCreation"));
        return this;
    }

    /**
     * @param eMail
     */
    @Step("validate invalid email for login error message")
    public LoginPage validateWrongEmail(String eMail)
    {
        errorMessage.validateErrorMessage(Neodymium.localizedText("AccountPages.validation.emailDoesNotExistError"));
        emailField.shouldHave(exactValue(eMail));
        return this;
    }

    /**
     * @param eMail
     */
    @Step("validate invalid password for login error message")
    public LoginPage validateWrongPassword(String eMail)
    {
        errorMessage.validateErrorMessage(Neodymium.localizedText("AccountPages.validation.incorrectPasswordError"));
        emailField.shouldHave(exactValue(eMail));
        return this;
    }
}
