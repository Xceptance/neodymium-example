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
    @Step("ensure this is a delete account page")
    public void isExpectedPage()
    {
        deleteForm.should(exist);
    }

    @Override
    @Step("validate delete account page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Asserts the headline is there and starts with a capital letter
        deleteForm.find(".h2").should(matchText("[A-Z].{3,}"));
        // Password field
        // Asserts the label belonging to the password field displays the correct text
        $("label[for=\"password\"]").shouldBe(exactText(Neodymium.localizedText("AccountPages.yourPassword")));
        // Asserts the field to enter your password is there
        passwordField.shouldBe(visible);
        // Button
        // Asserts the delete button is there
        deleteButton.shouldBe(visible);
    }

    @Step("delete account")
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
