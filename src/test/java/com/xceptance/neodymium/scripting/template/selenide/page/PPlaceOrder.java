/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.objects.Address;
import com.xceptance.neodymium.scripting.template.selenide.objects.CreditCard;

/**
 * @author pfotenhauer
 */
public class PPlaceOrder extends CheckoutPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#validateStructure()
     */
    @Override
    public void validateStructure()
    {
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
    public boolean isExpectedPage()
    {
        return $("#titleOrderOverview").exists();
    }

    /**
     * @param index
     *            The index of the product you want to check
     * @param productName
     *            The Product name of that product
     * @param productCount
     *            The amount
     * @param productFinish
     *            The style
     * @param productSize
     *            The size
     */
    public void validateProduct(int index, String productName, int productCount, String productStyle, String productSize)
    {
        // Item info evaluation
        // The product at index @{index} exists
        $("#checkoutOverviewTable tr#product" + index).should(exist);
        // Name
        // The name equals the parameter
        $("tr#product" + index + " .pName").should(exactText(productName));
        // Amount
        // The amount equals the parameter
        $("tr#product" + index + " td.pCount").should(exactText(Integer.toString(productCount)));
        // Style
        // The style equals the parameter
        $("tr#product" + index + " .pStyle").should(exactText(productStyle));
        // Size
        // The size equals the parameter
        $("tr#product" + index + " .pSize").should(exactText(productSize));
    }

    public void validateAddressAndPayment(Address shippingAddress, Address billingAddress, CreditCard creditcard)
    {
        // Shipping address
        // Name
        // Makes sure the shipping address name matches the parameter
        $("#shippingAddr .name").should(exactText(shippingAddress.getFullName()));
        // Company
        // Makes sure the shipping address company matches the parameter
        $("#shippingAddr .company").should(exactText(shippingAddress.getCompany()));
        // Address
        // Makes sure the shipping address matches the parameter
        $("#shippingAddr .addressLine").should(exactText(shippingAddress.getAddressLine()));
        // City
        // Makes sure the shipping address city matches the parameter
        $("#shippingAddr .city").should(exactText(shippingAddress.getCity()));
        // State
        // Makes sure the shipping address state matches the parameter
        $("#shippingAddr .state").should(exactText(shippingAddress.getState()));
        // ZIP
        // Makes sure the shipping address ZIP matches the parameter
        $("#shippingAddr .zip").should(exactText(" " + shippingAddress.getZip()));
        // Country
        // Makes sure the shipping address country matches the parameter
        $("#shippingAddr .country").should(exactText(shippingAddress.getCountry()));
        // Billing address
        // Name
        // Makes sure the billing address name matches the parameter
        $("#billingAddr .name").should(exactText(billingAddress.getFullName()));
        // Company
        // Makes sure the billing address company matches the parameter
        $("#billingAddr .company").should(exactText(billingAddress.getCompany()));
        // Address
        // Makes sure the billing address matches the parameter
        $("#billingAddr .addressLine").should(exactText(billingAddress.getAddressLine()));
        // City
        // Makes sure the billing address city matches the parameter
        $("#billingAddr .city").should(exactText(billingAddress.getCity()));
        // State
        // Makes sure the billing address state matches the parameter
        $("#billingAddr .state").should(exactText(billingAddress.getState()));
        // ZIP
        // Makes sure the billing address ZIP matches the parameter
        $("#billingAddr .zip").should(exactText(billingAddress.getZip()));
        // Country
        // Makes sure the billing address country matches the parameter
        $("#billingAddr .country").should(exactText(billingAddress.getCountry()));
        // Payment
        // Name
        // Makes sure the credit card holder matches the parameter
        $("#payment .name .value").should(exactText(creditcard.getFullName()));
        // Credit Card Number
        // Makes sure the anonymized credit card number matches the parameter
        $("#payment .cardNumber .value").should(exactText(creditcard.getCrypticCardNumber()));
        // Expiration
        // Makes sure the credit card expiration month matches the parameter
        $("#payment .exp .month").should(exactText(creditcard.getExpDateMonth()));
        // Makes sure the credit card expiration year matches the parameter
        $("#payment .exp .year").should(exactText(creditcard.getExpDateYear()));
    }

    public String getTotalCosts()
    {
        return $("#totalCosts").text();
    }

    public PHome placeOrder()
    {
        // Opens the homepage
        // Clicks the Order button
        $("#btnOrder").click();

        return page(PHome.class);
    }
}
