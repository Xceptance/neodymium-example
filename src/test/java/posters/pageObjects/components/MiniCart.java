/**
 * 
 */
package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import posters.dataObjects.Product;
import posters.pageObjects.pages.checkout.CartPage;
import posters.settings.Settings;

/**
 * @author pfotenhauer
 */
public class MiniCart extends AbstractComponent
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.component.BasicComponent#isComponentAvailable()
     */
    public void isComponentAvailable()
    {
        $("#btnCartOverviewForm").should(exist);
    }

    public void openMiniCart()
    {
        // Click the mini cart icon
        $("#headerCartOverview").scrollTo().click();
        // Wait for mini cart to appear
        // Wait for the mini cart to show
        $("#miniCartMenu").waitUntil(visible, Settings.timeout);
    }

    public void closeMiniCart()
    {
        // Click the mini cart icon again
        $("#headerCartOverview").scrollTo().click();
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
        // Close mini cart
        closeMiniCart();

        return subtotal;
    }

    public int getTotalCount()
    {
        return Integer.parseInt($("#btnCartOverviewForm .headerCartProductCount").text());
    }

    public CartPage openCartPage()
    {
        // Open the cart
        // Click on the button to go to the Cart
        $("#miniCartMenu .goToCart").scrollTo().click();
        return page(CartPage.class);
    }

    public void validateTotalCount(int totalCount)
    {
        $("#btnCartOverviewForm .headerCartProductCount").shouldHave(exactText(Integer.toString(totalCount)));
    }

    public void validateSubtotal(String subtotal)
    {
        // Verify the mini cart shows the specified subtotal
        // Open mini cart
        openMiniCart();
        // Verify subtotal equals specified subtotal
        // Compare the subTotal to the parameter
        $("#miniCartMenu .subOrderPrice").shouldHave(exactText(subtotal));
        // Close Mini Cart
        closeMiniCart();
    }

    /**
     * @param position
     * @param product
     */
    public void validateMiniCart(int position, Product product)
    {
        validateMiniCart(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), product.getTotalUnitPrice());
    }

    /**
     * @param position
     * @param product
     * @param productAmount
     * @param productTotalPrice
     */
    public void validateMiniCart(int position, Product product, int productAmount, String productTotalPrice)
    {
        validateMiniCart(position, product.getName(), product.getStyle(), product.getSize(), productAmount, productTotalPrice);
    }

    private void validateMiniCart(int position, String productName, String productStyle, String productSize, int productCount, String prodTotalPrice)
    {
        // Open the mini cart
        openMiniCart();
        // Validate data of specified item
        // Product Name
        // Compares the name of the cart item at position @{position} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + position + ") ul.cartItems .prodName").shouldHave(exactText(productName));
        // Product Style
        // Compares the style of the cart item at position @{position} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + position + ") ul.cartItems .prodStyle").shouldHave(exactText(productStyle));
        // Product Size
        // Compares the style of the cart item at position @{position} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + position + ") ul.cartItems .prodSize").shouldHave(exactText(productSize));
        // Amount
        // Compares the amount of the cart item at position @{position} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + position + ") ul.cartItems .prodCount").shouldHave(exactText(Integer.toString(productCount)));
        // Price
        // Compares the price of the cart item at position @{position} to the parameter
        $("ul.cartMiniElementList li:nth-child(" + position + ") ul.cartItems .prodPrice").shouldHave(exactText(prodTotalPrice));
        // Close mini cart
        closeMiniCart();
    }

}
