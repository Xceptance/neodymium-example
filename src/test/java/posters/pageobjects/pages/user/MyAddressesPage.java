package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$x;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.dataobjects.Address;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class MyAddressesPage extends AbstractBrowsingPage
{
    private SelenideElement addNewShippingButton = $("#linkAddNewShipAddr");

    private SelenideElement addNewBillingButton = $("#linkAddNewBillAddr");

    private SelenideElement nameField = $("#fullName");

    private SelenideElement companyField = $("#company");

    private SelenideElement addressLineField = $("#addressLine");

    private SelenideElement cityField = $("#city");

    private SelenideElement stateField = $("#state");

    private SelenideElement zipField = $("#zip");

    private SelenideElement countryDropdownMenu = $("#country");

    private SelenideElement confirmEnteredShippingAddress = $("#btnAddShippAddr");

    private SelenideElement confirmEnteredBillingAddress = $("#btnAddBillAddr");

    private SelenideElement saveEditedShippingAddress = $("#btnUpdateDelAddr");

    private SelenideElement saveEditedBillingAddress = $("#btnUpdateBillAddr");

    private String editShippingAddressButton = "form[action='/posters/updateShippingAddress']>button";

    private String editBillingAddressButton = "form[action='/posters/updateBillingAddress']>button";

    private String deleteShippingAddressButton = "form[action='/posters/confirmDeleteShippingAddress']>button";

    private String deleteBillingAddressButton = "form[action='/posters/confirmDeleteBillingAddress']>button";

    @Override
    public MyAddressesPage isExpectedPage()
    {
        $("#titleAddressOverview").shouldBe(visible);
        return this;
    }

    @Override
    public MyAddressesPage validateStructure()
    {
        $("#titleDelAddr").shouldBe(visible);
        $("#titleBillAddr").shouldBe(visible);
        addNewShippingButton.shouldBe(visible);
        addNewBillingButton.shouldBe(visible);
        return this;
    }

    private MyAddressesPage enterAddress(String name, String company, String addresLine, String city, String state, String zip, String country)
    {
        nameField.setValue(name);
        companyField.setValue(company);
        addressLineField.setValue(addresLine);
        cityField.setValue(city);
        stateField.setValue(state);
        zipField.setValue(zip);
        countryDropdownMenu.selectOption(country);
        return this;
    }

    private MyAddressesPage validateAddress(SelenideElement addressCard, String name, String company, String addresLine, String city, String state, String zip,
                                            String country)
    {
        addressCard.find("li.name").shouldHave(exactText(name));
        addressCard.find("li.company").shouldHave(exactText(company));
        addressCard.find("li.addressLine").shouldHave(exactText(addresLine));
        addressCard.find("span.city").shouldHave(exactText(city));
        addressCard.find("span.state").shouldHave(exactText(state));
        addressCard.find("span.zip").shouldHave(exactText(zip));
        addressCard.find("li.country").shouldHave(exactText(country));
        return this;
    }

    private SelenideElement getShippingAddressCard(String addressLine)
    {
        return $$x("//li[contains(@id,'shippingAddr')]/ul/li[contains(@class,'addressLine')]").findBy(matchText(addressLine)).parent();
    }

    private SelenideElement getBillingAddressCard(String addressLine)
    {
        return $$x("//li[contains(@id,'billAddr')]/ul/li[contains(@class,'addressLine')]").findBy(matchText(addressLine)).parent();
    }

    @Step("change shipping address with old address line {oldAddressLine}")
    public MyAddressesPage editShippingAddress(String oldAddressLine, String name, String company, String addresLine, String city, String state, String zip,
                                               String country)
    {
        getShippingAddressCard(oldAddressLine).parent().find(editShippingAddressButton).click();
        enterAddress(name, company, addresLine, city, state, zip, country);
        saveEditedShippingAddress.click();
        return this;
    }

    @Step("change billing address with old address line {oldAddressLine}")
    public MyAddressesPage editBillingAddress(String oldAddressLine, String name, String company, String addresLine, String city, String state, String zip,
                                              String country)
    {
        getBillingAddressCard(oldAddressLine).parent().find(editBillingAddressButton).click();
        enterAddress(name, company, addresLine, city, state, zip, country);
        saveEditedBillingAddress.click();
        return this;
    }

    @Step("change shipping address with old address line {oldAddressLine}")
    public MyAddressesPage editShippingAddress(String oldAddressLine, Address address)
    {
        return editShippingAddress(oldAddressLine, address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(),
                                   address.getState(), address.getZip(), address.getCountry());
    }

    @Step("change billing address with old address line {oldAddressLine}")
    public MyAddressesPage editBillingAddress(String oldAddressLine, Address address)
    {
        return editBillingAddress(oldAddressLine, address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(),
                                  address.getState(), address.getZip(), address.getCountry());
    }

    @Step("add new shipping address")
    public MyAddressesPage addNewShippingAddress(String name, String company, String addresLine, String city, String state, String zip, String country)
    {
        addNewShippingButton.click();

        enterAddress(name, company, addresLine, city, state, zip, country);
        confirmEnteredShippingAddress.click();
        return this;
    }

    @Step("add new billing address")
    public MyAddressesPage addNewBillingAddress(String name, String company, String addresLine, String city, String state, String zip, String country)
    {
        addNewBillingButton.click();

        enterAddress(name, company, addresLine, city, state, zip, country);
        confirmEnteredBillingAddress.click();
        return this;
    }

    @Step("delete shipping address with address line {addressLine}")
    public DeleteConfirmationPage deleteShippingAddress(String addressLine)
    {
        getShippingAddressCard(addressLine).parent().find(deleteShippingAddressButton).click();
        DeleteConfirmationPage deleteConfirmationPage = new DeleteConfirmationPage();
        deleteConfirmationPage.isExpectedPage();
        return deleteConfirmationPage;
    }

    @Step("delete billing address with address line {addressLine}")
    public DeleteConfirmationPage deleteBillingAddress(String addressLine)
    {
        getBillingAddressCard(addressLine).parent().find(deleteBillingAddressButton).click();
        DeleteConfirmationPage deleteConfirmationPage = new DeleteConfirmationPage();
        deleteConfirmationPage.isExpectedPage();
        return deleteConfirmationPage;
    }

    @Step("validate shipping address with address line {addressLine} doesn't exist")
    public MyAddressesPage validateShippingAddressDoesntExist(String addressLine)
    {
        getShippingAddressCard(addressLine).shouldNot(exist);
        return this;
    }

    @Step("validate billing address with address line {addressLine} doesn't exist")
    public MyAddressesPage validateBillingAddressDoesntExist(String addressLine)
    {
        getBillingAddressCard(addressLine).shouldNot(exist);
        return this;
    }
    // wrapper methods

    @Step("add new shipping page")
    public MyAddressesPage addNewShippingAddress(Address address)
    {
        return addNewShippingAddress(address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(), address.getState(),
                                     address.getZip(), address.getCountry());
    }

    @Step("add new billing page")
    public MyAddressesPage addNewBillingAddress(Address address)
    {
        return addNewBillingAddress(address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(), address.getState(),
                                    address.getZip(), address.getCountry());
    }

    public MyAddressesPage validateShippingAddress(Address address)
    {
        return validateShippingAddress(address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(), address.getState(),
                                       address.getZip(), address.getCountry());
    }

    public MyAddressesPage validateBillingAddress(Address address)
    {
        return validateBillingAddress(address.getFullName(), address.getCompany(), address.getAddressLine(), address.getCity(), address.getState(),
                                      address.getZip(), address.getCountry());
    }

    @Step("validate shipping address")
    public MyAddressesPage validateShippingAddress(String name, String company, String addressLine, String city, String state, String zip, String country)
    {
        return validateAddress(getShippingAddressCard(addressLine), name, company, addressLine, city, state, zip, country);
    }

    @Step("validate billing address")
    public MyAddressesPage validateBillingAddress(String name, String company, String addressLine, String city, String state, String zip, String country)
    {
        return validateAddress(getBillingAddressCard(addressLine), name, company, addressLine, city, state, zip, country);
    }

}
