/**
 * 
 */
package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.Address;

/**
 * @author pfotenhauer
 */
public class NewBillingAddressPage extends AbstractCheckoutPage
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
    @Step("ensure this is a new billing address page")
    public void isExpectedPage()
    {
        headline.should(exist);
    }

    @Override
    @Step("validate new billing address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Assert the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // Form
        // Asserts the form is there at all
        $("#formAddBillAddr").shouldBe(visible);
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
        // Continue Button
        // Asserts the Continue button is there
        addBillingButton.shouldBe(visible);
    }

    /**
     * //
     * 
     * @param name
     *            First and last name you want to use // T
     * @param company
     *            The company you want to use
     * @param address
     *            The address you want to use
     * @param city
     *            The City you want to use
     * @param state
     *            The state you want to use
     * @param zip
     *            The Zip you want to use, has to be in numbers format
     * @param country
     *            The country you want to use, currently only United States or Germany
     */
    @Step("fill and send new billing address form")
    public NewPaymentPage sendBillingAddressForm(String name, String company, String address, String city,
                                                 String state, String zip, String country)
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
        // Open the billing addresses or payment options page, depending on which radio button you checked
        // Click on Continue
        addBillingButton.scrollTo().click();

        return new NewPaymentPage();
    }

    /**
     * @param billingAddress
     * @return
     */
    public NewPaymentPage sendBillingAddressForm(Address billingAddress)
    {
        return sendBillingAddressForm(billingAddress.getFullName(), billingAddress.getCompany(), billingAddress.getAddressLine(),
                                      billingAddress.getCity(), billingAddress.getState(), billingAddress.getZip(),
                                      billingAddress.getCountry());
    }
}
