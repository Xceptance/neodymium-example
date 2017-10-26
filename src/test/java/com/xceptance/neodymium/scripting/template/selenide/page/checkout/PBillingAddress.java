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
public class PBillingAddress extends CheckoutPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public boolean isExpectedPage()
    {
        return $("#titleBillAddr").exists();
    }

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
        $("#titleBillAddr").should(matchText("[A-Z].{3,}"));
        // First address
        // Makes sure at least one address is visible
        $("#billAddr0").shouldBe(visible);
    }

    /**
     * @param index
     *            The index of the billing address you want to choose
     * @return PPayment
     */
    public PPayment selectBillingAddress(int index)
    {
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#billAddr" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseBillAddress").scrollTo().click();

        return page(PPayment.class);
    }
}
