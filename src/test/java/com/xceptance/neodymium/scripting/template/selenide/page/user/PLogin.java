/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

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
        // Input email
        // Fill the email field with the parameter.
        $("#email").val(email);
        // Input password
        // Fill the password field with the parameter.
        $("#password").val(password);
        // Log in and open the homepage
        // Click on the Sign In button.
        $("#btnSignIn").click();

        return page(PHome.class);
    }

}
