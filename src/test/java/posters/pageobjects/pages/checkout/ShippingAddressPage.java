/**
 * 
 */
package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.AddressContainer;
import posters.dataobjects.ShippingAddress;

/**
 * @author pfotenhauer
 */
public class ShippingAddressPage extends AbstractEnterAddressPage
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
    @Step("ensure this is a new shipping address page")
    public ShippingAddressPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Override
    @Step("validate new shipping address page structure")
    public ShippingAddressPage validateStructure()
    {
        super.validateStructure();

        // Headline
        // Assert the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // Form
        // Asserts the form is there at all
        $("#formAddDelAddr").shouldBe(visible);
        // Name
        // Asserts the label next to the name field shows the right text
        $("label[for=\"fullName\"]").shouldHave(exactText(Neodymium.localizedText("General.addresses.fullname")));
        // Asserts the name field is there
        nameField.shouldBe(visible);
        // Company
        // Asserts the label next to the company field shows the right text
        $("label[for=\"company\"]").shouldHave(exactText(Neodymium.localizedText("General.addresses.company")));
        // Asserts the company field is there
        companyField.shouldBe(visible);
        // Address
        // Asserts the label next to the address field shows the right text
        $("label[for=\"addressLine\"]").shouldHave(exactText(Neodymium.localizedText("General.addresses.address")));
        // Asserts the address field is there
        addressField.shouldBe(visible);
        // City
        // Asserts the label next to the city field shows the right text
        $("label[for=\"city\"]").shouldHave(exactText(Neodymium.localizedText("General.addresses.city")));
        // Asserts the city field is there
        cityField.shouldBe(visible);
        // State
        // Asserts the label next to the state field shows the right text
        $("label[for=\"state\"]").shouldHave(exactText(Neodymium.localizedText("General.addresses.state")));
        // Asserts the state field is there
        stateField.shouldBe(visible);
        // Zip
        // Asserts the label next to the zip field shows the right text
        $("label[for=\"zip\"]").shouldHave(exactText(Neodymium.localizedText("General.addresses.zip")));
        // Asserts the zip field is there
        zipField.shouldBe(visible);
        // Country
        // Asserts the label next to the country field shows the right text
        $("label[for=\"country\"]").shouldHave(exactText(Neodymium.localizedText("General.addresses.country")));
        // Asserts the country field is there
        countryField.shouldBe(visible);
        // Radio Button
        // Assert the radio buttons are there
        $$("input[name=\"billEqualShipp\"]").shouldHaveSize(2);
        // Continue Button
        // Asserts the Continue button is there
        addShippingButton.shouldBe(visible);
        return this;
    }

    @Step("enter shipping address")
    public ShippingAddressPage fillForm(String name, String company, String address, String city,
                                        String state, String zip, String country, boolean sameBillingAddress)
    {
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

        return this;
    }

    @Step("validate entered shipping address")
    public ShippingAddressPage validateEnteredData(String name, String company, String address, String city,
                                                   String state, String zip, String country, boolean sameBillingAddress)
    {
        // Name
        // Enter the name parameter
        nameField.shouldHave(value(name));
        // Company
        // Enter the company parameter
        companyField.shouldHave(value(company));
        // Address
        // Enter the address parameter
        addressField.shouldHave(value(address));
        // City
        // Enter the city parameter
        cityField.shouldHave(value(city));
        // State
        // Enter the state parameter
        stateField.shouldHave(value(state));
        // Zip
        // Enter the zip parameter
        zipField.shouldHave(value(zip));
        // Country
        // Select the country whose label equals the parameter
        countryField.getSelectedOption().shouldHave(value(country));
        // Radio Button
        // Click the radio button for Yes or No
        if (sameBillingAddress)
        {
            $("#billEqualShipp-Yes[checked='checked']").shouldBe(visible);
        }
        else
        {
            $("#billEqualShipp-No[checked='checked']").shouldBe(visible);
        }
        return this;
    }

    @Override
    @Step("enter shipping address")
    public ShippingAddressPage fillForm(AddressContainer address)
    {
        ShippingAddress shippingAddress = (ShippingAddress) address;
        return fillForm(shippingAddress.getFullName(), shippingAddress.getCompany(), shippingAddress.getAddressLine(), shippingAddress.getCity(),
                        shippingAddress.getState(), shippingAddress.getZip(), shippingAddress.getCountry(), shippingAddress.isUseForBilling());
    }

    @Override
    @Step("send correct shipping address")
    public BillingAddressPage sendCorrectForm()
    {
        addShippingButton.click();
        return new BillingAddressPage();
    }

    @Override
    @Step("send incorrect shipping address")
    public ShippingAddressPage sendIncorrectForm()
    {
        addShippingButton.click();
        return this;
    }

    @Override
    @Step("validate entered shipping address")
    public ShippingAddressPage validateEnteredData(AddressContainer address)
    {
        ShippingAddress shippingAddress = (ShippingAddress) address;
        return validateEnteredData(shippingAddress.getFullName(), shippingAddress.getCompany(), shippingAddress.getAddressLine(),
                                   shippingAddress.getCity(), shippingAddress.getState(), shippingAddress.getZip(), shippingAddress.getCountry(),
                                   shippingAddress.isUseForBilling());
    }
}
