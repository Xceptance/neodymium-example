package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

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

    @Step("validate shipping address usage for billing address radio")
    public void validateAddressRadio()
    {
        $(".control-label").shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.useAddressForBilling"))).shouldBe(visible);
        $(".radio :nth-of-type(1)").shouldHave(matchText(Neodymium.localizedText("AddressPages.fillIn.radio.yes"))).shouldBe(visible);
        $(".radio :nth-of-type(2)").shouldHave(matchText(Neodymium.localizedText("AddressPages.fillIn.radio.no"))).shouldBe(visible);
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
