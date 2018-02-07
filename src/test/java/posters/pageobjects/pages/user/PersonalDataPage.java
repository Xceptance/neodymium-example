/**
 * 
 */
package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
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
    public void isExpectedPage()
    {
        headline.should(exist);
    }

    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
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
        return new DeleteAccountPage();
    }
}
