package posters.pageobjects.pages.user;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.tests.testdata.dataobjects.User;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PersonalDataPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#title-personal-data");

    private SelenideElement changeNameOrEmailButton = $("#btn-change-name-email");

    private SelenideElement changePasswordButton = $("#btn-change-password");

    private SelenideElement deleteButton = $("#btn-delete-account");

    @Override
    @Step("ensure this is a personal data page")
    public PersonalDataPage assertExpectedPage()
    {
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
        $("#customer-name").shouldHave(exactText(fullName)).shouldBe(visible);

        // validate email
        $$(".form-group strong").findBy(exactText(Neodymium.localizedText("personalDataPage.email"))).shouldBe(visible);
        $("#customer-email").shouldHave(exactText(user.getEmail())).shouldBe(visible);
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
        deleteButton.click(ClickOptions.usingJavaScript());
        return new DeleteAccountPage().assertExpectedPage();
    }

    @Step("open delete account page from personal data page")
    public ChangeNameOrEmailPage openChangeNameOrEmailPage()
    {
        changeNameOrEmailButton.click(ClickOptions.usingJavaScript());
        return new ChangeNameOrEmailPage().assertExpectedPage();
    }

    @Step("open delete account page from personal data page")
    public ChangePasswordPage openChangePasswordPage()
    {
        changePasswordButton.click(ClickOptions.usingJavaScript());
        return new ChangePasswordPage().assertExpectedPage();
    }
}
