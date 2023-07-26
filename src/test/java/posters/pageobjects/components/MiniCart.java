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

public class MiniCart extends AbstractComponent
{
    private static SelenideElement headerCart = $("#headerCartOverview");

    private SelenideElement subOrderPrice = $("#miniCartMenu .subOrderPrice");

    private SelenideElement totalCountElement = $("#headerCartOverview .headerCartProductCount");
    
    private SelenideElement goToCartButton = $(".goToCart");
    
    @Override
    @Step("ensure availability mini cart")
    public void isComponentAvailable()
    {
        $("#btnCartOverviewForm").should(exist);
    }
    
    // ----- mini cart navigation ------ //
    
    @Step("open the mini cart")
    public void openMiniCart()
    {
        headerCart.hover();
    }

    @Step("close the mini cart")
    public void closeMiniCart()
    {
        $("#brand").hover();
    }
    
    @Step("open the cart page")
    public CartPage openCartPage()
    {
        openMiniCart();
        goToCartButton.click();
        return new CartPage().isExpectedPage();
    }
    
    // ----- validate product data in mini cart ----- //
   
    @Step("validate shopping cart menu")
    public static void validateStructure()
    {
        // TODO - verify (not) empty shopping cart in one function for header component 
    }
    
    @Step("validate the mini cart subtotal price")
    public void validateSubtotal(String subtotal)
    {
        openMiniCart();
        subOrderPrice.shouldHave(exactText(subtotal));
        closeMiniCart();
    }
    
    @Step("validate the mini cart total product count")
    public void validateTotalCount(int totalCount)
    {
        totalCountElement.shouldHave(exactText(Integer.toString(totalCount)));
    }
    
    @Step("validate empty mini cart")
    public void validateEmptyMiniCart() 
    {
        // validate shopping cart icon, item count
        $(".icon-shopping-cart").shouldBe(visible);
        $(".headerCartProductCount").shouldHave(exactText("0")).shouldBe(visible);
        
        openMiniCart();
        
        // validate cart window header
        $$("#miniCartMenu .cartMiniHeader").findBy(matchesText(Neodymium.localizedText("header.shoppingCart.noItemsInCart"))).shouldBe(visible);
        
        // validate subtotal
        $("#miniCartMenu .cartMiniSubOrderTotal").shouldHave(matchesText(Neodymium.localizedText("header.shoppingCart.subtotal"))).shouldBe(visible);
        $(".subOrderPrice").shouldHave(exactText("$0.00")).shouldBe(visible);
        
        // validate view cart button
        $("#miniCartMenu .linkButton").shouldHave(exactText(Neodymium.localizedText("header.shoppingCart.viewCart"))).shouldBe(visible);
        
        closeMiniCart();
    }
    
    @Step("validate data cart item in mini cart")
    private void validateMiniCart(int position, String productName, String productStyle, String productSize, int productCount, String prodTotalPrice)
    {
        openMiniCart();
        
        // selector for product
        SelenideElement miniCartItem = $$("#miniCartMenu .cartItems").get(position - 1);
        
        // validate product name is same as {productName}
        miniCartItem.find(".prodName").shouldHave(exactText(productName));
        
        // validate product style is same as {productStyle}
        miniCartItem.find(".prodStyle").shouldHave(exactText(productStyle));
        
        // validate product size is same as {productSize}
        miniCartItem.find(".prodSize").shouldHave(exactText(productSize));

        // validate product count is same as {productCount}
        miniCartItem.find(".prodCount").shouldHave(exactText(Integer.toString(productCount)));
        
        // validate product prize is same as {productPrize}
        miniCartItem.find(".prodPrice").shouldHave(exactText(prodTotalPrice));

        closeMiniCart();
    }
    
    @Step("validate '{product}' in the mini cart")
    public void validateMiniCart(int position, Product product)
    {
        validateMiniCart(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), PriceHelper.format(product.getTotalPrice()));
    }
    
    @Step("validate '{product}' in the mini cart")
    public void validateMiniCart(int position, Product product, int productAmount, String productPrice)
    {
        validateMiniCart(position, product.getName(), product.getStyle(), product.getSize(), productAmount, productPrice);
    }
    

    // --------------------------------------------------------------



    @Step("get the total product count from mini cart")
    public int getTotalCount()
    {
        return Integer.parseInt(totalCountElement.text());
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
}
