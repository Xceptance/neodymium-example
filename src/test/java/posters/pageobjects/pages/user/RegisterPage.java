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

    private SelenideElement registerForm = $("#formRegister");

    private SelenideElement firstNameField = $("#firstName");

    private SelenideElement lastNameField = $("#lastName");

    private SelenideElement emailField = $("#eMail");

    private SelenideElement passwordField = $("#password");

    private SelenideElement passwordRepeatField = $("#passwordAgain");

    private SelenideElement registerButton = $("#btnRegister");

    @Override
    @Step("ensure this is a register page")
    public RegisterPage isExpectedPage()
    {
        super.isExpectedPage();
        registerForm.should(exist);
        return this;
    }

    /// ----- validate content register page ----- ///
    
    private void validateFillInHeadlines(String headline)
    {
        $$("#formRegister .form-group label").findBy(exactText(headline)).shouldBe(visible);
    }
    
    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("RegisterPage.headlines.firstName"));
        validateFillInHeadlines(Neodymium.localizedText("RegisterPage.headlines.lastName"));
        validateFillInHeadlines(Neodymium.localizedText("RegisterPage.headlines.email"));
        validateFillInHeadlines(Neodymium.localizedText("RegisterPage.headlines.password"));
        validateFillInHeadlines(Neodymium.localizedText("RegisterPage.headlines.passwordRepeat"));
    }
    
    @Step("validate fill in form placeholder")
    public void validateFillInPlaceholder() 
    {
        firstNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("RegisterPage.placeholder.firstName")))).shouldBe(visible);
        lastNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("RegisterPage.placeholder.lastName")))).shouldBe(visible);
        emailField.shouldHave(attribute("placeholder", (Neodymium.localizedText("RegisterPage.placeholder.email")))).shouldBe(visible);
        passwordField.shouldHave(attribute("placeholder", (Neodymium.localizedText("RegisterPage.placeholder.password")))).shouldBe(visible);
        passwordRepeatField.shouldHave(attribute("placeholder", (Neodymium.localizedText("RegisterPage.placeholder.passwordRepeat")))).shouldBe(visible);
    }
    
    @Step("validate required string")
    public void validateRequiredString() 
    {
        $(".reqField").shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.requiredFields"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate register page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        registerForm.find("legend").shouldHave(exactText(Neodymium.localizedText("RegisterPage.title"))).shouldBe(visible);
        
        // validate fill in headlines
        validateFillInHeadlines();
        
        // validate fill in placeholder
        validateFillInPlaceholder();
        
        // validate "required fields" string
        validateRequiredString();
      
        // validate sign in button
        registerButton.shouldHave(exactText(Neodymium.localizedText("RegisterPage.button")));
    }

    /// ----- register page navigation ----- ///
    
    @Step("fill and send register form with '{user}'")
    public LoginPage sendRegisterForm(User user)
    {
        // fill out the registration form
        lastNameField.val(user.getLastName());
        firstNameField.val(user.getFirstName());
        emailField.val(user.getEmail());
        passwordField.val(user.getPassword());
        passwordRepeatField.val(user.getPassword());

        // click on the Register Button
        registerButton.scrollTo().click();

        return new LoginPage().isExpectedPage();
    }
}
