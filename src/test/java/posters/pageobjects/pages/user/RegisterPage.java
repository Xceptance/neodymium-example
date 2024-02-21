package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.User;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class RegisterPage extends AbstractBrowsingPage
{
    private SelenideElement registerForm = $("#form-register");

    private SelenideElement firstNameField = $("#registration-form-first-name");

    private SelenideElement lastNameField = $("#registration-form-last-name");

    private SelenideElement emailField = $("#registration-form-e-mail");

    private SelenideElement passwordField = $("#registration-form-password");

    private SelenideElement passwordRepeatField = $("#registration-form-password-repeat");

    private SelenideElement registerButton = $("#btn-register");

    @Override
    @Step("ensure this is a register page")
    public RegisterPage isExpectedPage()
    {
        super.isExpectedPage();
        registerForm.should(exist);
        return this;
    }

    /// ========== validate content register page ========== ///
    
    private void validateFillInHeadlines(String headline)
    {
        $$(".form-label").findBy(exactText(headline)).shouldBe(visible);
    }
    
    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.firstName"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.lastName"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.email"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.password"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.confirmPassword"));
    }
    
    @Step("validate fill in form placeholder")
    public void validateFillInPlaceholder() 
    {
        firstNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.firstName")))).shouldBe(visible);
        lastNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.lastName")))).shouldBe(visible);
        emailField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.email")))).shouldBe(visible);
        passwordField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.password")))).shouldBe(visible);
        passwordRepeatField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.confirmPassword")))).shouldBe(visible);
    }

    @Override
    @Step("validate register page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        registerForm.find("legend").shouldHave(exactText(Neodymium.localizedText("registerPage.title"))).shouldBe(visible);
        
        // validate fill in headlines
        validateFillInHeadlines();
        
        // validate fill in placeholder
        validateFillInPlaceholder();
        
        // validate "required fields" string
        $("#req-field").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);
      
        // validate sign in button
        registerButton.shouldHave(exactText(Neodymium.localizedText("button.createAccount")));
    }

    /// ========== register page navigation ========== ///
    
    @Step("fill and send register form with '{user}'")
    public LoginPage sendRegisterForm(User user)
    {
        // fill out the registration form
        lastNameField.val(user.getLastName());
        firstNameField.val(user.getFirstName());
        emailField.val(user.getEmail());
        passwordField.val(user.getPassword());
        passwordRepeatField.val(user.getPassword());

        // click on the register button
        registerButton.click();

        return new LoginPage().isExpectedPage();
    }
}
