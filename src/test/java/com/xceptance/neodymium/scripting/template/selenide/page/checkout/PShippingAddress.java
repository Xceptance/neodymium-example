/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page.checkout;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * @author pfotenhauer
 */
public class PShippingAddress extends CheckoutPage
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
        // Assert the headline is there and starts with a capital letter
        $("#titleDelAddr").should(matchText("[A-Z].{3,}"));
        // First address
        // Makes sure at least one address is visible
        $("#delAddr0").shouldBe(visible);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public boolean isExpectedPage()
    {
        return $("#titleDelAddr").exists();
    }

    /**
     * @param index
     *            Index of the shipping address
     * @return PBillingAddress
     */
    public PBillingAddress selectShippingAddress(int index)
    {
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#delAddr" + index + " input").click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseAddressContinue").click();

        return page(PBillingAddress.class);
    }
}
