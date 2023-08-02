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
import posters.dataobjects.Address;

public class ShippingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement headline = $("#titleDelAddr");

    private SelenideElement nameField = $("#fullName");

    private SelenideElement companyField = $("#company");

    private SelenideElement addressField = $("#addressLine");

    private SelenideElement cityField = $("#city");

    private SelenideElement stateField = $("#state");

    private SelenideElement zipField = $("#zip");

    private SelenideElement countryField = $("#country");

    private SelenideElement addShippingButton = $("#btnAddDelAddr");

    @Override
    @Step("ensure this is a shipping address page")
    public ShippingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        headline.should(exist);
        return this;
    }

    /// ----- validate shipping address page ----- ///

    @Step("validate breadcrumb")
    public void validateBreadcrumb()
    {
        $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("General.breadcrumb.cart"))).shouldBe(visible);
        $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("General.breadcrumb.shippingAddress"))).shouldBe(visible);
        $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("General.breadcrumb.billingAddress"))).shouldBe(visible);
        $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("General.breadcrumb.payment"))).shouldBe(visible);
        $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("General.breadcrumb.placeOrder"))).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines(String headline)
    {
        $$(".form-group").findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines("General.fillIn.headlines.fullName");
        validateFillInHeadlines("General.fillIn.headlines.company");
        validateFillInHeadlines("General.fillIn.headlines.address");
        validateFillInHeadlines("General.fillIn.headlines.city");
        validateFillInHeadlines("General.fillIn.headlines.state");
        validateFillInHeadlines("General.fillIn.headlines.zip");
        // validateFillInHeadlines("General.fillIn.headlines.country");
    }

    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {
        nameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("General.fillIn.placeholder.yourName")))).shouldBe(visible);
        companyField.shouldHave(attribute("placeholder", (Neodymium.localizedText("General.fillIn.placeholder.companyName")))).shouldBe(visible);
        addressField.shouldHave(attribute("placeholder", (Neodymium.localizedText("General.fillIn.placeholder.address")))).shouldBe(visible);
        zipField.shouldHave(attribute("placeholder", (Neodymium.localizedText("General.fillIn.placeholder.zip")))).shouldBe(visible);
    }

    @Step("validate country dropdown")
    public void validateCountryDropdown()
    {
        countryField.shouldBe(matchText(Neodymium.localizedText("General.fillIn.dropdown.usa"))).should(exist);
        countryField.shouldBe(matchText(Neodymium.localizedText("General.fillIn.dropdown.germany"))).should(exist);
    }

    @Step("validate shipping address usage for billing address radio")
    public void validateAddressRadio()
    {
        $(".control-label").shouldHave(exactText(Neodymium.localizedText("General.fillIn.headlines.useAddressForBilling"))).shouldBe(visible);
        $(".radio :nth-of-type(1)").shouldHave(matchText(Neodymium.localizedText("General.fillIn.radio.yes"))).shouldBe(visible);
        $(".radio :nth-of-type(2)").shouldHave(matchText(Neodymium.localizedText("General.fillIn.radio.no"))).shouldBe(visible);
    }

    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();

        // validate fill in form headline
        headline.shouldHave(exactText(Neodymium.localizedText("ShippingAddressPage.fillIn.headline"))).shouldBe(visible);
        validateFillInHeadlines();

        // validate fill in form structure
        validateFillInPlaceholder();

        // validate country selection dropdown
        validateCountryDropdown();

        // validate address usage radio
        validateAddressRadio();

        // validate "required fields" string
        $(".reqField").shouldHave(exactText(Neodymium.localizedText("General.fillIn.headlines.requiredFields"))).shouldBe(visible);

        // validate continue button
        addShippingButton.shouldHave(exactText(Neodymium.localizedText("General.fillIn.button"))).shouldBe(visible);
    }

    /// ----- send shipping address form ----- ///

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
        nameField.val(name);
        companyField.val(company);
        addressField.val(address);
        cityField.val(city);
        stateField.val(state);
        zipField.val(zip);
        countryField.selectOption(country);

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

    /**
     * @param position
     *            position of the shipping address
     * @return BillingAddressPage
     */
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
