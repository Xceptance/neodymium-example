package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Address;

public class GuestBillingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titleBillAddr");

    private SelenideElement addBillingButton = $("#btnAddBillAddr");

    @Override
    @Step("ensure this is a billing address page")
    public GuestBillingAddressPage isExpectedPage()
    {
        title.should(exist);
        return this;
    }

    /// ----- validate guest billing address page ----- ///

    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();

        // validate process wrap
        // validateProcessWrap();

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

    @Step("fill and send new billing address form")
    public GuestPaymentPage goToGuestPaymentPage(Address billingAddress)
    {
        String fullName = billingAddress.getFirstName() + " " + billingAddress.getLastName();

        return goToGuestPaymentPage(fullName, billingAddress.getCompany(), billingAddress.getStreet(),
                                    billingAddress.getCity(), billingAddress.getState(), billingAddress.getZip(),
                                    billingAddress.getCountry());
    }

    @Step("fill and send new billing address form")
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

        // go to guest payment page
        addBillingButton.scrollTo().click();

        return new GuestPaymentPage().isExpectedPage();
    }
}
