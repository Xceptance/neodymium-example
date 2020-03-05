package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.dataobjects.Address;
import posters.pageobjects.pages.checkout.AbstractCheckoutPage;
import posters.pageobjects.pages.checkout.PaymentListPage;

public class NewAddressOverlay extends AbstractComponent
{

    private SelenideElement addNewAddressButton = $("#addAdressModal");

    private SelenideElement nameField = $("#fullName");

    private SelenideElement companyField = $("#company");

    private SelenideElement addressField = $("#addressLine");

    private SelenideElement cityField = $("#city");

    private SelenideElement stateField = $("#state");

    private SelenideElement zipField = $("#zip");

    private SelenideElement countryField = $("#country");

    @Override
    public void isComponentAvailable()
    {
        addNewAddressButton.shouldBe(visible);
    }

    @Step("fill and send shipping address form")
    protected NewAddressOverlay fillForm(String name, String company, String address, String city,
                                         String state, String zip, String country)
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
        return this;
    }

    @Step("fill and send billing address form")
    public PaymentListPage sendBillingAddressForm(Address address)
    {
        fillForm(address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(), address.getState(),
                 address.getZip(), address.getCountry());
        // Open the billing addresses or payment options page, depending on which radio button you checked
        // Click on Continue
        sendForm();
        return new PaymentListPage().isExpectedPage();
    }

    @Step("fill and send billing address form")
    public PaymentListPage sendBillingAddressForm(String name, String company, String address, String city,
                                                  String state, String zip, String country)
    {
        fillForm(name, company, address, city, state, zip, country);
        sendForm();
        PaymentListPage paymentPage = new PaymentListPage();
        paymentPage.isExpectedPage();
        return paymentPage;
    }

    public NewAddressOverlay fillForm(Address container)
    {
        Address address = (Address) container;
        return fillForm(address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(), address.getState(), address.getZip(),
                        address.getCountry());
    }

    public AbstractCheckoutPage sendForm()
    {
        $("#btnAddBillAddr").scrollTo().click();
        return new PaymentListPage();
    }
}
