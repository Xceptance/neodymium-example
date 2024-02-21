package posters.pageobjects.pages.checkout;

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
import posters.tests.testdata.dataobjects.Address;

public class GuestShippingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titleDelAddr");
    
    private SelenideElement fullNameField = $("#address-form-input-full-name");
    
    private SelenideElement companyField = $("#address-form-input-company");
    
    private SelenideElement addressLineField = $("#address-form-input-adress-line");
    
    private SelenideElement cityField = $("#address-form-input-city");
    
    private SelenideElement stateField = $("#address-form-input-state");
    
    private SelenideElement zipField = $("#address-form-input-zip");
    
    private SelenideElement countryField = $("#address-form-select-country");

    private SelenideElement addShippingButton = $(".ms-auto.btn");

    @Override
    @Step("ensure this is a shipping address page")
    public GuestShippingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ========== validate content guest shipping address page ========== ///
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        for (int i = 1; i <= 6; i++) 
        {
            $(".progress-step-" + i + " .progress-bubble").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".number"))).shouldBe(visible);
            $(".progress-step-" + i + " .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".name"))).shouldBe(visible);    
        }
    }
    
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
    
    @Step("validate shipping address usage for billing address radio")
    public void validateAddressRadio()
    {
        $(".mb-1").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.useThisAddressForBilling"))).shouldBe(visible);
        $$(".form-check-label").findBy(attribute("for", "billEqualShipp-Yes")).shouldHave(exactText(Neodymium.localizedText("fillIn.radio.yes"))).shouldBe(visible);        
        $$(".form-check-label").findBy(attribute("for", "billEqualShipp-No")).shouldHave(exactText(Neodymium.localizedText("fillIn.radio.no"))).shouldBe(visible);
    }

    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate process wrap
         validateProcessWrap();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("guestShippingAddressPage.title"))).shouldBe(visible);

        // validate fill in form headlines
        validateFillInHeadlines();

        // validate fill in form placeholder
        validateFillInPlaceholder();

        // validate country selection dropdown
        validateCountryDropdown();

        // validate address usage radio
        validateAddressRadio();

        // validate "required fields" string
        $(".me-auto").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);

        // validate continue button
        addShippingButton.shouldHave(exactText(Neodymium.localizedText("button.continue"))).shouldBe(visible);
    }

    /// ========== send shipping address form ========== ///
    
    @Step("fill and send shipping address form with '{shippingAddress}' and go to guest billing address page")
    public GuestBillingAddressPage goToGuestBillingAddressPage(Address shippingAddress)
    {
        String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();
        
        // fill in form with parameters
        $("#address-form-input-full-name").val(fullName);
        $("#address-form-input-company").val(shippingAddress.getCompany());
        $("#address-form-input-adress-line").val(shippingAddress.getStreet());
        $("#address-form-input-city").val(shippingAddress.getCity());
        $("#address-form-input-state").val(shippingAddress.getState());
        $("#address-form-input-zip").val(shippingAddress.getZip());
        $("#address-form-select-country").selectOption(shippingAddress.getCountry());

        // go to guest billing address page
        $("#billEqualShipp-No").click();
        addShippingButton.click();

        return new GuestBillingAddressPage().isExpectedPage();
    }

    private GuestPaymentPage goToGuestPaymentPage(String name, String company, String address, String city,
                                                 String state, String zip, String country)
    {
        // fill in form with parameters
         $("#address-form-input-full-name").val(name);
         $("#address-form-input-company").val(company);
         $("#address-form-input-adress-line").val(address);
         $("#address-form-input-city").val(city);
         $("#address-form-input-state").val(state);
         $("#address-form-input-zip").val(zip);
         $("#address-form-select-country").selectOption(country);

        $("#billEqualShipp-Yes").click();

        // go to guest payment page
        addShippingButton.click();

        return new GuestPaymentPage().isExpectedPage();
    }
    
    @Step("fill and send shipping address form with '{shippingAddress}' and go to guest payment page")
    public GuestPaymentPage goToGuestPaymentPage(Address shippingAddress)
    {
        String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();

        return goToGuestPaymentPage(fullName, shippingAddress.getCompany(), shippingAddress.getStreet(),
                                    shippingAddress.getCity(), shippingAddress.getState(), shippingAddress.getZip(),
                                    shippingAddress.getCountry());
    }
}
