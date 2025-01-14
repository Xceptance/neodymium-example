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

public class ChangeNameOrEmailPage extends AbstractBrowsingPage<ChangeNameOrEmailPage>
{

    private SelenideElement changeNameOrEmailForm = $("#form-change-name-email");

    private SelenideElement lastNameField = $("#last-name");

    private SelenideElement firstNameField = $("#first-name");

    private SelenideElement emailField = $("#e-mail");

    private SelenideElement passwordField = $("#password");

    private SelenideElement updateNameOrEmailButton = $("#btn-change-name-email");

    @Override
    @Step("ensure this is a change name or email page")
    public ChangeNameOrEmailPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is a change name or email page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(changeNameOrEmailForm, exist);
        return changeNameOrEmailForm.exists();
    }

    /// ========== validate content change name or email page ========== ///

    private void validateFillInHeadlines(String headline)
    {
        $$(".form-group label").findBy(exactText(headline)).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.lastName"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.firstName"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.email"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.password"));
    }

    @Step("validate fill in form placeholder")
    public void validateFillInPlaceholder()
    {
        lastNameField.shouldHave(attribute("placeholder", Neodymium.localizedText("fillIn.placeholder.lastName"))).shouldBe(visible);
        firstNameField.shouldHave(attribute("placeholder", Neodymium.localizedText("fillIn.placeholder.firstName"))).shouldBe(visible);
        emailField.shouldHave(attribute("placeholder", Neodymium.localizedText("fillIn.placeholder.email"))).shouldBe(visible);
        passwordField.shouldHave(attribute("placeholder", Neodymium.localizedText("fillIn.placeholder.password"))).shouldBe(visible);
    }

    @Override
    @Step("validate change name or email page structure")
    public ChangeNameOrEmailPage validateStructure()
    {
        super.validateStructure();

        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("changeNameOrEmailPage.title"))).shouldBe(visible);

        // validate fill in headlines
        validateFillInHeadlines();

        // validate fill in placeholder
        validateFillInPlaceholder();

        // validate "required fields" string
        $(".reqField").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);

        // validate update account button
        updateNameOrEmailButton.shouldHave(exactText(Neodymium.localizedText("button.updateAccount"))).shouldBe(visible);

        return this;
    }

    @Step("validate user information")
    public void validateUserInformation(User user)
    {
        lastNameField.shouldHave(attribute("value", user.getLastName())).shouldBe(visible);
        firstNameField.shouldHave(attribute("value", user.getFirstName())).shouldBe(visible);
        emailField.shouldHave(attribute("value", user.getEmail())).shouldBe(visible);
    }

    /// ========== change name or email page navigation ========== ///

    @Step("change name or email")
    public PersonalDataPage changeNameOrPassword(User user)
    {
        // fill out the change name or email form
        lastNameField.val(user.getLastName());
        firstNameField.val(user.getFirstName());
        emailField.val(user.getEmail());
        passwordField.val(user.getPassword());

        // click on the update account button
        updateNameOrEmailButton.click(ClickOptions.usingJavaScript());

        return new PersonalDataPage().assertExpectedPage();
    }
}
