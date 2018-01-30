/**
 * 
 */
package posters.pageObjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import posters.pageObjects.pages.browsing.AbstractBrowsingPage;
import posters.pageObjects.pages.browsing.HomePage;

/**
 * @author pfotenhauer
 */
public class DeleteAccountPage extends AbstractBrowsingPage
{
    @Override
    public void isExpectedPage()
    {
        $("#formDeleteAccount").should(exist);
    }

    @Override
    public void validateStructure()
    {
        super.validateStructure();

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

        return new HomePage();
    }
}
