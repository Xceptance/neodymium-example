/**
 * 
 */
package posters.pageObjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import posters.dataObjects.User;
import posters.pageObjects.pages.browsing.AbstractBrowsingPage;
import posters.pageObjects.pages.browsing.HomePage;

/**
 * @author pfotenhauer
 */
public class LoginPage extends AbstractBrowsingPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public void isExpectedPage()
    {
        $("#formLogin").should(exist);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#validateStructure()
     */
    @Override
    @Then("^I want to be on the login page$")
    public void validateStructure()
    {
        super.validateStructure();

        // Login headline
        // Make sure the Headline is there and starts with a capital letter followed by at least 3 more symbols.
        $("#formLogin h2").should(matchText("[A-Z].{3,}"));
        // Email field
        // Asserts the Email field has a label displaying the value.
        $("#formLogin label.control-label[for=email]").shouldHave(exactText("Your email*"));
        // Asserts the email field is present.
        $("#email").shouldBe(visible);
        // Password field
        // Verifies the password field has a label displaying the value.
        $("#formLogin label.control-label[for=password]").shouldHave(exactText("Your password*"));
        // Asserts the password field is there.
        $("#password").shouldBe(visible);
        // Login button
        // asserts the login button displays the value.
        $("#btnSignIn").shouldHave(exactText("Sign in"));
        // Register headline
        // Asserts the Headline for the Registration is there.
        $("#main .h3").shouldHave(exactText("New customer"));
        // Registration page link
        // Asserts the Register link is there and shows the correct text.
        $("#linkRegister").shouldHave(exactText("Create new Account"));
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
        return page(HomePage.class);
    }

    @When("^I fill the register form with \"([^\"]*)\" and \"([^\"]*)\" and send it$")
    public void sendFormWithData(String email, String password)
    {
        // Input email
        // Fill the email field with the parameter.
        $("#email").val(email);
        // Input password
        // Fill the password field with the parameter.
        $("#password").val(password);
        // Log in and open the homepage
        // Click on the Sign In button.
        $("#btnSignIn").scrollTo().click();
    }

    /**
     * @return
     */
    @When("^I click the register button$")
    public RegisterPage openRegister()
    {
        $("#linkRegister").scrollTo().click();
        return page(RegisterPage.class);
    }

    /**
     * 
     */
    @Then("^I want to be registered successfully$")
    public void validateSuccessfullRegistration()
    {
        successMessage().validateSuccessMessage("Your account has been created. Log in with your email address and password.");
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
        return page(LoginPage.class);
    }

    /**
     * @param eMail
     */
    public void validateWrongEmail(String eMail)
    {
        errorMessage().validateErrorMessage("The email address you entered doesn't exist. Please try again.");
        $("#email").shouldHave(exactValue(eMail));
    }

    /**
     * @param eMail
     */
    public void validateWrongPassword(String eMail)
    {
        errorMessage().validateErrorMessage("The password you entered is incorrect. Please try again.");
        $("#email").shouldHave(exactValue(eMail));
    }

}
