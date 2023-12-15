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

public class GuestBillingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titleBillAddr");

    private SelenideElement addBillingButton = $(".ms-auto.btn");

    @Override
    @Step("ensure this is a billing address page")
    public GuestBillingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate content guest billing address page ----- ///
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        // validate process numbers
        $(".progress-step-1 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.number"))).shouldBe(visible);
        $(".progress-step-2 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.number"))).shouldBe(visible);
        $(".progress-step-3 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.number"))).shouldBe(visible);
        $(".progress-step-4 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.number"))).shouldBe(visible);
        $(".progress-step-5 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.number"))).shouldBe(visible);
        $(".progress-step-6 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.number"))).shouldBe(visible);
        
        // validate process names
        $(".progress-step-1 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.name"))).shouldBe(visible);
        $(".progress-step-2 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.name"))).shouldBe(visible);
        $(".progress-step-3 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.name"))).shouldBe(visible);
        $(".progress-step-4 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.name"))).shouldBe(visible);
        $(".progress-step-5 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.name"))).shouldBe(visible);
        $(".progress-step-6 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.name"))).shouldBe(visible);
    }
    
    private void validateFillInHeadlines(String headline)
    {
        $$(".mb-3").findBy(exactText(headline)).shouldBe(visible);
    }

    private void validateFillInHeadlinesStateZip(String headline)
    {
        $$(".mb-3 .col .form-label").findBy(exactText(headline)).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("AddressPages.fillIn.headlines.fullName"));
        validateFillInHeadlines(Neodymium.localizedText("AddressPages.fillIn.headlines.company"));
        validateFillInHeadlines(Neodymium.localizedText("AddressPages.fillIn.headlines.address"));
        validateFillInHeadlines(Neodymium.localizedText("AddressPages.fillIn.headlines.city"));
        validateFillInHeadlinesStateZip(Neodymium.localizedText("AddressPages.fillIn.headlines.state"));
        validateFillInHeadlinesStateZip(Neodymium.localizedText("AddressPages.fillIn.headlines.zip"));
        // TODO - fix after issue is fixed
        //validateFillInHeadlines(Neodymium.localizedText("AddressPages.fillIn.headlines.country"));
    }

    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {
        $("#address-form-input-full-name").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.yourName")))).shouldBe(visible);
        $("#address-form-input-company").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.companyName")))).shouldBe(visible);
        $("#address-form-input-adress-line").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.address")))).shouldBe(visible);
        $("#address-form-input-zip").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.zip")))).shouldBe(visible);
    }
    
    @Step("validate country dropdown")
    public void validateCountryDropdown()
    {
        $("#address-form-select-country").shouldBe(matchText(Neodymium.localizedText("AddressPages.fillIn.dropdown.usa"))).should(exist);
        $("#address-form-select-country").shouldBe(matchText(Neodymium.localizedText("AddressPages.fillIn.dropdown.germany"))).should(exist);
    }
    
    @Step("validate required string")
    public void validateRequiredString() 
    {
        $(".me-auto").shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.requiredFields"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate process wrap
         validateProcessWrap();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("GuestBillingAddressPage.title"))).shouldBe(visible);

        // validate fill form headlines
        validateFillInHeadlines();

        // validate fill in form structure
        validateFillInPlaceholder();

       // validate country selection dropdown
        validateCountryDropdown();

       // validate "required fields" string
        validateRequiredString();

        // validate continue button
        addBillingButton.shouldHave(exactText(Neodymium.localizedText("AddressPages.button"))).shouldBe(visible);
    }

    /// ----- send billing address form ----- ///

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
        // go to guest payment page
        addBillingButton.scrollTo().click();

        return new GuestPaymentPage().isExpectedPage();
    }
    
    @Step("fill and send new billing address form with '{billingAddress}'")
    public GuestPaymentPage goToGuestPaymentPage(Address billingAddress)
    {
        String fullName = billingAddress.getFirstName() + " " + billingAddress.getLastName();

        return goToGuestPaymentPage(fullName, billingAddress.getCompany(), billingAddress.getStreet(),
                                    billingAddress.getCity(), billingAddress.getState(), billingAddress.getZip(),
                                    billingAddress.getCountry());
    }
}
