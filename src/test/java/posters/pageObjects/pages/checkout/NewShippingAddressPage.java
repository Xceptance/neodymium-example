/**
 * 
 */
package posters.pageObjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

import posters.dataObjects.Address;

/**
 * @author pfotenhauer
 */
public class NewShippingAddressPage extends AbstractCheckoutPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#validateStructure()
     */
    @Override
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Assert the headline is there and starts with a capital letter
        $("#titleDelAddr").should(matchText("[A-Z].{3,}"));
        // Form
        // Asserts the form is there at all
        $("#formAddDelAddr").shouldBe(visible);
        // Name
        // Asserts the label next to the name field shows the right text
        $("label[for=\"fullName\"]").shouldHave(exactText("Full name*"));
        // Asserts the name field is there
        $("#fullName").shouldBe(visible);
        // Company
        // Asserts the label next to the company field shows the right text
        $("label[for=\"company\"]").shouldHave(exactText("Company"));
        // Asserts the company field is there
        $("#company").shouldBe(visible);
        // Address
        // Asserts the label next to the address field shows the right text
        $("label[for=\"addressLine\"]").shouldHave(exactText("Address*"));
        // Asserts the address field is there
        $("#addressLine").shouldBe(visible);
        // City
        // Asserts the label next to the city field shows the right text
        $("label[for=\"city\"]").shouldHave(exactText("City*"));
        // Asserts the city field is there
        $("#city").shouldBe(visible);
        // State
        // Asserts the label next to the state field shows the right text
        $("label[for=\"state\"]").shouldHave(exactText("State/Province*"));
        // Asserts the state field is there
        $("#state").shouldBe(visible);
        // Zip
        // Asserts the label next to the zip field shows the right text
        $("label[for=\"zip\"]").shouldHave(exactText("ZIP/Postal code*"));
        // Asserts the zip field is there
        $("#zip").shouldBe(visible);
        // Country
        // Asserts the label next to the country field shows the right text
        $("label[for=\"country\"]").shouldHave(exactText("Country*"));
        // Asserts the country field is there
        $("#country").shouldBe(visible);
        // Radio Button
        // Assert the radio buttons are there
        $$(".col-sm-1 input[type=\"radio\"]").shouldHaveSize(2);
        // Continue Button
        // Asserts the Continue button is there
        $("#btnAddDelAddr").shouldBe(visible);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public void isExpectedPage()
    {
        $("#titleDelAddr").should(exist);
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
     * @param sameBillingAddress
     *            Decision whether or not use the same billing address
     */
    public NewBillingAddressPage sendShippingAddressForm(String name, String company, String address, String city,
                                                         String state, String zip, String country, boolean sameBillingAddress)
    {
        // Name
        // Enter the name parameter
        $("#fullName").val(name);
        // Company
        // Enter the company parameter
        $("#company").val(company);
        // Address
        // Enter the address parameter
        $("#addressLine").val(address);
        // City
        // Enter the city parameter
        $("#city").val(city);
        // State
        // Enter the state parameter
        $("#state").val(state);
        // Zip
        // Enter the zip parameter
        $("#zip").val(zip);
        // Country
        // Select the country whose label equals the parameter
        $("#country").selectOption(country);
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
        $("#btnAddDelAddr").scrollTo().click();

        return page(NewBillingAddressPage.class);
    }

    /**
     * @param shippingAddress
     * @param sameBillingAddress
     * @return
     */
    public NewBillingAddressPage sendShippingAddressForm(Address shippingAddress, boolean sameBillingAddress)
    {
        return sendShippingAddressForm(shippingAddress.getFullName(), shippingAddress.getCompany(), shippingAddress.getAddressLine(),
                                       shippingAddress.getCity(), shippingAddress.getState(), shippingAddress.getZip(),
                                       shippingAddress.getCountry(), sameBillingAddress);
    }
}
