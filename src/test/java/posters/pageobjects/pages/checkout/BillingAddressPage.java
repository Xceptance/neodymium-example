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

public class BillingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement headline = $("#titleBillAddr");

    private SelenideElement nameField = $("#fullName");

    private SelenideElement companyField = $("#company");

    private SelenideElement addressField = $("#addressLine");

    private SelenideElement cityField = $("#city");

    private SelenideElement stateField = $("#state");

    private SelenideElement zipField = $("#zip");

    private SelenideElement countryField = $("#country");

    private SelenideElement addBillingButton = $("#btnAddBillAddr");

    @Override
    @Step("ensure this is a billing address page")
    public BillingAddressPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    /// ----- validate shipping address page ----- ///

    @Step("validate breadcrumb")
    public void validateBreadcrumb()
    {
        $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.cart"))).shouldBe(visible);
        $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.shippingAddress"))).shouldBe(visible);
        $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.billingAddress"))).shouldBe(visible);
        $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.payment"))).shouldBe(visible);
        $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.placeOrder"))).shouldBe(visible);
    }
    
    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines(String headline)
    {
        $$(".form-group").findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines("AddressPages.fillIn.headlines.fullName");
        validateFillInHeadlines("AddressPages.fillIn.headlines.company");
        validateFillInHeadlines("AddressPages.fillIn.headlines.address");
        validateFillInHeadlines("AddressPages.fillIn.headlines.city");
        validateFillInHeadlines("AddressPages.fillIn.headlines.state");
        validateFillInHeadlines("AddressPages.fillIn.headlines.zip");
        // validateFillInHeadlines("AddressPages.fillIn.headlines.country");
    }

    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {
        nameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.yourName")))).shouldBe(visible);
        companyField.shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.companyName")))).shouldBe(visible);
        addressField.shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.address")))).shouldBe(visible);
        zipField.shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.zip")))).shouldBe(visible);
    }

    @Step("validate country dropdown")
    public void validateCountryDropdown()
    {
        countryField.shouldBe(matchText(Neodymium.localizedText("AddressPages.fillIn.dropdown.usa"))).should(exist);
        countryField.shouldBe(matchText(Neodymium.localizedText("AddressPages.fillIn.dropdown.germany"))).should(exist);
    }

    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();

        // validate fill in form headline
        headline.shouldHave(exactText(Neodymium.localizedText("BillingAddressPage.fillIn.title"))).shouldBe(visible);
        validateFillInHeadlines();

        // validate fill in form structure
        validateFillInPlaceholder();

        // validate country selection dropdown
        validateCountryDropdown();

        // validate "required fields" string
        $(".reqField").shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.requiredFields"))).shouldBe(visible);

        // validate continue button
        addBillingButton.shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.button"))).shouldBe(visible);
    }
    
    /// ----- send billing address form ----- ///
    
    @Step("fill and send new billing address form")
    public PaymentPage sendBillingAddressForm(Address billingAddress)
    {
        String fullName = billingAddress.getFirstName() + " " + billingAddress.getLastName();

        return sendBillingAddressForm(fullName, billingAddress.getCompany(), billingAddress.getStreet(),
                                      billingAddress.getCity(), billingAddress.getState(), billingAddress.getZip(),
                                      billingAddress.getCountry());
    }
    
    @Step("fill and send new billing address form")
    public PaymentPage sendBillingAddressForm(String name, String company, String address, String city,
                                              String state, String zip, String country)
    {
        // enter parameters
        nameField.val(name);
        companyField.val(company);
        addressField.val(address);
        cityField.val(city);
        stateField.val(state);
        zipField.val(zip);
        countryField.selectOption(country);
        
        // click on "Continue" button
        addBillingButton.scrollTo().click();

        return new PaymentPage().isExpectedPage();
    }

    // ---------------------------------------------------
    
    /**
     * @param position
     *            The position of the billing address you want to choose
     * @return PaymentPage
     */
    @Step("select a billing address")
    public PaymentPage selectBillingAddress(int position)
    {
        final int index = position - 1;
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#billAddr" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseBillAddress").scrollTo().click();

        return new PaymentPage().isExpectedPage();
    }
}
