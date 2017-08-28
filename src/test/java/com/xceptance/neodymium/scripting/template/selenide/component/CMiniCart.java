/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.xceptance.neodymium.scripting.template.selenide.page.PCart;
import com.xceptance.neodymium.scripting.template.selenide.utility.Settings;

/**
 * @author pfotenhauer
 */
public class CMiniCart extends BasicComponent
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.component.BasicComponent#isComponentAvailable()
     */
    @Override
    protected boolean isComponentAvailable()
    {
        return $("#btnCartOverviewForm").exists();
    }

    private void openMiniCart()
    {
        // Click the mini cart icon
        $("#headerCartOverview").click();
        // Wait for mini cart to appear
        // Wait for the mini cart to show
        $("#miniCartMenu").waitUntil(visible, Settings.timeout);
    }

    private void closeMiniCart()
    {
        // Click the mini cart icon again
        $("#headerCartOverview").click();
        // Move the mouse out of the area
        $("a#brand").hover();
        // Wait for mini cart to disappear
        // Wait for the mini cart to disappear
        $("#miniCartMenu").waitUntil(not(visible), Settings.timeout);
    }

    public String getSubtotal()
    {

        // Store the mini cart subtotal
        // Open mini cart
        openMiniCart();

        // Store subtotal in oldSubTotal
        String subtotal = $("#miniCartMenu .subOrderPrice").text();
        // Remove the $ sign off the price
        // Removes the first character from the string, which is the "$" symbol
        subtotal = subtotal.substring(1);
        // Close mini cart
        closeMiniCart();

        return subtotal;
    }

    public int getTotalCount()
    {
        return Integer.parseInt($("#btnCartOverviewForm .headerCartProductCount").text());
    }

    public PCart openCartPage()
    {
        // Open the cart
        // Click on the button to go to the Cart
        $("#miniCartMenu .goToCart").click();
        return page(PCart.class);
    }

    public void validateTotalCount(int totalCount)
    {
        $("#btnCartOverviewForm .headerCartProductCount").shouldBe(exactText(Integer.toString(totalCount)));
    }

    public void validateSubtotal(String subtotal)
    {
        // Verify the mini cart shows the specified subtotal
        // Open mini cart
        openMiniCart();
        // Verify subtotal equals specified subtotal
        // Compare the subTotal to the parameter
        $("#miniCartMenu .subOrderPrice").should(exactText(subtotal));
        // Close Mini Cart
        closeMiniCart();
    }

    public void validateMiniCart(int index, String productName, String productStyle, String productSize, int productCount, String prodTotalPrice)
    {
        // Open the mini cart
        openMiniCart();
        // Validate data of specified item
        // Product Name
        // Compares the name of the cart item at index @{index} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + index + ") ul.cartItems .prodName").shouldHave(exactText(productName));
        // Product Style
        // Compares the style of the cart item at index @{index} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + index + ") ul.cartItems .prodStyle").shouldHave(exactText(productStyle));
        // Product Size
        // Compares the style of the cart item at index @{index} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + index + ") ul.cartItems .prodSize").shouldHave(exactText(productSize));
        // Amount
        // Compares the amount of the cart item at index @{index} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + index + ") ul.cartItems .prodCount").shouldHave(exactText(Integer.toString(productCount)));
        // Price
        // Compares the price of the cart item at index @{index} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + index + ") ul.cartItems .prodPrice").shouldHave(exactText(prodTotalPrice));
        // Close mini cart
        closeMiniCart();
    }
}
