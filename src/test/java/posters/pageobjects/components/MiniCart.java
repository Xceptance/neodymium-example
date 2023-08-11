package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Product;
import posters.pageobjects.pages.checkout.CartPage;
import posters.pageobjects.utility.PriceHelper;

public class MiniCart extends AbstractComponent
{
    private static SelenideElement headerCart = $("#headerCartOverview");

    private static SelenideElement subOrderPrice = $("#miniCartMenu .subOrderPrice");
    
    private static SelenideElement miniCart = $("#miniCartMenu");
    
    private static SelenideElement miniCartTitle = $(".cartMiniProductCounter");

    private static SelenideElement totalCountElement = $("#headerCartOverview .headerCartProductCount");
    
    private SelenideElement goToCartButton = $(".goToCart");
    
    private static ElementsCollection productCounts = $$(".prodCount");
    
    private static ElementsCollection productPrices = $$(".prodPrice strong");
    
    @Override
    @Step("ensure availability mini cart")
    public void isComponentAvailable()
    {
        $("#btnCartOverviewForm").should(exist);
    }
    
    /// ----- mini cart navigation ------ ///
    
    @Step("open the mini cart")
    public static void openMiniCart()
    {
        headerCart.hover();
        miniCart.waitUntil(visible, 9000);
    }

    @Step("close the mini cart")
    public static void closeMiniCart()
    {
        $("#brand").hover();
        miniCart.waitUntil(not(visible), 9000);
    }
    
    @Step("open the cart page")
    public CartPage openCartPage()
    {
        openMiniCart();
        goToCartButton.click();
        return new CartPage().isExpectedPage();
    }
    
    /// ----- get data mini cart ----- ///
    
    @Step("get the subtotal price from mini cart")
    public String getSubtotal()
    {
        openMiniCart();
        String subtotal = subOrderPrice.text();
        closeMiniCart();

        return subtotal;
    }
    
    @Step("get the total product count from mini cart")
    public static int getTotalCount()
    {
        return Integer.parseInt(totalCountElement.text());
    }
    
    @Step("calculate sum of all total product prices")
    public static String calculateSubtotal() 
    {
        double subtotal = 0;
        
        for (SelenideElement totalProductPrice : productPrices) 
        {
            subtotal = PriceHelper.calculateSubtotalMiniCart(subtotal, totalProductPrice.getText());
        }

        return PriceHelper.format(subtotal);
    }
    
    @Step("calculate total count via product counts")
    public static int calculateTotalCount() 
    {
        int totalCount = 0;
        
        if ($(".cartItems").exists()) 
        {
            for (SelenideElement productCount : productCounts) 
            {
                totalCount = PriceHelper.calculateTotalCountMiniCart(totalCount, productCount.getText());
            }
            
            return totalCount;
        }
        
        return totalCount;
    }
    
    /// ----- validate mini cart structure ----- ///
   
    @Step("validate the mini cart total product count")
    public static void validateTotalCount(int totalCount)
    {
        totalCountElement.shouldHave(exactText(Integer.toString(totalCount)));
    }
    
    @Step("validate the mini cart title")
    public static void validateTitle()
    {        
        if (getTotalCount() == 1) 
        {
            miniCartTitle.shouldHave(exactText(getTotalCount() + " " + Neodymium.localizedText("header.miniCart.title.singular"))).shouldBe(visible);
        }
        else 
        {
            miniCartTitle.shouldHave(exactText(getTotalCount() + " " + Neodymium.localizedText("header.miniCart.title.plural"))).shouldBe(visible);
        }
    }
    
    @Step("validate the mini cart subtotal price")
    public static void validateSubtotal(String subtotal)
    {
        subOrderPrice.shouldHave(exactText(subtotal)).shouldBe(visible);
    }
    
    @Step("validate mini cart menu")
    public static void validateStructure()
    {
        openMiniCart();
        
        // validate shopping cart icon
        $(".icon-shopping-cart").shouldBe(visible);
        
        // validate total count
        validateTotalCount((int)(calculateTotalCount()));
        
        // validate title
        // TODO - fix consistency mini cart title
        //validateTitle();
        
        // validate label subtotal
        $(".labelText").shouldHave(exactText(Neodymium.localizedText("header.miniCart.subtotal"))).shouldBe(visible);
        
        // validate view cart button
        $("#miniCartMenu .linkButton").shouldHave(exactText(Neodymium.localizedText("header.miniCart.viewCart"))).shouldBe(visible);
        
        // validate subtotal
        if (getTotalCount() == 0) 
        {
            validateSubtotal("$0.00");
        }
        else 
        {
            validateSubtotal(calculateSubtotal());
        }
        
        closeMiniCart();
    }
    
    /// ----- validate mini cart item ----- ///
    
    @Step("validate data cart item in mini cart")
    private void validateMiniCartItem(int position, String productName, String productStyle, String productSize, int productCount, String prodTotalPrice)
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
    public void validateMiniCartItem(int position, Product product)
    {
        validateMiniCartItem(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), PriceHelper.format(product.getTotalPrice()));
    }
    
    @Step("validate '{product}' in the mini cart")
    public void validateMiniCartItem(int position, Product product, int productAmount, String productPrice)
    {
        validateMiniCartItem(position, product.getName(), product.getStyle(), product.getSize(), productAmount, productPrice);
    }
}
