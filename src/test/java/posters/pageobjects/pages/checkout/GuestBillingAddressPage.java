package posters.pageobjects.pages.checkout;

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

    /// ========== validate content guest billing address page ========== ///
    
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
        $("#address-form-input-full-name").shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.name")))).shouldBe(visible);
        $("#address-form-input-company").shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.companyName")))).shouldBe(visible);
        $("#address-form-input-adress-line").shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.address")))).shouldBe(visible);
        $("#address-form-input-zip").shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.zip")))).shouldBe(visible);
    }
    
    @Step("validate country dropdown")
    public void validateCountryDropdown()
    {
        $("#address-form-select-country").shouldBe(matchText(Neodymium.localizedText("fillIn.dropdown.country.usa"))).should(exist);
        $("#address-form-select-country").shouldBe(matchText(Neodymium.localizedText("fillIn.dropdown.country.germany"))).should(exist);
    }
    
    @Step("validate required string")
    public void validateRequiredString() 
    {
        $(".me-auto").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate process wrap
         validateProcessWrap();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("guestBillingAddressPage.title"))).shouldBe(visible);

        // validate fill form headlines
        validateFillInHeadlines();

        // validate fill in form structure
        validateFillInPlaceholder();

       // validate country selection dropdown
        validateCountryDropdown();

       // validate "required fields" string
        validateRequiredString();

        // validate continue button
        addBillingButton.shouldHave(exactText(Neodymium.localizedText("button.continue"))).shouldBe(visible);
    }

    /// ========== send billing address form ========== ///

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
        addBillingButton.click(ClickOptions.usingJavaScript());

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
