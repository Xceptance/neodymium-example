/**
 * 
 */
package posters.pageObjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import org.junit.Assert;

import posters.dataObjects.Product;
import posters.pageObjects.pages.browsing.AbstractBrowsingPage;
import posters.pageObjects.pages.browsing.ProductdetailPage;
import posters.pageObjects.utility.PriceHelper;
import posters.settings.Settings;

/**
 * @author pfotenhauer
 */
public class CartPage extends AbstractBrowsingPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#isAwaitedPage()
     */
    @Override
    public void isExpectedPage()
    {
        $("#cartOverviewTable").should(exist);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.BasicPage#validate()
     */
    @Override
    public void validateStructure()
    {
        super.validateStructure();

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
        final int index = position - 1;
        // Visibility
        // Makes sure a product at the specified index exists and is visible
        $("#product" + (index)).shouldBe(visible);
        // Name
        // Compares the displayed name with the parameter
        $("#product" + index + " .productName").shouldHave(exactText(productName));
        // Count
        // Compares the displayed amount with the parameter
        validateProductAmount(position, productAmount);
        // Style
        // Compares the displayed style with the parameter
        $("#product" + index + " .productStyle").shouldHave(exactText(productStyle));
        // Size
        // Compares the displayed style with the parameter
        $("#product" + index + " .productSize").shouldHave(exactText(productSize));
        // Price
        // Compares the displayed price with the parameter
        $("#product" + index + " .productUnitPrice").shouldHave(exactText(productPrice));
    }

    public void validateSubAndLineItemTotalAfterAdd(int position, String oldSubTotal, String oldLineItemTotal)
    {
        final int index = position - 1;
        // Store unit price (without $ sign)
        // Takes the pricer per 1 unit of the specified item
        String unitPriceShort_varDynamic = $("#product" + index + " td .unitPriceShort").text();
        // Store product count
        // Takes the amount of the specified item
        String quantity_varDynamic = $("#productCount" + index).val();

        String subOrderPrice = PriceHelper.computeRowPrice(PriceHelper.addCurrency(unitPriceShort_varDynamic), quantity_varDynamic);

        // Verify calculated cost is the shown cost
        // Compare calculated Unit Price to displayed total Unit Price
        $("#product" + index + " .productTotalUnitPrice").shouldHave(exactText(subOrderPrice));

        // Verify subtotal
        // Stores the subtotal with the new item present
        // Remove "$" symbol from price to be able to use it in a calculation
        // Cuts off the first character from the string, which is the "$" symbol
        String newSubTotal = $("#orderSubTotalValue").text();
        // New Total - Old Total = Price of item you just added
        String price = PriceHelper.subtractFromPrice(newSubTotal, oldSubTotal);
        String price2 = PriceHelper.subtractFromPrice(subOrderPrice, oldLineItemTotal);

        Assert.assertEquals(price, price2);
    }

    public void validateProductAmount(int position, int amount)
    {
        final int index = position - 1;
        // Makes sure the amount of the item with index @{index} in the cart equals the parameter
        $("#product" + index + " .productCount").shouldHave(exactValue(Integer.toString(amount)));
    }

    public String getProductName(int position)
    {
        final int index = position - 1;
        // Get the product name to enable usage outside this module.
        return $("#product" + index + " .productName").text();
    }

    public String getProductStyle(int position)
    {
        final int index = position - 1;
        // Get the style to enable usage outside this module.
        return $("#product" + index + " .productStyle").text();
    }

    public String getProductSize(int position)
    {
        final int index = position - 1;
        // Get the size to enable usage outside this module.
        return $("#product" + index + " .productSize").text();
    }

    public String getProductCount(int position)
    {
        final int index = position - 1;
        // Get the size to enable usage outside this module.
        return $("#product" + index + " .productCount").val();
    }

    public String getProductUnitPrice(int position)
    {
        final int index = position - 1;
        // Get the product price to enable usage outside this module.
        return $("#product" + index + " .productUnitPrice").text();
    }

    public String getProductTotalUnitPrice(int position)
    {
        final int index = position - 1;
        // Get the product price to enable usage outside this module.
        return $("#product" + index + " .productTotalUnitPrice").text();
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
        final int index = position - 1;
        // Type in the specified amount
        $("#product" + index + " .productCount").setValue(Integer.toString(amount));
        // Stores the new amount in an outside variable
        // Click the update button
        // Clicks the update button for the product
        $("#product" + index + " .btnUpdateProduct").scrollTo().click();
    }

    /**
     * @param position
     */
    public void removeProduct(int position)
    {
        final int index = position - 1;
        // Click delete button
        // Click on the delete button for the product
        $("#btnRemoveProdCount" + index).scrollTo().click();
        // Wait for the second delete button to appear
        // Wait until the confirmation button is visible
        $("#buttonDelete").waitUntil(visible, Settings.timeout);
        // Click delete button
        // Click the confirmation button
        $("#buttonDelete").scrollTo().click();
        // Wait until the confirmation button is gone
        $("#buttonDelete").waitUntil(hidden, Settings.timeout);
        // Reload page to let IDs adjust to the deletion
        miniCart().openMiniCart();
        miniCart().openCartPage();
    }

    /**
     * @param oldSubTotal
     * @param oldLineItemTotal
     */
    public void validateSubAndLineItemTotalAfterRemove(String oldSubTotal, String oldLineItemTotal)
    {
        String newSubTotal = PriceHelper.subtractFromPrice(oldSubTotal, oldLineItemTotal);
        $("#orderSubTotalValue").shouldHave(exactText(newSubTotal));
    }

    /**
     * @param index
     */
    public ProductdetailPage openProductPage(int position)
    {
        final int index = position - 1;
        $("#product" + index + " img").scrollTo().click();
        return page(ProductdetailPage.class);
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
        return page(NewShippingAddressPage.class);
    }

    /**
     * 
     */
    public ShippingAddressPage openShippingPage()
    {
        clickCheckoutButton();
        return page(ShippingAddressPage.class);
    }

    /**
     * @return
     */
    public boolean hasProductsInCart()
    {
        return $("#btnRemoveProdCount0").exists();
    }
}
