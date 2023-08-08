package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Address;

public class BillingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titleBillAddr");

    private SelenideElement addBillingButton = $("#btnAddBillAddr");

    @Override
    @Step("ensure this is a billing address page")
    public BillingAddressPage isExpectedPage()
    {
        title.should(exist);
        return this;
    }

    /// ----- validate billing address page ----- ///

    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();
        
        // validate process wrap
        //validateProcessWrap();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.title.billing"))).shouldBe(visible);
        
        // validate fill form headline
        validateFillInHeadlines();

        // validate fill in form structure
        validateFillInPlaceholder();

        // validate country selection dropdown
        validateCountryDropdown();

        // validate "required fields" string
        validateRequiredString();

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
        $("#fullName").val(name);
        $("#company").val(company);
        $("#addressLine").val(address);
        $("#city").val(city);
        $("#state").val(state);
        $("#zip").val(zip);
        $("#country").selectOption(country);
        
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
