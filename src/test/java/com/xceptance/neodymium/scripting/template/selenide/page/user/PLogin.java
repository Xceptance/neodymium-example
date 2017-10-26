/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.objects.User;
import com.xceptance.neodymium.scripting.template.selenide.page.BasicPage;
import com.xceptance.neodymium.scripting.template.selenide.page.PHome;

/**
 * @author pfotenhauer
 */
public class PLogin extends BasicPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public boolean isExpectedPage()
    {
        return $("#formLogin").exists();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#validateStructure()
     */
    @Override
    public void validateStructure()
    {
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
    public PHome sendLoginform(String email, String password)
    {
        sendFormWithData(email, password);
        return page(PHome.class);
    }

    private void sendFormWithData(String email, String password)
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
    public PRegister openRegister()
    {
        $("#linkRegister").scrollTo().click();
        return page(PRegister.class);
    }

    /**
     * 
     */
    public void validateSuccessfulLRegistration()
    {
        // Wait until javascript makes the success message visible
        // Waits until javascript makes the success message visible.
        $("#successMessage").shouldBe(visible);
        // The message displays the correct text.
        $("#successMessage").shouldHave(exactText("× Your account has been created. Log in with your email address and password."));
    }

    /**
     * @param user
     * @return
     */
    public PHome sendLoginform(User user)
    {
        return sendLoginform(user.getEMail(), user.getPassword());
    }

    /**
     * @param eMail
     */
    public void validateWrongEmail(String eMail)
    {
        // Wait until javascript makes the error message visible
        // Waits until javascript makes the error message visible.
        $("#errorMessage").shouldBe(visible);
        // Makes sure the correct text is displayed.
        $("#errorMessage").shouldHave(exactText("× The email address you entered doesn't exist. Please try again."));
        // Verify that the email address is still there
        // Asserts the email field contains the parameter.
        $("#email").shouldHave(exactValue(eMail));
    }

    /**
     * @param user
     */
    public PLogin sendFalseLoginform(User user)
    {
        sendFormWithData(user.getEMail(), user.getPassword());
        return page(PLogin.class);
    }
}
