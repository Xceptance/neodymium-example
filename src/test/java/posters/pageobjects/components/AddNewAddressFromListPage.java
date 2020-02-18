package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.AllureAddons;

import io.qameta.allure.Step;
import posters.dataobjects.Address;
import posters.pageobjects.pages.checkout.BillingAddressSelectionListPage;
import posters.pageobjects.pages.checkout.PaymentSelectionListPage;

public class AddNewAddressFromListPage extends AbstractComponent
{

    private SelenideElement addNewAddressButton = $("#addAdressModal");

    private SelenideElement nameField = $("#fullName");

    private SelenideElement companyField = $("#company");

    private SelenideElement addressField = $("#addressLine");

    private SelenideElement cityField = $("#city");

    private SelenideElement stateField = $("#state");

    private SelenideElement zipField = $("#zip");

    private SelenideElement countryField = $("#country");

    private SelenideElement addShippingButton = $("#btnAddDelAddr");

    @Override
    public void isComponentAvailable()
    {
        addNewAddressButton.shouldBe(visible);
    }

    @Step("fill and send shipping address form")
    private void sendForm(String name, String company, String address, String city,
                          String state, String zip, String country, boolean sameBillingAddress)
    {
        $("#addAdressModal").click();
        // Name
        // Enter the name parameter
        nameField.val(name);
        // Company
        // Enter the company parameter
        companyField.val(company);
        // Address
        // Enter the address parameter
        addressField.val(address);
        // City
        // Enter the city parameter
        cityField.val(city);
        // State
        // Enter the state parameter
        stateField.val(state);
        // Zip
        // Enter the zip parameter
        zipField.val(zip);
        // Country
        // Select the country whose label equals the parameter
        countryField.selectOption(country);
        // Radio Button
        // Click the radio button for Yes or No
        if (sameBillingAddress)
        {
            $("#billEqualShipp-Yes").scrollTo().click();
        }
        else
        {
            $("#billEqualShipp-No").scrollTo().click();
        }
        // Open the billing addresses or payment options page, depending on which radio button you checked
        // Click on Continue
        addShippingButton.scrollTo().click();

    }

    @Step("fill and send shipping address form")
    public BillingAddressSelectionListPage sendShippingAddressForm(Address address, boolean sameBillingAddress)
    {
        AllureAddons.addToReport("shipping address", address + " use for billing " + sameBillingAddress);
        sendForm(address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(), address.getState(),
                 address.getZip(), address.getCountry(), sameBillingAddress);
        BillingAddressSelectionListPage billingAddressPage = new BillingAddressSelectionListPage();
        billingAddressPage.isExpectedPage();
        return billingAddressPage;
    }

    @Step("fill and send billing address form")
    public PaymentSelectionListPage sendBillingAddressForm(Address address)
    {
        AllureAddons.addToReport("billing address", address);
        sendForm(address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(), address.getState(),
                 address.getZip(), address.getCountry(), false);
        PaymentSelectionListPage paymentPage = new PaymentSelectionListPage();
        paymentPage.isExpectedPage();
        return paymentPage;
    }

    @Step("fill and send shipping address form")
    public BillingAddressSelectionListPage sendShippingAddressForm(String name, String company, String address, String city,
                                                                   String state, String zip, String country, boolean sameBillingAddress)
    {
        sendForm(name, company, address, city, state, zip, country, sameBillingAddress);
        BillingAddressSelectionListPage billingAddressPage = new BillingAddressSelectionListPage();
        billingAddressPage.isExpectedPage();
        return billingAddressPage;
    }

    @Step("fill and send billing address form")
    public PaymentSelectionListPage sendBillingAddressForm(String name, String company, String address, String city,
                                                           String state, String zip, String country)
    {
        sendForm(name, company, address, city, state, zip, country, false);
        PaymentSelectionListPage paymentPage = new PaymentSelectionListPage();
        paymentPage.isExpectedPage();
        return paymentPage;
    }
}