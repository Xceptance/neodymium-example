package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.junit.Assert;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.Product;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.ProductDetailPage;
import posters.pageobjects.utility.PriceHelper;

public class CartPage extends AbstractBrowsingPage
{
    private SelenideElement cartTable = $("#cartOverviewTable");

    private SelenideElement subTotal = $("#orderSubTotalValue");

    @Override
    @Step("ensure this is a cart page")
    public CartPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#titleCart").shouldBe(visible);
        return this;
    }
    
    // ----- validate content cart page ----- //
    
    @Override
    @Step("validate cart page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title 
        $("#titleCart").shouldBe(matchText("[A-Z].{3,}"));
        
        // validate product list
        cartTable.shouldBe(visible);
        
        // validate at least 1 product in product list
        $("#product0").shouldBe(visible);
        
        // validate cart summary list
        $("#cartSummaryList").shouldBe(visible);
        
        // validate checkut button
        $("#btnStartCheckout").should(exist);
    }
    
    @Step("validate shipping costs on cart page")
    public void validateShippingCosts(String shippingCosts)
    {
        $("#orderShippingCosts").shouldHave(exactText(shippingCosts));
    }
    
    @Step("validate cart page with shipping costs: '{shippingCosts}'")
    public void validate(String shippingCosts)
    {
        validateStructure();
        validateShippingCosts(shippingCosts);
    }
    
    // ----- validate product data in cart item ----- //

    private void validateCartItem(int position, String productName, String productStyle, String productSize, int productAmount, String productPrice)
    {
        // selector for product
        SelenideElement productContainer = $("#product" + (position - 1));

        // validate product name is same as {productName}
        productContainer.find(".productName").shouldHave(exactText(productName));
        
        // validate product style is same as {productStyle}
        productContainer.find(".productStyle").shouldHave(exactText(productStyle));

        // validate product size is same as {productSize}
        productContainer.find(".productSize").shouldHave(exactText(productSize));
        
        // validate product amount is same as {productAmount}
        productContainer.find(".productCount").shouldHave(exactValue(Integer.toString(productAmount)));

        // validate product name is same as {productName}
        productContainer.find(".productUnitPrice").shouldHave(exactText(productPrice));
    }
    
    @Step("validate '{product}' in on the cart page")
    public void validateCartItem(int position, Product product)
    {
        validateCartItem(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), product.getUnitPrice());
    }
    
    // ----------------------------------------------------------------------------- //
    
    @Step("validate subtotal in the cart")
    public void validateSubtotal(String subtotal)
    {
        $$("#cartSummaryList li").findBy(text("Subtotal")).find(".text-right").shouldHave(exactText(subtotal));
    }

    @Step("validate product in the cart")
    public void validateContainsProduct(Product product)
    {
        SelenideElement productContainer = $$("div.hidden-xs").filter((matchText(product.getRowRegex()))).shouldHaveSize(1).first()
                                                              .parent().parent();
        productContainer.find(".productName").shouldHave(exactText(product.getName()));
        productContainer.find(".productSize").shouldHave(exactText(product.getSize()));
        productContainer.find(".productStyle").shouldHave(exactText(product.getStyle()));
        productContainer.find(".productCount").shouldHave(value(Integer.toString(product.getAmount())));
        productContainer.find(".productUnitPrice").shouldHave(exactText(product.getUnitPrice()));
        productContainer.find(".productTotalUnitPrice").shouldHave(exactText(PriceHelper.format(product.getTotalPrice())));
    }

    @Step("validate sub total and line item total after adding on the cart page")
    public void validateSubAndLineItemTotalAfterAdd(int position, String oldSubTotal, double totalPrice)
    {
        SelenideElement productContainer = $("#product" + (position - 1));
        // Store unit price (without $ sign)
        // Takes the price per 1 unit of the specified item
        String unitPriceShort_varDynamic = PriceHelper.removeCurrency(productContainer.find(".unitPriceShort").text());
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
        String price2 = PriceHelper.subtractFromPrice(subOrderPrice, PriceHelper.format(totalPrice));

        Assert.assertEquals(price, price2);
    }

    /* TODO - check if needed
    @Step("validate line item amount on the cart page")
    public void validateProductAmount(int position, int amount)
    {
        // Makes sure the amount of the item with index @{index} in the cart equals the parameter
        $("#product" + (position - 1) + " .productCount").shouldHave(exactValue(Integer.toString(amount)));
    }
    */

    @Step("get product name from line item on the cart page")
    public String getProductName(int position)
    {
        // Get the product name to enable usage outside this module.
        return $("#product" + (position - 1) + " .productName").text();
    }

    @Step("get product style from line item on the cart page")
    public String getProductStyle(int position)
    {
        // Get the style to enable usage outside this module.
        return $("#product" + (position - 1) + " .productStyle").text();
    }

    @Step("get product size from line item on the cart page")
    public String getProductSize(int position)
    {
        // Get the size to enable usage outside this module.
        return $("#product" + (position - 1) + " .productSize").text();
    }

    @Step("get product count from line item on the cart page")
    public String getProductCount(int position)
    {
        // Get the size to enable usage outside this module.
        return $("#product" + (position - 1) + " .productCount").val();
    }

    @Step("get product unit price from line item on the cart page")
    public String getProductUnitPrice(int position)
    {
        // Get the product price to enable usage outside this module.
        return $("#product" + (position - 1) + " .productUnitPrice").text();
    }

    @Step("get product total price from line item on the cart page")
    public String getProductTotalUnitPrice(int position)
    {
        // Get the product price to enable usage outside this module.
        return $("#product" + (position - 1) + " .productTotalUnitPrice").text();
    }

    @Step("get product from line item on the cart page")
    public Product getProduct(int position)
    {
        return new Product(getProductName(position), getProductUnitPrice(position), getProductStyle(position), getProductSize(position), Integer.parseInt(getProductCount(position)));
    }

    /**
     * @param position
     * @param amount
     */
    @Step("update product count on the cart page")
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

    private SelenideElement findProductContainer(String productName, String style, String size)
    {
        SelenideElement productContainer = $$("div.hidden-xs").filter(text(productName)).first().parent().parent();
        for (int i = 0; i < $$("div.hidden-xs").filter(text(productName)).size(); i++)
        {
            SelenideElement product = $$("div.hidden-xs").filter(text(productName)).get(i);
            if (product.find(".productStyle").text().equals(style) && product.find(".productSize").text().equals(size))
            {
                productContainer = product.parent().parent();
            }
        }
        return productContainer;
    }

    @Step("update product count by the name")
    public void updateProductCountByName(String productName, String style, String size, int amount)
    {
        SelenideElement productContainer = findProductContainer(productName, style, size);
        productContainer.find(".productCount").setValue(Integer.toString(amount));
        productContainer.find(".btnUpdateProduct").scrollTo().click();
    }

    @Step("remove product by name")
    public void removeProductByName(String productName, String style, String size)
    {
        SelenideElement productContainer = findProductContainer(productName, style, size);
        productContainer.find(".btnRemoveProduct").click();
        $("#buttonDelete").click();
    }

    /**
     * @param position
     */
    @Step("remove product on the cart page")
    public void removeProduct(int position)
    {
        // Click delete button
        // Click on the delete button for the product
        $("#btnRemoveProdCount" + (position - 1)).scrollTo().click();
        // Wait for the second delete button to appear
        // Wait until the confirmation button is visible
        $("#buttonDelete").waitUntil(visible, Neodymium.configuration().selenideTimeout());
        // Click delete button
        // Click the confirmation button
        $("#buttonDelete").scrollTo().click();
        // Wait until the confirmation button is gone
        $("#buttonDelete").waitUntil(hidden, Neodymium.configuration().selenideTimeout());
        // Reload page to let IDs adjust to the deletion
        miniCart.openMiniCart();
        miniCart.openCartPage();
    }

    /**
     * @param oldSubTotal
     * @param oldLineItemTotal
     */
    @Step("validate sub total and line item total after removing on the cart page")
    public void validateSubAndLineItemTotalAfterRemove(String oldSubTotal, String oldLineItemTotal)
    {
        String newSubTotal = PriceHelper.subtractFromPrice(oldSubTotal, oldLineItemTotal);
        subTotal.shouldHave(exactText(newSubTotal));
    }

    /**
     * @param position
     */
    @Step("click on a product on the cart page")
    public ProductDetailPage openProductPage(int position)
    {
        $("#product" + (position - 1) + " .product-img").scrollTo().click();
        return new ProductDetailPage().isExpectedPage();
    }

    private void clickCheckoutButton()
    {
        $("#btnStartCheckout").scrollTo().click();
    }

    @Step("open new shipping address from the cart page")
    public NewShippingAddressPage openNewShippingPage()
    {
        clickCheckoutButton();
        return new NewShippingAddressPage().isExpectedPage();
    }

    @Step("open shipping address from the cart page")
    public ShippingAddressPage openShippingPage()
    {
        clickCheckoutButton();
        return new ShippingAddressPage().isExpectedPage();
    }

    /**
     * @return
     */
    @Step("check if there are product on the cart page")
    public boolean hasProductsInCart()
    {
        return $("#btnRemoveProdCount0").exists();
    }
}
