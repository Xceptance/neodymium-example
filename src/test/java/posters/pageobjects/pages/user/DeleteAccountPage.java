package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;

public class DeleteAccountPage extends AbstractBrowsingPage
{
    private SelenideElement deleteForm = $("#form-delete-account");

    private SelenideElement passwordField = $("#password");

    private SelenideElement deleteButton = $("#btn-delete-account");

    @Override
    @Step("ensure this is a delete account page")
    public DeleteAccountPage isExpectedPage()
    {
        super.isExpectedPage();
        deleteForm.should(exist);
        
        return this;
    }
    
    @Override
    @Step("validate delete account page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("deleteAccontPage.title"))).shouldBe(visible);
        
        // validate headline
        $("label[for='password']").shouldBe(exactText(Neodymium.localizedText("fillIn.inputDescription.password")));
        
        // validate placeholder
        passwordField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.password")))).shouldBe(visible);
        
        // validate "required fields" string
        $(".req-field").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);
        
        // validate button
        deleteButton.shouldHave(exactText(Neodymium.localizedText("button.delete"))).shouldBe(visible);
    }

    @Step("delete account")
    public HomePage deleteAccount(String password)
    {
        passwordField.setValue(password);
        deleteButton.click(ClickOptions.usingJavaScript());
        
        return new HomePage().isExpectedPage();
    }
}
