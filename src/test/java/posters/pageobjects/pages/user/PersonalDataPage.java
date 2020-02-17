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
public class PersonalDataPage extends AbstractBrowsingPage
{
    private SelenideElement headline = $("#titlePersonalData");

    private SelenideElement deleteButton = $("#btnDeleteAccount");

    @Override
    @Step("ensure this is a personal data page")
    public PersonalDataPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Override
    @Step("validate personal data page structure")
    public PersonalDataPage validateStructure()
    {
        super.validateStructure();

        // Headline
        // Makes sure the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // Data
        // Makes sure the form with your user Data is there
        $("#customerName").shouldBe(visible);
        $("#customerEmail").shouldBe(visible);
        // Delete Account Button
        // Make sure the button to delete your account is there
        deleteButton.shouldBe(visible);
        return this;
    }

    /**
     * @return
     */
    @Step("open delete account page from personal data page")
    public DeleteAccountPage openDeleteAccount()
    {
        // Open the delete account page
        // Clicks the button to get to the Delete Account page
        deleteButton.scrollTo().click();
        DeleteAccountPage deleteAccountPage = new DeleteAccountPage();
        deleteAccountPage.isExpectedPage();
        return deleteAccountPage;
    }

    @Step("validate user data matches {user}")
    public PersonalDataPage validateUserData(User user)
    {
        $("#customerFirstName").shouldHave(exactText(user.getFirstName()));
        $("#customerLastName").shouldHave(exactText(user.getLastName()));
        $("#customerEmail").shouldHave(exactText(user.getEmail()));
        return this;
    }

    @Step("change user name and email to {user}")
    public PersonalDataPage changeNameOrEmail(User user)
    {
        $("#btnChangeNameEmail").click();
        $("#lastName").setValue(user.getLastName());
        $("#firstName").setValue(user.getFirstName());
        $("#eMail").setValue(user.getEmail());
        $("#password").setValue(user.getPassword());
        $("#btnChangeNameEmail").click();
        return this;
    }

    @Step("validate error message tells such account already exists")
    public PersonalDataPage validateErrorMessageAccountExists()
    {
        this.errorMessage.validateErrorMessage(Neodymium.localizedText("AccountPages.validation.accountExistsError"));
        return this;
    }

    @Step("change password from {oldPassword} to {newPassword}")
    public PersonalDataPage changePassword(String newPassword, String oldPassword)
    {
        $("#btnChangePassword").click();
        $("#oldPassword").setValue(oldPassword);
        $("#password").setValue(newPassword);
        $("#passwordAgain").setValue(newPassword);
        $("#btnChangePassword").click();
        return this;
    }
}
