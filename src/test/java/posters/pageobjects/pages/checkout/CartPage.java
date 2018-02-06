/**
 * 
 */
package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Context;

import posters.dataobjects.Product;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.pageobjects.utility.PriceHelper;

/**
 * @author pfotenhauer
 */
public class CartPage extends AbstractBrowsingPage
{
    private SelenideElement cartTable = $("#cartOverviewTable");

    private SelenideElement subTotal = $("#orderSubTotalValue");

    @Override
    public void isExpectedPage()
    {
        cartTable.should(exist);
    }

    @Override
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Makes sure the headline is there and starts with a capital letter
        $("#titleCart").shouldBe(matchText("[A-Z].{3,}"));
        // Assert there is an item list with at least one item present
        // Makes sure the list of cart items is there
        cartTable.shouldBe(visible);
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
        $("#orderShippingCosts").shouldHave(exactText(shippingCosts));
    }

    /**
     * @param shippingcosts
     */
    public void validate(String shippingCosts)
    {
        validateStructure();
        validateShippingCosts(shippingCosts);
    }

    /**
     * @param position
     * @param product
     */
    public void validateCartItem(int position, Product product)
    {
        validateCartItem(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), product.getUnitPrice());
    }

    /**
     * @param position
     * @param product
     * @param productAmount
     */
    public void validateCartItem(int position, Product product, int productAmount)
    {
        validateCartItem(position, product.getName(), product.getStyle(), product.getSize(), productAmount, product.getUnitPrice());
    }

    private void validateCartItem(int position, String productName, String productStyle, String productSize, int productAmount, String productPrice)
    {
        SelenideElement productContainer = $("#product" + (position - 1));
        // Visibility
        // Makes sure a product at the specified index exists and is visible
        productContainer.shouldBe(visible);
        // Name
        // Compares the displayed name with the parameter
        productContainer.find(".productName").shouldHave(exactText(productName));
        // Count
        // Compares the displayed amount with the parameter
        validateProductAmount(position, productAmount);
        // Style
        // Compares the displayed style with the parameter
        productContainer.find(".productStyle").shouldHave(exactText(productStyle));
        // Size
        // Compares the displayed style with the parameter
        productContainer.find(".productSize").shouldHave(exactText(productSize));
        // Price
        // Compares the displayed price with the parameter
        productContainer.find(".productUnitPrice").shouldHave(exactText(productPrice));
    }

    public void validateSubAndLineItemTotalAfterAdd(int position, String oldSubTotal, String oldLineItemTotal)
    {
        SelenideElement productContainer = $("#product" + (position - 1));
        // Store unit price (without $ sign)
        // Takes the pricer per 1 unit of the specified item
        String unitPriceShort_varDynamic = productContainer.find(".unitPriceShort").text();
        // Store product count
        // Takes the amount of the specified item
        String quantity_varDynamic = $("#productCount" + (position - 1)).val();

        String subOrderPrice = PriceHelper.computeRowPrice(PriceHelper.addCurrency(unitPriceShort_varDynamic), quantity_varDynamic);

        // Verify calculated cost is the shown cost
        // Compare calculated Unit Price to displayed total Unit Price
        productContainer.find(".productTotalUnitPrice").shouldHave(exactText(subOrderPrice));

        // Verify subtotal
        // Stores the subtotal with the new item present
        // Remove "$" symbol from price to be able to use it in a calculation
        // Cuts off the first character from the string, which is the "$" symbol
        String newSubTotal = subTotal.text();
        // New Total - Old Total = Price of item you just added
        String price = PriceHelper.subtractFromPrice(newSubTotal, oldSubTotal);
        String price2 = PriceHelper.subtractFromPrice(subOrderPrice, oldLineItemTotal);

        Assert.assertEquals(price, price2);
    }

    public void validateProductAmount(int position, int amount)
    {
        // Makes sure the amount of the item with index @{index} in the cart equals the parameter
        $("#product" + (position - 1) + " .productCount").shouldHave(exactValue(Integer.toString(amount)));
    }

    public String getProductName(int position)
    {
        // Get the product name to enable usage outside this module.
        return $("#product" + (position - 1) + " .productName").text();
    }

    public String getProductStyle(int position)
    {
        // Get the style to enable usage outside this module.
        return $("#product" + (position - 1) + " .productStyle").text();
    }

    public String getProductSize(int position)
    {
        // Get the size to enable usage outside this module.
        return $("#product" + (position - 1) + " .productSize").text();
    }

    public String getProductCount(int position)
    {
        // Get the size to enable usage outside this module.
        return $("#product" + (position - 1) + " .productCount").val();
    }

    public String getProductUnitPrice(int position)
    {
        // Get the product price to enable usage outside this module.
        return $("#product" + (position - 1) + " .productUnitPrice").text();
    }

    public String getProductTotalUnitPrice(int position)
    {
        // Get the product price to enable usage outside this module.
        return $("#product" + (position - 1) + " .productTotalUnitPrice").text();
    }

    public Product getProduct(int position)
    {
        return new Product(getProductName(position), getProductUnitPrice(position), getProductTotalUnitPrice(position), getProductStyle(position), getProductSize(position), Integer.parseInt(getProductCount(position)));
    };

    /**
     * @param position
     * @param amount
     */
    public void updateProductCount(int position, int amount)
    {
        SelenideElement productContainer = $("#product" + (position - 1));
        // Type in the specified amount
        productContainer.find(".productCount").setValue(Integer.toString(amount));
        // Stores the new amount in an outside variable
        // Click the update button
        // Clicks the update button for the product
        productContainer.find(".btnUpdateProduct").scrollTo().click();
    }

    /**
     * @param position
     */
    public void removeProduct(int position)
    {
        // Click delete button
        // Click on the delete button for the product
        $("#btnRemoveProdCount" + (position - 1)).scrollTo().click();
        // Wait for the second delete button to appear
        // Wait until the confirmation button is visible
        $("#buttonDelete").waitUntil(visible, Context.get().configuration.timeout());
        // Click delete button
        // Click the confirmation button
        $("#buttonDelete").scrollTo().click();
        // Wait until the confirmation button is gone
        $("#buttonDelete").waitUntil(hidden, Context.get().configuration.timeout());
        // Reload page to let IDs adjust to the deletion
        miniCart.openMiniCart();
        miniCart.openCartPage();
    }

    /**
     * @param oldSubTotal
     * @param oldLineItemTotal
     */
    public void validateSubAndLineItemTotalAfterRemove(String oldSubTotal, String oldLineItemTotal)
    {
        String newSubTotal = PriceHelper.subtractFromPrice(oldSubTotal, oldLineItemTotal);
        subTotal.shouldHave(exactText(newSubTotal));
    }

    /**
     * @param index
     */
    public ProductdetailPage openProductPage(int position)
    {
        $("#product" + (position - 1) + " img").scrollTo().click();
        return new ProductdetailPage();
    }

    private void clickCheckoutButton()
    {
        $("#btnStartCheckout").scrollTo().click();
    }

    /**
     * 
     */
    public NewShippingAddressPage openNewShippingPage()
    {
        clickCheckoutButton();
        return new NewShippingAddressPage();
    }

    /**
     * 
     */
    public ShippingAddressPage openShippingPage()
    {
        clickCheckoutButton();
        return new ShippingAddressPage();
    }

    /**
     * @return
     */
    public boolean hasProductsInCart()
    {
        return $("#btnRemoveProdCount0").exists();
    }
}