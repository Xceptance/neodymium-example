package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.checkout.GuestBillingAddressPage;
import posters.pageobjects.pages.checkout.GuestPaymentPage;
import posters.pageobjects.pages.user.AddressOverviewPage;
import posters.tests.testdata.dataobjects.Address;

public class AddressForm extends AbstractComponent
{
    private SelenideElement lastNameField = $("#address-last-name");

    private SelenideElement firstNameField = $("#address-first-name");
    
    private SelenideElement companyField = $("#address-company");
    
    private SelenideElement addressLineField = $("#address-address-line");
    
    private SelenideElement cityField = $("#address-city");
    
    private SelenideElement stateField = $("#address-state");
    
    private SelenideElement zipField = $("#address-zip");
    
    private SelenideElement countryField = $("#address-country");
    
    private SelenideElement requiredField = $(".me-auto");
    
    private SelenideElement addAddressButton = $("#btn-add-shipp-addr, #button-add-shipping-address, #btn-add-bill-addr");
    
    @Override
    @Step("ensure availability address form")
    public void isComponentAvailable()
    {
        lastNameField.should(exist);
    }
    
    /// ========== address form navigation ========== ///
    
    private void fillInAddressForm(Address address) 
    {
        lastNameField.val(address.getLastName());
        firstNameField.val(address.getFirstName());
        companyField.val(address.getCompany());
        addressLineField.val(address.getStreet());
        cityField.val(address.getCity());
        stateField.val(address.getState());
        zipField.val(address.getZip());
        countryField.selectOption(address.getCountry());
    }
    
    @Step("fill in address form with {address}")
    public AddressOverviewPage addNewAddress(Address address) 
    {      
        // fill in shipping address form
        fillInAddressForm(address);
        
        // click add new address button
        addAddressButton.click(ClickOptions.usingJavaScript());
        
        return new AddressOverviewPage().isExpectedPage();
    }
    
    @Step("fill and send shipping address form with '{shippingAddress}' and go to guest billing address page")
    public GuestBillingAddressPage goToGuestBillingAddressPage(Address shippingAddress)
    {       
        // fill in form with parameters
        fillInAddressForm(shippingAddress);

        // go to guest billing address page
        $("#bill-unequal-shipp").click(ClickOptions.usingJavaScript());
        addAddressButton.click(ClickOptions.usingJavaScript());

        return new GuestBillingAddressPage().isExpectedPage();
    }
     
    @Step("fill and send address form with '{address}' and go to guest payment page")
    public GuestPaymentPage goToGuestPaymentPage(Address address)
    {
        // fill in form with parameters
        fillInAddressForm(address);

        // go to guest payment page
        if ($("#bill-equal-shipp").exists() && $("#bill-unequal-shipp").exists()) 
        {
            $("#bill-equal-shipp").click(ClickOptions.usingJavaScript());
        }
         
        addAddressButton.click(ClickOptions.usingJavaScript());

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
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.lastName"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.firstName"));
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
        lastNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.lastName")))).shouldBe(visible);
        firstNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.firstName")))).shouldBe(visible);
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
        $("#address-country-help").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.shippingInfo"))).shouldBe(visible);

        // validate "required fields" string
        requiredField.shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);
    }
}
