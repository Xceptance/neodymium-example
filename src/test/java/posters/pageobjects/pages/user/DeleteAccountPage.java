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
import com.xceptance.neodymium.util.Context;

import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;

/**
 * @author pfotenhauer
 */
public class DeleteAccountPage extends AbstractBrowsingPage
{
    private SelenideElement deleteForm = $("#formDeleteAccount");

    private SelenideElement passwordField = $("#password");

    private SelenideElement deleteButton = $("#btnDeleteAccount");

    @Override
    public void isExpectedPage()
    {
        deleteForm.should(exist);
    }

    @Override
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Asserts the headline is there and starts with a capital letter
        deleteForm.find(".h2").should(matchText("[A-Z].{3,}"));
        // Password field
        // Asserts the label belonging to the password field displays the correct text
        $("label[for=\"password\"]").shouldBe(exactText(Context.localizedText("AccountPages.password")));
        // Asserts the field to enter your password is there
        passwordField.shouldBe(visible);
        // Button
        // Asserts the delete button is there
        deleteButton.shouldBe(visible);
    }

    /**
     * 
     */
    public HomePage deleteAccount(String password)
    {
        // Password
        // Type the parameter into the password field
        passwordField.setValue(password);
        // Delete account and open the homepage
        // click the confirmation button
        deleteButton.scrollTo().click();

        return new HomePage();
    }
}
