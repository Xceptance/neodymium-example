package posters.pageobjects.pages.user;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.tests.testdata.dataobjects.User;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ChangePasswordPage extends AbstractBrowsingPage<ChangePasswordPage>
{

    private SelenideElement changePasswordForm = $("#form-change-password");

    private SelenideElement oldPasswordField = $("#old-password");

    private SelenideElement newPasswordField = $("#password");

    private SelenideElement newPasswortAgainField = $("#password-again");

    private SelenideElement updatePasswordButton = $("#btn-change-password");

    @Override
    @Step("ensure this is a change password page")
    public ChangePasswordPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is a change password page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(changePasswordForm, exist);
        return changePasswordForm.exists();
    }

    /// ========== validate change password page ========== ///

    private void validateFillInHeadlines(String headline)
    {
        $$(".form-group label").findBy(exactText(headline)).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.oldPassword"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.newPassword"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.repeatNewPassword"));
    }

    @Step("validate fill in form placeholder")
    public void validateFillInPlaceholder()
    {
        // validate placeholder
        oldPasswordField.shouldHave(attribute("placeholder", Neodymium.localizedText("fillIn.placeholder.oldPassword"))).shouldBe(visible);
        newPasswordField.shouldHave(attribute("placeholder", Neodymium.localizedText("fillIn.placeholder.newPassword"))).shouldBe(visible);
        newPasswortAgainField.shouldHave(attribute("placeholder", Neodymium.localizedText("fillIn.placeholder.repeatNewPassword"))).shouldBe(visible);
    }

    @Override
    @Step("validate change password page structure")
    public ChangePasswordPage validateStructure()
    {
        super.validateStructure();

        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("changePasswordPage.title"))).shouldBe(visible);

        // validate fill in headlines
        validateFillInHeadlines();

        // validate fill in placeholder
        validateFillInPlaceholder();

        // validate "required fields" string
        $(".reqField").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);

        // validate update account button
        updatePasswordButton.shouldHave(exactText(Neodymium.localizedText("button.submit"))).shouldBe(visible);

        return this;
    }

    /// ========== change password page navigation ========== ///

    @Step("change password")
    public PersonalDataPage changeNameOrPassword(User user, String newPassword)
    {
        // fill out the change password form
        oldPasswordField.val(user.getPassword());
        newPasswordField.val(newPassword);
        newPasswortAgainField.val(newPassword);

        // click on the update account button
        updatePasswordButton.click(ClickOptions.usingJavaScript());

        return new PersonalDataPage().assertExpectedPage();
    }
}
