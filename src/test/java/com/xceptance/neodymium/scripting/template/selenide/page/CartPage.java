/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;

/**
 * @author pfotenhauer
 */
public class CartPage extends BasicPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#isAwaitedPage()
     */
    @Override
    protected boolean isAwaitedPage()
    {
        return $("#cartOverviewTable").exists();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#validate()
     */
    @Override
    public void validate()
    {
        // Headline
        // Makes sure the headline is there and starts with a capital letter
        $("#titleCart").shouldBe(matchText("[A-Z].{3,}"));
        // Assert there is an item list with at least one item present
        // Makes sure the list of cart items is there
        $("#cartOverviewTable").shouldBe(visible);
        // Makes sure there's at least one product in the item list
        $("#product0").shouldBe(visible);
        // Assert the cart summary is visible
        // Makes sure the price breakdown list is there
        $("#cartSummaryList").shouldBe(visible);
        // Checkout Button
        // Makes sure the checkout button is there.
        $("#btnStartCheckout").should(exist);
    }

    public void validateShippingCosts(String shippingCosts)
    {
        // Assert the correct shipping price is shown
        String currentShippingCosts = $("#orderShippingCosts").text();
        // Removes the first character off the stored shipping costs and compares it to the global shipping costs
        // variable to make sure the displayed shipping costs are correct
        Assert.assertEquals(currentShippingCosts.substring(1), shippingCosts);
    }

}
