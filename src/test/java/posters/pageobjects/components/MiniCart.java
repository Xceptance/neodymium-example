package posters.pageobjects.components;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;

import io.qameta.allure.Step;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import posters.pageobjects.pages.checkout.CartPage;
import posters.pageobjects.utility.PriceHelper;
import posters.tests.testdata.dataobjects.Product;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MiniCart extends AbstractComponent
{
    private SelenideElement headerCart = $("#header-cart-overview");

    private SelenideElement headerTotalCount = $("#count-wide-view");

    private SelenideElement miniCart = $("#mini-cart-menu");

    private SelenideElement miniCartTitle = $(".cart-mini-product-counter");

    private SelenideElement subOrderPrice = $(".suborder-price");

    private SelenideElement viewCartButton = $(".go-to-cart");

    private ElementsCollection productCounts = $$(".prod-count");

    private ElementsCollection productPrices = $$(".prod-price strong");

    @Override
    @Step("ensure availability mini cart")
    public void assertComponentAvailable()
    {
        Assert.assertTrue(SelenideAddons.optionalWaitUntilCondition(headerCart, exist));
    }
    
    @Step("ensure availability mini cart")
    public boolean isComponentAvailable()
    {
         return miniCart.exists();
    }

    /// ========== mini cart navigation ==========- ///

    @Step("open the mini cart")
    public void openMiniCart()
    {
        headerCart.click(ClickOptions.usingJavaScript());
        miniCart.shouldBe(visible, Duration.ofMillis(9000));
    }

    @Step("close the mini cart")
    public void closeMiniCart()
    {
        $("#top-demo-disclaimer").click(ClickOptions.usingJavaScript());
        miniCart.shouldBe(not(visible), Duration.ofMillis(9000));
    }

    @Step("open the cart page")
    public CartPage openCartPage()
    {
        openMiniCart();
        viewCartButton.click(ClickOptions.usingJavaScript());
        return new CartPage().assertExpectedPage();
    }

    /// ========== get mini cart data ========== ///

    @Step("get the subtotal price from mini cart")
    public String getSubtotal()
    {
        String subtotal;

        if (Integer.parseInt(headerTotalCount.text()) == 0)
        {
            subtotal = "$0.00";
        }
        else
        {
            openMiniCart();
            subtotal = subOrderPrice.text();
            closeMiniCart();
        }

        return subtotal;
    }

    @Step("get the total product count from mini cart")
    public int getTotalCount()
    {
        return Integer.parseInt(headerTotalCount.text());
    }

    /**
     * Loops through all total product prices in the mini cart and adds it to the "subtotal" variable.
     *
     * @return subtotal The sum of all total product prices
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
     * If there is at least 1 item in the cart, it loops through all total product counts in the mini cart and adds it to the "totalCount" variable.
     *
     * @return totalCount The sum of all total product counts
     */
    @Step("calculate total count via product counts")
    public int calculateTotalCount()
    {
        int totalCount = 0;

        if ($(".cart-items").exists())
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
        // validate shopping cart icon
        $(".icon-shopping-cart").shouldBe(visible);

        // if minicart contains 0 items it's not possible to open
        if (getTotalCount() == 0)
        {
            return;
        }

        openMiniCart();

        // validate total count
        validateTotalCount(calculateTotalCount());

        // validate subtotal
        validateSubtotal(calculateSubtotal());

        // validate label subtotal
        $(".labelText").shouldHave(exactText(Neodymium.localizedText("header.miniCart.subtotal"))).shouldBe(visible);

        // validate title
        // TODO - fix consistency mini cart title
        // validateTitle();

        // validate view cart button
        viewCartButton.shouldHave(exactText(Neodymium.localizedText("button.viewCart"))).shouldBe(visible);

        closeMiniCart();
    }

    private void validateMiniCartItem(String productName, String productStyle, String productSize, int productCount, String prodTotalPrice)
    {
        openMiniCart();

        // selector for product
        SelenideElement productContainer = $$(".cart-items").shouldHave(sizeGreaterThan(0))
                                                            .findBy(new WebElementCondition("has name " + productName + ", style " + productStyle
                                                                                                + " and size " + productSize)
                                                            {
                                                                @Override
                                                                public CheckResult check(Driver driver, WebElement element)
                                                                {
                                                                    boolean matchesName = element.findElement(By.cssSelector(".prod-name")).getText()
                                                                                                 .equals(productName);
                                                                    boolean matchesStyle = element.findElement(By.cssSelector(".prod-style")).getText()
                                                                                                  .equals(productStyle);
                                                                    boolean matchesSize = element.findElement(By.cssSelector(".prod-size")).getText()
                                                                                                 .equals(productSize);
                                                                    return new CheckResult(matchesName && matchesStyle && matchesSize, "");
                                                                }
                                                            }).shouldBe(visible, Duration.ofMillis(9000));

        // validate parameters
        productContainer.find(".prod-name").shouldHave(exactText(productName));
        productContainer.find(".prod-style").shouldHave(exactText(productStyle));
        productContainer.find(".prod-size").shouldHave(exactText(productSize));
        productContainer.find(".prod-count").shouldHave(exactText(Integer.toString(productCount)));
        productContainer.find(".prod-price").shouldHave(exactText(prodTotalPrice));

        closeMiniCart();
    }

    @Step("validate '{product}' in the mini cart")
    public void validateMiniCartItem(Product product)
    {
        validateMiniCartItem(product.getName(), product.getStyle(), product.getSize(), product.getAmount(),
                             PriceHelper.format(product.calculateTotalPrice()));
    }

    @Step("validate '{product}' in the mini cart after changing it's quantity")
    public void validateMiniCartItem(Product product, int productAmount, String productPrice)
    {
        validateMiniCartItem(product.getName(), product.getStyle(), product.getSize(), productAmount, productPrice);
    }
}
