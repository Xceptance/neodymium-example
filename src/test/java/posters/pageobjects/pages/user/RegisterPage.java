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
    
    @Step("validate fill in form headlines")
    public void validateFillInHeadlines()
    {
        $$("#formRegister .form-group label").findBy(exactText(Neodymium.localizedText("RegisterPage.headlines.firstName"))).shouldBe(visible);
        $$("#formRegister .form-group label").findBy(exactText(Neodymium.localizedText("RegisterPage.headlines.lastName"))).shouldBe(visible);
        $$("#formRegister .form-group label").findBy(exactText(Neodymium.localizedText("RegisterPage.headlines.email"))).shouldBe(visible);
        $$("#formRegister .form-group label").findBy(exactText(Neodymium.localizedText("RegisterPage.headlines.password"))).shouldBe(visible);
        $$("#formRegister .form-group label").findBy(exactText(Neodymium.localizedText("RegisterPage.headlines.passwordRepeat"))).shouldBe(visible);
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
    
    @Step("fill and send register form")
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
