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

public class ShippingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titleDelAddr");

    private SelenideElement addShippingButton = $("#btnAddDelAddr");

    @Override
    @Step("ensure this is a shipping address page")
    public ShippingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate shipping address page ----- ///

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

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("ShippingAddressPage.fillIn.title"))).shouldBe(visible);
        
        // validate fill in form headline
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
        addShippingButton.shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.button"))).shouldBe(visible);
    }

    /// ----- send shipping address form ----- ///

    @Step("fill and send shipping address form")
    public BillingAddressPage sendShippingAddressForm(Address shippingAddress, boolean sameBillingAddress)
    {
        String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();

        return sendShippingAddressForm(fullName, shippingAddress.getCompany(), shippingAddress.getStreet(),
                                       shippingAddress.getCity(), shippingAddress.getState(), shippingAddress.getZip(),
                                       shippingAddress.getCountry(), sameBillingAddress);
    }

    @Step("fill and send shipping address form")
    public BillingAddressPage sendShippingAddressForm(String name, String company, String address, String city,
                                                      String state, String zip, String country, boolean sameBillingAddress)
    {        
        // enter parameters
        $("#fullName").val(name);
        $("#company").val(company);
        $("#addressLine").val(address);
        $("#city").val(city);
        $("#state").val(state);
        $("#zip").val(zip);
        $("#country").selectOption(country);

        // check if shipping address and billing address is equal
        if (sameBillingAddress)
        {
            $("#billEqualShipp-Yes").scrollTo().click();
        }
        else
        {
            $("#billEqualShipp-No").scrollTo().click();
        }

        // click on "Continue" button
        addShippingButton.scrollTo().click();

        return new BillingAddressPage().isExpectedPage();
    }

    // ---------------------------------------------------------------

    @Step("select a shipping address")
    public BillingAddressPage selectShippingAddress(int position)
    {
        final int index = position - 1;
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#delAddr" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseAddressContinue").scrollTo().click();

        return new BillingAddressPage().isExpectedPage();
    }
}
