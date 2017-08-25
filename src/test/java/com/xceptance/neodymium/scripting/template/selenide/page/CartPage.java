/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;

import com.xceptance.neodymium.scripting.template.selenide.utility.PriceHelper;

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

    public void validateCartItem(int index, String productName, String productStyle, String productSize, int productCount, String productPrice)
    {
        // Visibility
        // Makes sure a product at the specified index exists and is visible
        $("#product" + index).shouldBe(visible);
        // Name
        // Compares the displayed name with the parameter
        $("#product" + index + " .productName").shouldBe(exactText(productName));
        // Count
        // Compares the displayed amount with the parameter
        $("#product" + index + " .productCount").shouldBe(exactValue(Integer.toString(productCount)));
        // Style
        // Compares the displayed style with the parameter
        $("#product" + index + " .productStyle").shouldBe(exactText(productStyle));
        // Size
        // Compares the displayed style with the parameter
        $("#product" + index + " .productSize").shouldBe(exactText(productSize));
        // Price
        // Compares the displayed price with the parameter
        $("#product" + index + " .productUnitPrice").shouldBe(exactText(productPrice));
    }

    public void validateSubTotal(int index, String oldSubTotal, String currency, String productTotalPrice)
    {

        // Store unit price (without $ sign)
        // Takes the pricer per 1 unit of the specified item
        String unitPriceShort_varDynamic = $("#product" + index + " td .unitPriceShort").text();
        // Store product count
        // Takes the amount of the specified item
        String quantity_varDynamic = $("#productCount" + index).val();

        String subOrderPrice = PriceHelper.computeRowPrice(unitPriceShort_varDynamic, quantity_varDynamic);
        // add a "$" at the beginning
        String subOrderPrice_varDynamic = "$" + subOrderPrice;
        // Store the price that is actually shown for calculations
        // Stores the displayed Total Unit Price
        // Removes the first character from the string, which is the "$" symbol
        String shownUnitPrice = $("#product" + index + " .productTotalUnitPrice").text().substring(1);

        // Verify calculated cost is the shown cost
        // Compare calculated Unit Price to displayed total Unit Price
        $("#product" + index + " .productTotalUnitPrice").shouldBe(exactText(productTotalPrice));

        // Verify subtotal
        // Stores the subtotal with the new item present
        // Remove "$" symbol from price to be able to use it in a calculation
        // Cuts off the first character from the string, which is the "$" symbol
        String newSubTotal = $("#orderSubTotalValue").text().substring(1);
        // New Total - Old Total = Price of item you just added
        String price = PriceHelper.subtractFromPrice(newSubTotal, oldSubTotal);
        Assert.assertEquals(price, subOrderPrice);

        // TODO
        // Store subtotal
        // Removes the first character from the string, which is the "$" symbol
        String oldSubTotal2 = $("#orderSubTotalValue").text().substring(1);

    }
}
