/**
 * 
 */
package posters.pageObjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import posters.dataObjects.Address;
import posters.dataObjects.CreditCard;
import posters.pageObjects.pages.browsing.HomePage;

/**
 * @author pfotenhauer
 */
public class PlaceOrderPlace extends AbstractCheckoutPage
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
        // Headline is there and starts with a capital letter
        $("#titleOrderOverview").should(matchText("[A-Z].{3,}"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public void isExpectedPage()
    {
        $("#titleOrderOverview").should(exist);
    }

    /**
     * @param position
     *            The position of the product you want to check
     * @param productName
     *            The Product name of that product
     * @param productCount
     *            The amount
     * @param productStyle
     *            The style
     * @param productSize
     *            The size
     */
    public void validateProduct(int position, String productName, int productCount, String productStyle, String productSize)
    {
        final int index = position - 1;
        // Item info evaluation
        // The product at index @{index} exists
        $("#checkoutOverviewTable tr#product" + index).should(exist);
        // Name
        // The name equals the parameter
        $("tr#product" + index + " .pName").shouldHave(exactText(productName));
        // Amount
        // The amount equals the parameter
        $("tr#product" + index + " td.pCount").shouldHave(exactText(Integer.toString(productCount)));
        // Style
        // The style equals the parameter
        $("tr#product" + index + " .pStyle").shouldHave(exactText(productStyle));
        // Size
        // The size equals the parameter
        $("tr#product" + index + " .pSize").shouldHave(exactText(productSize));
    }

    public void validateAddressAndPayment(Address shippingAddress, Address billingAddress, CreditCard creditcard)
    {
        // Shipping address
        // Name
        // Makes sure the shipping address name matches the parameter
        $("#shippingAddr .name").shouldHave(exactText(shippingAddress.getFullName()));
        // Company
        // Makes sure the shipping address company matches the parameter
        $("#shippingAddr .company").shouldHave(exactText(shippingAddress.getCompany()));
        // Address
        // Makes sure the shipping address matches the parameter
        $("#shippingAddr .addressLine").shouldHave(exactText(shippingAddress.getAddressLine()));
        // City
        // Makes sure the shipping address city matches the parameter
        $("#shippingAddr .city").shouldHave(exactText(shippingAddress.getCity()));
        // State
        // Makes sure the shipping address state matches the parameter
        $("#shippingAddr .state").shouldHave(exactText(shippingAddress.getState()));
        // ZIP
        // Makes sure the shipping address ZIP matches the parameter
        $("#shippingAddr .zip").shouldHave(exactText(" " + shippingAddress.getZip()));
        // Country
        // Makes sure the shipping address country matches the parameter
        $("#shippingAddr .country").shouldHave(exactText(shippingAddress.getCountry()));
        // Billing address
        // Name
        // Makes sure the billing address name matches the parameter
        $("#billingAddr .name").shouldHave(exactText(billingAddress.getFullName()));
        // Company
        // Makes sure the billing address company matches the parameter
        $("#billingAddr .company").shouldHave(exactText(billingAddress.getCompany()));
        // Address
        // Makes sure the billing address matches the parameter
        $("#billingAddr .addressLine").shouldHave(exactText(billingAddress.getAddressLine()));
        // City
        // Makes sure the billing address city matches the parameter
        $("#billingAddr .city").shouldHave(exactText(billingAddress.getCity()));
        // State
        // Makes sure the billing address state matches the parameter
        $("#billingAddr .state").shouldHave(exactText(billingAddress.getState()));
        // ZIP
        // Makes sure the billing address ZIP matches the parameter
        $("#billingAddr .zip").shouldHave(exactText(billingAddress.getZip()));
        // Country
        // Makes sure the billing address country matches the parameter
        $("#billingAddr .country").shouldHave(exactText(billingAddress.getCountry()));
        // Payment
        // Name
        // Makes sure the credit card holder matches the parameter
        $("#payment .name .value").shouldHave(exactText(creditcard.getFullName()));
        // Credit Card Number
        // Makes sure the anonymized credit card number matches the parameter
        $("#payment .cardNumber .value").shouldHave(exactText(creditcard.getCrypticCardNumber()));
        // Expiration
        // Makes sure the credit card expiration month matches the parameter
        $("#payment .exp .month").shouldHave(exactText(creditcard.getExpDateMonth()));
        // Makes sure the credit card expiration year matches the parameter
        $("#payment .exp .year").shouldHave(exactText(creditcard.getExpDateYear()));
    }

    public String getTotalCosts()
    {
        return $("#totalCosts").text();
    }

    public HomePage placeOrder()
    {
        // Opens the homepage
        // Clicks the Order button
        $("#btnOrder").scrollTo().click();

        return page(HomePage.class);
    }
}
