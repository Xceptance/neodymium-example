package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Product;
import posters.pageobjects.pages.checkout.CartPage;
import posters.pageobjects.utility.PriceHelper;

public class MiniCart extends AbstractComponent
{
    private SelenideElement headerCart = $("#headerCartOverview");
    
    private SelenideElement headerTotalCount = $("#count_wideView");

    private SelenideElement miniCart = $("#mini-cart-menu");
    
    private SelenideElement miniCartTitle = $(".cartMiniProductCounter");
    
    private SelenideElement subOrderPrice = $(".subOrderPrice");
    
    private SelenideElement viewCartButton = $(".goToCart");

    private ElementsCollection productCounts = $$(".prodCount");

    private ElementsCollection productPrices = $$(".prodPrice strong");

    @Override
    @Step("ensure availability mini cart")
    public void isComponentAvailable()
    {
         miniCart.should(exist);
    }

    /// ========== mini cart navigation ==========- ///

    @Step("open the mini cart")
    public void openMiniCart()
    {
        headerCart.click(ClickOptions.usingJavaScript());
        miniCart.waitUntil(visible, 9000);            
    }

    @Step("close the mini cart")
    public void closeMiniCart()
    {
        $("#top-demo-disclaimer").click(ClickOptions.usingJavaScript());
        miniCart.waitUntil(not(visible), 9000);
    }

    @Step("open the cart page")
    public CartPage openCartPage()
    {
        openMiniCart();
        viewCartButton.click(ClickOptions.usingJavaScript());
        return new CartPage().isExpectedPage();
    }

    /// ========== get mini cart data ========== ///

    @Step("get the subtotal price from mini cart")
    public String getSubtotal()
    {
        openMiniCart();
        String subtotal = subOrderPrice.text();
        closeMiniCart();

        return subtotal;
    }

    @Step("get the total product count from mini cart")
    public int getTotalCount()
    {
        return Integer.parseInt(headerTotalCount.text());
    }

    /**
     * Note: Loops through all total product prices in the mini cart and adds it to the "subtotal" variable.
     * 
     * @return subtotal (The sum of all total product prices)
     */
    @Step("calculate sum of all total product prices")
    public String calculateSubtotal()
    {
        double subtotal = 0;

        for (SelenideElement totalProductPrice : productPrices)
        {
            subtotal = PriceHelper.calculateSubtotalMiniCart(subtotal, totalProductPrice.getText());
        }

        return PriceHelper.format(subtotal);
    }

    /**
     * Note: If there is at least 1 item in the cart, it loops through all total product counts in the mini cart
     * and adds it to the "totalCount" variable.
     * 
     * @return totalCount (The sum of all total product counts)
     */
    @Step("calculate total count via product counts")
    public int calculateTotalCount()
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

    /// ========== validate mini cart ========== ///

    @Step("validate the mini cart total product count equals '{totalCount}'")
    public void validateTotalCount(int totalCount)
    {
        headerTotalCount.shouldHave(exactText(Integer.toString(totalCount)));
    }

    @Step("validate the mini cart title")
    public void validateTitle()
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

    @Step("validate the mini cart subtotal price equals '{subtotal}'")
    public void validateSubtotal(String subtotal)
    {
        subOrderPrice.shouldHave(exactText(subtotal)).shouldBe(visible);
    }

    @Step("validate mini cart")
    public void validateStructure()
    {
        openMiniCart();

        // validate shopping cart icon
        $(".icon-shopping-cart").shouldBe(visible);

        // validate total count
        validateTotalCount(calculateTotalCount());

        // validate title
        // TODO - fix consistency mini cart title
        // validateTitle();

        // validate label subtotal
        $(".labelText").shouldHave(exactText(Neodymium.localizedText("header.miniCart.subtotal"))).shouldBe(visible);

        // validate subtotal
        if (getTotalCount() == 0)
        {
            // if there are no items in cart
            validateSubtotal("$0.00");
        }
        else
        {
            // if there are items in cart
            validateSubtotal(calculateSubtotal());
        }

        // validate view cart button
        viewCartButton.shouldHave(exactText(Neodymium.localizedText("button.viewCart"))).shouldBe(visible);

        closeMiniCart();
    }

    private void validateMiniCartItem(int position, String productName, String productStyle, String productSize, int productCount, String prodTotalPrice)
    {
        openMiniCart();

        // selector for product
        SelenideElement miniCartItem = $$("#mini-cart-menu .cartItems").get(position - 1);

        // validate parameters
        miniCartItem.find(".prodName").shouldHave(exactText(productName));
        miniCartItem.find(".prodStyle").shouldHave(exactText(productStyle));
        miniCartItem.find(".prodSize").shouldHave(exactText(productSize));
        miniCartItem.find(".prodCount").shouldHave(exactText(Integer.toString(productCount)));
        miniCartItem.find(".prodPrice").shouldHave(exactText(prodTotalPrice));

        closeMiniCart();
    }

    @Step("validate '{product}' on position {position} in the mini cart")
    public void validateMiniCartItem(int position, Product product)
    {
        validateMiniCartItem(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), PriceHelper.format(product.getTotalPrice()));
    }

    @Step("validate '{product}' on position '{position}' in the mini cart after changing it's quantity")
    public void validateMiniCartItem(int position, Product product, int productAmount, String productPrice)
    {
        validateMiniCartItem(position, product.getName(), product.getStyle(), product.getSize(), productAmount, productPrice);
    }
}
