/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.browsing.AbstractBrowsingPage;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;

/**
 * @author pfotenhauer
 */
public class DeleteAccountPage extends AbstractBrowsingPage
{
    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public boolean isExpectedPage()
    {
        return $("#formDeleteAccount").exists();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#validateStructure()
     */
    @Override
    public void validateStructure()
    {
        // Headline
        // Asserts the headline is there and starts with a capital letter
        $("#formDeleteAccount .h2").should(matchText("[A-Z].{3,}"));
        // Password field
        // Asserts the label belonging to the password field displays the correct text
        $("label[for=\"password\"]").shouldBe(exactText("Your password*"));
        // Asserts the field to enter your password is there
        $("#password").shouldBe(visible);
        // Button
        // Asserts the delete button is there
        $("#btnDeleteAccount").shouldBe(visible);
    }

    /**
     * 
     */
    public HomePage deleteAccount(String password)
    {
        // Password
        // Type the parameter into the password field
        $("#password").setValue(password);
        // Delete account and open the homepage
        // click the confirmation button
        $("#btnDeleteAccount").scrollTo().click();

        return page(HomePage.class);
    }
}
