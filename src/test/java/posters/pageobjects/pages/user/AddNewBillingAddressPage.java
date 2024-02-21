package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.tests.testdata.dataobjects.Address;

public class AddNewBillingAddressPage extends AbstractBrowsingPage
{    
    private SelenideElement fullNameField = $("#address-form-input-full-name");
    
    private SelenideElement companyField = $("#address-form-input-company");
    
    private SelenideElement addressLineField = $("#address-form-input-adress-line");
    
    private SelenideElement cityField = $("#address-form-input-city");
    
    private SelenideElement stateField = $("#address-form-input-state");
    
    private SelenideElement zipField = $("#address-form-input-zip");
    
    private SelenideElement countryField = $("#address-form-select-country");
    
    private SelenideElement addNewAddressButton = $("#btnAddBillAddr");

    @Override
    @Step("ensure this is a add new billing address page")
    public AddNewBillingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#formAddBillAddr .h2").should(exist);
        return this;
    }

    /// ========== validate content add new billing address page ========== ///

    private void validateFillInHeadlines(String headline)
    {
        $$(".form-label").findBy(exactText(headline)).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.fullName"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.company"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.address"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.city"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.state"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.zip"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.country"));
    }
    
    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {
        fullNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.name")))).shouldBe(visible);
        companyField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.companyName")))).shouldBe(visible);
        addressLineField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.address")))).shouldBe(visible);
        cityField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.city")))).shouldBe(visible);
        stateField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.state")))).shouldBe(visible);
        zipField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.zip")))).shouldBe(visible);
    }
    
    @Step("validate country dropdown")
    public void validateCountryDropdown()
    {
        countryField.shouldBe(matchText(Neodymium.localizedText("fillIn.dropdown.country.usa"))).should(exist);
        countryField.shouldBe(matchText(Neodymium.localizedText("fillIn.dropdown.country.germany"))).should(exist);
    }
    
    @Override
    @Step("validate add new billing address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("addNewBillingAddressPage.title"))).shouldBe(visible);
        
        // validate fill in form headlines
        validateFillInHeadlines();

        // validate fill in form placeholder
        validateFillInPlaceholder();

        // validate country selection dropdown
        validateCountryDropdown();
        
        // validate country selection help text
        $("#address-form-select-country-help-block").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.shippingInfo"))).shouldBe(visible);

        // validate "required fields" string
        $(".reqField").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);

        // validate continue button
        addNewAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewAddress"))).shouldBe(visible);
    }
    
    /// ========== add new billing address page navigation ========== ///
    
    @Step("fill in billing address form with {billingAddress}")
    public AddressOverviewPage addNewBillingAddress(Address billingAddress) 
    {      
        // fill in shipping address form
        fullNameField.val(billingAddress.getFullName());
        companyField.val(billingAddress.getCompany());
        addressLineField.val(billingAddress.getStreet());
        cityField.val(billingAddress.getCity());
        stateField.val(billingAddress.getState());
        zipField.val(billingAddress.getZip());
        countryField.selectOption(billingAddress.getCountry());
        
        // click add new address button
        addNewAddressButton.click();
        
        return new AddressOverviewPage().isExpectedPage();
    }
}