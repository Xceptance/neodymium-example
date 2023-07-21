package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchesText;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.Product;
import posters.pageobjects.pages.checkout.CartPage;
import posters.pageobjects.utility.PriceHelper;

/**
 * @author pfotenhauer
 */
public class MiniCart extends AbstractComponent
{
    private static SelenideElement headerCart = $("#headerCartOverview");

    private final static String miniCartSelector = "#miniCartMenu";

    private SelenideElement miniCart = $(miniCartSelector);
    
    public static ElementsCollection subtotal = $$(miniCartSelector + " .cartMiniSubOrderTotal");

    private SelenideElement subOrderPrice = $(miniCartSelector + " .subOrderPrice");

    private SelenideElement totalCountElement = $("#headerCartOverview .headerCartProductCount");
    
    private SelenideElement goToCartButton = $(".goToCart");
    
    public void isComponentAvailable()
    {
        $("#btnCartOverviewForm").should(exist);
    }

    @Step("open the mini cart")
    public void openMiniCart()
    {
        // Hover over the cart icon
        headerCart.hover();
        // Wait for mini cart to appear
        // Wait for the mini cart to show
        miniCart.waitUntil(visible, Neodymium.configuration().selenideTimeout());
    }

    @Step("close the mini cart")
    public void closeMiniCart()
    {
        // Move the mouse out of the area
        $("#brand").hover();
        // Wait for mini cart to disappear
        // Wait for the mini cart to disappear
        miniCart.waitUntil(not(visible), Neodymium.configuration().selenideTimeout());
    }

    @Step("open the cart page")
    public CartPage openCartPage()
    {

        // Open the cart
        // Click on the button to go to the Cart
         goToCartButton.click();
        return new CartPage().isExpectedPage();
    }

    @Step("get the total product count from mini cart")
    public int getTotalCount()
    {
        return Integer.parseInt(totalCountElement.text());
    }

    @Step("validate the mini cart total product count")
    public void validateTotalCount(int totalCount)
    {
        totalCountElement.shouldHave(exactText(Integer.toString(totalCount)));
    }

    @Step("get the subtotal price from mini cart")
    public String getSubtotal()
    {
        // Store the mini cart subtotal
        // Open mini cart
        openMiniCart();
        // Store subtotal in oldSubTotal
        String subtotal = subOrderPrice.text();
        // Close mini cart
        closeMiniCart();

        return subtotal;
    }

    @Step("validate the mini cart subtotal price")
    public void validateSubtotal(String subtotal)
    {
        // Verify the mini cart shows the specified subtotal
        // Open mini cart
        openMiniCart();
        // Verify subtotal equals specified subtotal
        // Compare the subtotal to the parameter
        subOrderPrice.shouldHave(exactText(subtotal));
        // Close Mini Cart
        closeMiniCart();
    }

    /**
     * @param position
     * @param product
     */

    @Step("validate '{product}' in the mini cart")
    public void validateMiniCart(int position, Product product)
    {
        validateMiniCart(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(),
                         PriceHelper.format(product.getTotalPrice()));
    }

    @Step("validate '{product}' in the mini cart by name")
    public void validateMiniCartByProduct(Product product)
    {
        SelenideElement productContainer = $$(".cartItems").filter(matchesText(product.getCartRowRegex())).shouldHaveSize(1).first();

        productContainer.find(".prodName").shouldHave(exactText(product.getName()));
        productContainer.find(".prodStyle").shouldHave(exactText(product.getStyle()));
        productContainer.find(".prodSize").shouldHave(exactText(product.getSize()));
        productContainer.find(".prodCount").shouldHave(exactText(Integer.toString(product.getAmount())));
        productContainer.find(".prodPrice").shouldHave(exactText(PriceHelper.format(product.getTotalPrice())));
    }

    /**
     * @param position
     * @param product
     * @param productAmount
     * @param productTotalPrice
     */
    @Step("validate '{product}' in the mini cart")
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
        // ul.cartMiniElementList li:nth-child(" + position + ") ul.cartItems
        SelenideElement miniCartItem = $$("#miniCartMenu .cartItems").get(position - 1);
        // Compares the name of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodName").shouldHave(exactText(productName));
        // Product Style
        // Compares the style of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodStyle").shouldHave(exactText(productStyle));
        // Product Size
        // Compares the style of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodSize").shouldHave(exactText(productSize));
        // Amount
        // Compares the amount of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodCount").shouldHave(exactText(Integer.toString(productCount)));
        // Price
        // Compares the price of the cart item at position @{position} to the parameter
        miniCartItem.find(".prodPrice").shouldHave(exactText(prodTotalPrice));
        // Close mini cart
        closeMiniCart();
    }

    // extended by Jonas
    @Step("validate shopping cart menu")
    public static void validateStructure() 
    {
        $(".icon-shopping-cart").shouldBe(visible);
        $$("#count_wideView").findBy(matchesText("\\d")).shouldBe(visible);
        
        headerCart.hover();
        // TODO - validate item count
        $$("#miniCartMenu .cartMiniHeader").findBy(matchesText(Neodymium.localizedText("header.shoppingCart.itemsInCart"))).shouldBe(visible);
        subtotal.findBy(matchesText(Neodymium.localizedText("header.shoppingCart.subtotal"))).shouldBe(visible);
        subtotal.findBy(matchesText("\\$\\d\\.\\d")).shouldBe(visible);
        $$("#miniCartMenu .linkButton").findBy(exactText(Neodymium.localizedText("header.shoppingCart.viewCart"))).shouldBe(visible);
        
        $("#globalNavigation").hover();
    }
}
