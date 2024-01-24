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
    private SelenideElement deleteForm = $("#formDeleteAccount");

    private SelenideElement passwordField = $("#password");

    private SelenideElement deleteButton = $("#btnDeleteAccount");

    @Override
    @Step("ensure this is a delete account page")
    public DeleteAccountPage isExpectedPage()
    {
        super.isExpectedPage();
        deleteForm.should(exist);
        
        return this;
    }

    /// ----- validate content delete account page ----- ///
    
    @Step("validate required string")
    public void validateRequiredString() 
    {
        $(".me-auto").shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.requiredFields"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate delete account page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        deleteForm.find(".h2").shouldHave(exactText(Neodymium.localizedText("DeleteAccontPage.title"))).shouldBe(visible);
        
        // validate headline
        $("label[for='password']").shouldBe(exactText(Neodymium.localizedText("DeleteAccontPage.headline")));
        
        // validate placeholder
        passwordField.shouldHave(attribute("placeholder", (Neodymium.localizedText("DeleteAccontPage.placeholder")))).shouldBe(visible);
        
        // validate "required fields" string
        validateRequiredString();
        
        // validate button
        deleteButton.shouldHave(exactText(Neodymium.localizedText("DeleteAccontPage.button"))).shouldBe(visible);
    }
    
    /// ----- delete account page navigation ----- ///

    @Step("delete account")
    public HomePage deleteAccount(String password)
    {
        passwordField.setValue(password);
        deleteButton.click(ClickOptions.usingJavaScript());
        
        return new HomePage().isExpectedPage();
    }
}
