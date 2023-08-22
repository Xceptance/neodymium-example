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

    private SelenideElement addShippingButton = $("#btnAddDelAddr");

    @Override
    @Step("ensure this is a shipping address page")
    public GuestShippingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate content guest shipping address page ----- ///

    @Step("validate breadcrumb")
    public void validateBreadcrumb()
    {
        $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.cart"))).shouldBe(visible);
        $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.shippingAddress"))).shouldBe(visible);
        $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.billingAddress"))).shouldBe(visible);
        $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.payment"))).shouldBe(visible);
        $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.placeOrder"))).shouldBe(visible);
    }
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        // validate process numbers
        $("#crt span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.number"))).shouldBe(visible);
        $("#ship span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.number"))).shouldBe(visible);
        $("#bill span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.number"))).shouldBe(visible);
        $("#payment span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.number"))).shouldBe(visible);
        $("#chkout span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.number"))).shouldBe(visible);
        $("#orderCmplt span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.number"))).shouldBe(visible);
        
        // validate process names
        $("#crt h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.name"))).shouldBe(visible);
        $("#ship h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.name"))).shouldBe(visible);
        $("#bill h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.name"))).shouldBe(visible);
        $("#payment h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.name"))).shouldBe(visible);
        $("#chkout h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.name"))).shouldBe(visible);
        $("#orderCmplt h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.name"))).shouldBe(visible);
    }
    
    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.fullName")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.company")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.address")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.city")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.state")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.zip")));
        // TODO - fix after issue is fixed
        //$$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.country")));
    }
    
    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {
        $("#fullName").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.yourName")))).shouldBe(visible);
        $("#company").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.companyName")))).shouldBe(visible);
        $("#addressLine").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.address")))).shouldBe(visible);
        $("#zip").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.zip")))).shouldBe(visible);
    }
    
    @Step("validate country dropdown")
    public void validateCountryDropdown()
    {
        $("#country").shouldBe(matchText(Neodymium.localizedText("AddressPages.fillIn.dropdown.usa"))).should(exist);
        $("#country").shouldBe(matchText(Neodymium.localizedText("AddressPages.fillIn.dropdown.germany"))).should(exist);
    }
    
    @Step("validate shipping address usage for billing address radio")
    public void validateAddressRadio()
    {
        $(".control-label").shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.useAddressForBilling"))).shouldBe(visible);
        $(".radio :nth-of-type(1)").shouldHave(matchText(Neodymium.localizedText("AddressPages.fillIn.radio.yes"))).shouldBe(visible);
        $(".radio :nth-of-type(2)").shouldHave(matchText(Neodymium.localizedText("AddressPages.fillIn.radio.no"))).shouldBe(visible);
    }
    
    @Step("validate required string")
    public void validateRequiredString() 
    {
        $(".reqField").shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.requiredFields"))).shouldBe(visible);
    }

    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();

        // validate process wrap
        // TODO - after fixing issue 171: consistent element selectors for all checkout pages with progress indicator
        // validateProcessWrap();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("GuestShippingAddressPage.title"))).shouldBe(visible);

        // validate fill in form headlines
        validateFillInHeadlines();

        // validate fill in form structure
        validateFillInPlaceholder();

        // validate country selection dropdown
        validateCountryDropdown();

        // validate address usage radio
        validateAddressRadio();

        // validate "required fields" string
        validateRequiredString();

        // validate continue button
        addShippingButton.shouldHave(exactText(Neodymium.localizedText("AddressPages.button"))).shouldBe(visible);
    }

    /// ----- send shipping address form ----- ///

    @Step("fill and send shipping address form and go to guest billing address page")
    public GuestBillingAddressPage goToGuestBillingAddressPage(Address shippingAddress)
    {
        String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();

        return goToGuestBillingAddressPage(fullName, shippingAddress.getCompany(), shippingAddress.getStreet(),
                                           shippingAddress.getCity(), shippingAddress.getState(), shippingAddress.getZip(),
                                           shippingAddress.getCountry());
    }

    @Step("fill and send shipping address form and go to guest billing address page")
    public GuestBillingAddressPage goToGuestBillingAddressPage(String name, String company, String address, String city,
                                                               String state, String zip, String country)
    {
        // fill in form with parameters
        $("#fullName").val(name);
        $("#company").val(company);
        $("#addressLine").val(address);
        $("#city").val(city);
        $("#state").val(state);
        $("#zip").val(zip);
        $("#country").selectOption(country);

        $("#billEqualShipp-No").scrollTo().click();

        // go to guest billing address page
        addShippingButton.scrollTo().click();

        return new GuestBillingAddressPage().isExpectedPage();
    }

    @Step("fill and send shipping address form and go to guest payment page")
    public GuestPaymentPage goToGuestPaymentPage(Address shippingAddress)
    {
        String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();

        return goToGuestPaymentPage(fullName, shippingAddress.getCompany(), shippingAddress.getStreet(),
                                    shippingAddress.getCity(), shippingAddress.getState(), shippingAddress.getZip(),
                                    shippingAddress.getCountry());
    }

    @Step("fill and send shipping address form and go to guest payment page")
    public GuestPaymentPage goToGuestPaymentPage(String name, String company, String address, String city,
                                                 String state, String zip, String country)
    {
        // fill in form with parameters
        $("#fullName").val(name);
        $("#company").val(company);
        $("#addressLine").val(address);
        $("#city").val(city);
        $("#state").val(state);
        $("#zip").val(zip);
        $("#country").selectOption(country);

        $("#billEqualShipp-Yes").scrollTo().click();

        // go to guest payment page
        addShippingButton.scrollTo().click();

        return new GuestPaymentPage().isExpectedPage();
    }
}
