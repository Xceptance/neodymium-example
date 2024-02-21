package posters.pageobjects.components;

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
import posters.pageobjects.pages.checkout.GuestBillingAddressPage;
import posters.pageobjects.pages.checkout.GuestPaymentPage;
import posters.pageobjects.pages.user.AddressOverviewPage;
import posters.tests.testdata.dataobjects.Address;

public class AddressForm extends AbstractComponent
{
    private SelenideElement fullNameField = $("#address-form-input-full-name");
    
    private SelenideElement companyField = $("#address-form-input-company");
    
    private SelenideElement addressLineField = $("#address-form-input-adress-line");
    
    private SelenideElement cityField = $("#address-form-input-city");
    
    private SelenideElement stateField = $("#address-form-input-state");
    
    private SelenideElement zipField = $("#address-form-input-zip");
    
    private SelenideElement countryField = $("#address-form-select-country");
    
    private SelenideElement requiredField = $(".reqField, .me-auto");
    
    private SelenideElement addNewAddressToAddressOverviewButton = $("#btnAddShippAddr, #btnAddBillAddr");
    
    private SelenideElement addNewGuestAddressButton = $("#button-add-shipping-address, #button-add-billing-address");
    
    @Override
    @Step("ensure availability top navigation")
    public void isComponentAvailable()
    {
        $("#address-form-input-full-name").should(exist);
    }
    
    /// ========== address form navigation ========== ///
    
    @Step("fill in address form with {address}")
    public AddressOverviewPage addNewAddress(Address address) 
    {      
        // fill in shipping address form
        fullNameField.val(address.getFullName());
        companyField.val(address.getCompany());
        addressLineField.val(address.getStreet());
        cityField.val(address.getCity());
        stateField.val(address.getState());
        zipField.val(address.getZip());
        countryField.selectOption(address.getCountry());
        
        // click add new address button
        addNewAddressToAddressOverviewButton.click();
        
        return new AddressOverviewPage().isExpectedPage();
    }
    
    @Step("fill and send shipping address form with '{shippingAddress}' and go to guest billing address page")
    public GuestBillingAddressPage goToGuestBillingAddressPage(Address shippingAddress)
    {       
        // fill in form with parameters
        fullNameField.val(shippingAddress.getFullName());
        companyField.val(shippingAddress.getCompany());
        addressLineField.val(shippingAddress.getStreet());
        cityField.val(shippingAddress.getCity());
        stateField.val(shippingAddress.getState());
        zipField.val(shippingAddress.getZip());
        countryField.selectOption(shippingAddress.getCountry());

        // go to guest billing address page
        $("#billEqualShipp-No").click();
        addNewGuestAddressButton.click();

        return new GuestBillingAddressPage().isExpectedPage();
    }
     
    @Step("fill and send address form with '{address}' and go to guest payment page")
    public GuestPaymentPage goToGuestPaymentPage(Address address)
    {
        // fill in form with parameters
        fullNameField.val(address.getFullName());
        companyField.val(address.getCompany());
        addressLineField.val(address.getStreet());
        cityField.val(address.getCity());
        stateField.val(address.getState());
        zipField.val(address.getZip());
        countryField.selectOption(address.getCountry());

        // go to guest payment page
        if ($("#billEqualShipp-Yes").exists() && $("#billEqualShipp-No").exists()) 
        {
            $("#billEqualShipp-Yes").click();
        }
         
        addNewGuestAddressButton.click();

        return new GuestPaymentPage().isExpectedPage();
    }
    
    /// ========== validate address form ========== ///
    
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
    
    @Step("validate address form structure")
    public void validateStructure()
    {       
        // validate fill in form headlines
        validateFillInHeadlines();

        // validate fill in form placeholder
        validateFillInPlaceholder();

        // validate country selection dropdown
        validateCountryDropdown();
        
        // validate country selection help text
        $("#address-form-select-country-help-block").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.shippingInfo"))).shouldBe(visible);

        // validate "required fields" string
        requiredField.shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);
    }
}
