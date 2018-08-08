/**
 * 
 */
package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.User;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

/**
 * @author pfotenhauer
 */
public class RegisterPage extends AbstractBrowsingPage
{

    private SelenideElement registerForm = $("#formRegister");

    private SelenideElement firstnameField = $("#firstName");

    private SelenideElement lastnameField = $("#lastName");

    private SelenideElement emailField = $("#eMail");

    private SelenideElement passwordField = $("#password");

    private SelenideElement passwordRepeatField = $("#passwordAgain");

    private SelenideElement registerButton = $("#btnRegister");

    @Override
    @Step("ensure this is a register page")
    public void isExpectedPage()
    {
        registerForm.should(exist);
    }

    @Override
    @Step("validate register page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // Login headline
        // Make sure the Headline is there and starts with a capital letter followed by at least 3 more symbols.
        registerForm.find(".h2").should(matchText("[A-Z].{3,}"));
        // Form
        // Asserts the label belonging to the last name field displays the correct text
        $("label[for=\"lastName\"]").shouldHave(exactText(Neodymium.localizedText("AccountPages.lastname")));
        // Make sure the field to type in the last name is visible.
        lastnameField.shouldBe(visible);
        // Asserts the label belonging to the first name field displays the correct text
        $("label[for=\"firstName\"]").shouldHave(exactText(Neodymium.localizedText("AccountPages.firstname")));
        // Make sure the field to type in the first name is visible.
        firstnameField.shouldBe(visible);
        // Asserts the label belonging to the email field displays the correct text
        $("label[for=\"eMail\"]").shouldHave(exactText(Neodymium.localizedText("AccountPages.email")));
        // Make sure the field to type in the e-Mail is visible.
        emailField.shouldBe(visible);
        // Asserts the label belonging to the password field displays the correct text
        $("label[for=\"password\"]").shouldHave(exactText(Neodymium.localizedText("AccountPages.password")));
        // Make sure the field to type in the password is visible.
        passwordField.shouldBe(visible);
        // Asserts the label belonging to the second password field displays the correct text
        $("label[for=\"passwordAgain\"]").shouldHave(exactText(Neodymium.localizedText("AccountPages.passwordRepeat")));
        // Make sure the field to type in the password again is visible.
        passwordRepeatField.shouldBe(visible);
        // Register button
        // Make sure the Registration button displays the correct text.
        registerButton.shouldHave(exactText(Neodymium.localizedText("AccountPages.createAccount")));
    }

    /**
     * @param eMail
     *            The email of the account you want to log into
     * @param password
     *            The password of the account you want to log into
     */
    @Step("fill and send register form")
    public LoginPage sendRegisterForm(String firstName, String lastName, String eMail, String password, String passwordRepeat)
    {
        // Fill out the registration form
        // Type the last name parameter into the last name field.
        lastnameField.val(lastName);
        // Type the first name parameter into the first name field.
        firstnameField.val(firstName);
        // Type the email parameter into the email field.
        emailField.val(eMail);
        // Type the password parameter into the password field.
        passwordField.val(password);
        // Type the second password parameter into the second password field.
        passwordRepeatField.val(passwordRepeat);
        // Register and open the login page if successful
        // Click on the Register Button
        registerButton.scrollTo().click();

        return new LoginPage();
    }

    /**
     * @param user
     * @param passwordRepeat
     * @return
     */
    @Step("send register form for {user}")
    public LoginPage sendRegisterForm(User user, String passwordRepeat)
    {
        return sendRegisterForm(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), passwordRepeat);
    }
}
