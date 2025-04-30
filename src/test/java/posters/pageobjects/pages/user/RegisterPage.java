package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.User;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class RegisterPage extends AbstractBrowsingPage
{
    private SelenideElement registerForm = $("#registerForm");
    
    private SelenideElement salutation = $("#new_reg_gender_H");

    private SelenideElement title = $("#new_reg_wtitle_H");
    
    private SelenideElement firstNameField = $("[name='new_reg_fname']");
    
    private SelenideElement lastNameField = $("[name='new_reg_lname']");

    private SelenideElement degree = $("#studiengang_H");

    private SelenideElement matriculation = $("#registerForm .uk-inline [name='matrikelnummer']");
    
    private SelenideElement emailField = $("[name='new_reg_email']");
    
    private SelenideElement language = $(".uk-inline [name='lang']");

    private SelenideElement registerButton = $(".uk-first-column > button");

    @Override
    @Step("ensure this is a register page")
    public RegisterPage isExpectedPage()
    {
        super.isExpectedPage();
        registerForm.should(exist);
        return this;
    }
    
    @Step("validate unsuccessful registration of user '{firstName}' on home page")
    public void validateRegisterEmailExists()
    {
        errorMessage.validateErrorMessageRegister(Neodymium.localizedText("errorMessage.emailExists"));
    }

    @Step("validate unsuccessful registration of user '{firstName}' on home page")
    public void validateRegisterMissingInfo()
    {
        errorMessage.validateErrorMessageRegister(Neodymium.localizedText("errorMessage.missingInfo"));
    }

    /// ========== validate content register page ========== ///
    
    @Step("validate fill in form placeholder")
    public void validatePlaceholders() 
    {
        lastNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.lastName")))).shouldBe(visible);
        firstNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.firstName")))).shouldBe(visible);
        degree.shouldHave(text(Neodymium.localizedText("fillIn.placeholder.degree"))).shouldBe(visible);
        matriculation.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.matriculation")))).shouldBe(visible);
        emailField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.email")))).shouldBe(visible);
        title.shouldHave(exactText(Neodymium.localizedText("fillIn.placeholder.title")));
        salutation.shouldHave(exactText(Neodymium.localizedText("fillIn.placeholder.salutation")));
    }



    @Override
    @Step("validate register page structure")
    public void validateStructure()
    {
        validatePlaceholders();

        language.should(exist).shouldBe(visible);
        
        // validate registration button
        registerButton.shouldHave(exactText(Neodymium.localizedText("button.register")));
    }

    /// ========== register page navigation ========== ///
    
    @Step("fill and send register form with '{user}'")
    public RegisterPage sendRegisterForm(User user)
    {
        // fill out the registration form
        $("#my-id4 [uk-tooltip='title: Salutation']").selectOption("Mr.");
        $("#my-id4 [uk-tooltip='title: Title']").selectOption("M.Sc.");
        firstNameField.val(user.getFirstName());
        lastNameField.val(user.getLastName());
        $("#my-id4 [uk-tooltip='title: Degree programme']").selectOption("[RCSE] Research in Computer & Systems Engineering");
        matriculation.val(user.getMatriculationNumber());
        emailField.val(user.getEmail());

        // click on the register button
        registerButton.click();

        return new RegisterPage().isExpectedPage();
    }

    @Step("fill and send register form with missing info '{user}'")
    public RegisterPage sendRegisterFormMissingInfo(User user)
    {
        // fill out the registration form
        salutation.selectOption("Mr.");
        title.selectOption("M.Sc.");
        firstNameField.val(user.getFirstName());
        lastNameField.val(user.getLastName());
        degree.selectOption("[RCSE] Research in Computer & Systems Engineering");
        matriculation.val(user.getMatriculationNumber());
        emailField.val(user.getEmail());

        // click on the register button
        registerButton.click();

        return new RegisterPage().isExpectedPage();
    }
}
