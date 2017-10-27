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
public class PaymentPage extends AbstractCheckoutPage
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
        // Makes sure the headline is there and starts with a capital letter
        $("#titlePayment").should(matchText("[A-Z].{3,}"));
        // First credit card
        // Makes sure at least one credit card is saved
        $("#payment0").shouldBe(visible);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public boolean isExpectedPage()
    {
        return $("#titlePayment").exists();
    }

    /**
     * @param index
     *            The index of the credit card you want to select
     * @return PPlaceOrder
     */
    public PlaceOrderPlace selectCreditCard(int index)
    {
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#payment" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUsePayment").scrollTo().click();

        return page(PlaceOrderPlace.class);
    }
}
