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
import com.xceptance.neodymium.util.Context;

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
    public void isExpectedPage()
    {
        loginForm.should(exist);
    }

    @Override
    public void validateStructure()
    {
        super.validateStructure();

        // Login headline
        // Make sure the Headline is there and starts with a capital letter followed by at least 3 more symbols.
        loginForm.find("h2").should(matchText("[A-Z].{3,}"));
        // Email field
        // Asserts the Email field has a label displaying the value.
        loginForm.find("label.control-label[for=email]").shouldHave(exactText(Context.localizedText("AccountPages.yourEmail")));
        // Asserts the email field is present.
        emailField.shouldBe(visible);
        // Password field
        // Verifies the password field has a label displaying the value.
        loginForm.find("label.control-label[for=password]").shouldHave(exactText(Context.localizedText("AccountPages.yourPassword")));
        // Asserts the password field is there.
        passwordField.shouldBe(visible);
        // Login button
        // asserts the login button displays the value.
        signInButton.shouldHave(exactText(Context.localizedText("AccountPages.signIn")));
        // Register headline
        // Asserts the Headline for the Registration is there.
        $("#main .h3").shouldHave(exactText(Context.localizedText("AccountPages.newCustomer")));
        // Registration page link
        // Asserts the Register link is there and shows the correct text.
        registerLink.shouldHave(exactText(Context.localizedText("AccountPages.createNewAccount")));
    }

    /**
     * @param email
     *            The email of the account you want to log into
     * @param password
     *            The password of the account you want to log into
     */
    public HomePage sendLoginform(String email, String password)
    {
        sendFormWithData(email, password);
        return new HomePage();
    }

    public void sendFormWithData(String email, String password)
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
    }

    /**
     * @return
     */
    public RegisterPage openRegister()
    {
        registerLink.scrollTo().click();
        return new RegisterPage();
    }

    /**
     * 
     */
    public void validateSuccessfullRegistration()
    {
        successMessage.validateSuccessMessage(Context.localizedText("AccountPages.validation.successfulAccountCreation"));
    }

    /**
     * @param user
     * @return
     */
    public HomePage sendLoginform(User user)
    {
        return sendLoginform(user.getEMail(), user.getPassword());
    }

    /**
     * @param user
     */
    public LoginPage sendFalseLoginform(User user)
    {
        sendFormWithData(user.getEMail(), user.getPassword());
        return new LoginPage();
    }

    /**
     * @param eMail
     */
    public void validateWrongEmail(String eMail)
    {
        errorMessage.validateErrorMessage(Context.localizedText("AccountPages.validation.emailDoesNotExistError"));
        emailField.shouldHave(exactValue(eMail));
    }

    /**
     * @param eMail
     */
    public void validateWrongPassword(String eMail)
    {
        errorMessage.validateErrorMessage(Context.localizedText("AccountPages.validation.incorrectPasswordError"));
        emailField.shouldHave(exactValue(eMail));
    }
}
