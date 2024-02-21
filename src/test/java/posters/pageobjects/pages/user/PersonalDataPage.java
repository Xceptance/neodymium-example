package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.tests.testdata.dataobjects.User;

public class PersonalDataPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titlePersonalData");

    private SelenideElement changeNameOrEmailButton = $("#btnChangeNameEmail");
    
    private SelenideElement changePasswordButton = $("#btnChangePassword");
    
    private SelenideElement deleteButton = $("#btnDeleteAccount");

    @Override
    @Step("ensure this is a personal data page")
    public PersonalDataPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ========== validate content personal data page ========== ///
    
    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("personalDataPage.title"))).shouldBe(visible);
        
        // validate buttons
        changeNameOrEmailButton.shouldHave(exactText(Neodymium.localizedText("button.changeNameOrMail"))).shouldBe(visible);
        changePasswordButton.shouldHave(exactText(Neodymium.localizedText("button.changePassword"))).shouldBe(visible);
        deleteButton.shouldHave(exactText(Neodymium.localizedText("button.deleteAccount"))).shouldBe(visible);
    }
    
    @Step("validate personal data of '{user}")
    public void validatePersonalData(User user) 
    {
        // validate name
        String fullName = user.getFirstName() + " " + user.getLastName();
        $$(".form-group strong").findBy(exactText(Neodymium.localizedText("personalDataPage.name"))).shouldBe(visible);
        $("#customerName").shouldHave(exactText(fullName)).shouldBe(visible);
        
        // validate email
        $$(".form-group strong").findBy(exactText(Neodymium.localizedText("personalDataPage.email"))).shouldBe(visible);
        $("#customerEmail").shouldHave(exactText(user.getEmail())).shouldBe(visible);
    }
    
    @Step("validate successful update")
    public void validateSuccessfulSave()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulUpdate"));
    }
    
    /// ========== personal data page navigation ========== ///
    
    @Step("open delete account page from personal data page")
    public DeleteAccountPage openDeleteAccountPage()
    {
        deleteButton.click();
        return new DeleteAccountPage().isExpectedPage();
    }
    
    @Step("open delete account page from personal data page")
    public ChangeNameOrEmailPage openChangeNameOrEmailPage()
    {
        changeNameOrEmailButton.click();
        return new ChangeNameOrEmailPage().isExpectedPage();
    }
    
    @Step("open delete account page from personal data page")
    public ChangePasswordPage openChangePasswordPage()
    {
        changePasswordButton.click();
        return new ChangePasswordPage().isExpectedPage();
    }
}
