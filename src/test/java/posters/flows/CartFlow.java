package posters.flows;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;

import io.qameta.allure.Step;
import posters.pageobjects.utility.PriceHelper;

public class CartFlow
{
    private static SelenideElement subTotal = $("#order-sub-total-value");
    
    /**
     * It checks if the price change of subtotal is equal to the price change of the product.
     * 
     * @param position
     *            of specific product in cart
     * @param oldSubTotal
     *            subtotal before adding new product to cart/ increasing product quantity
     * @param oldTotalProductPrice
     *            product price before adding/ increasing product quantity
     */
    @Step("validate sub total and line item total after adding on the cart page")
    public static void validateTotalAfterAdd(String productName, String productSize, String productStyle, String oldSubTotal, double oldTotalProductPrice)
    {
        /// ========== validate total unit price of specified product ========== ///
       
        // selector for product
        SelenideElement productContainer = $$(".js-cart-product").shouldHave(sizeGreaterThan(0))
                                                                 .findBy(new WebElementCondition("has name " + productName + ", style " + productStyle
                                                                                                 + " and size " + productSize)
                                                                 {
                                                                     @Override
                                                                     public CheckResult check(Driver driver, WebElement element)
                                                                     {
                                                                         boolean matchesName = element.findElement(By.cssSelector(".product-name")).getText()
                                                                                                      .equals(productName);
                                                                         boolean matchesStyle = element.findElement(By.cssSelector(".product-style")).getText()
                                                                                                       .equals(productStyle);
                                                                         boolean matchesSize = element.findElement(By.cssSelector(".product-size")).getText()
                                                                                                      .equals(productSize);
                                                                         return new CheckResult(matchesName && matchesStyle && matchesSize, "");
                                                                     }
                                                                 }).shouldBe(visible, Duration.ofMillis(9000));
        
        // store product unit price (without $ sign) for 1 unit
        String unitPrice = productContainer.find(".product-unit-price").text();

        // store product count
        String quantity = $(".product-count").val();

        // calculate price of specified product
        String newTotalProductPrice = PriceHelper.calculateTotalProductPrice(unitPrice, quantity);

        // verify calculated unit price equals the displayed total unit price
        productContainer.find(".product-total-unit-price").shouldHave(exactText(newTotalProductPrice));

        /// ========== validate sub total ========== ///

        String newSubTotal = subTotal.text();

        // new total - old total = price of item you just added
        String productPrice = PriceHelper.substract(newSubTotal, oldSubTotal);

        // price difference for specific product after changing product amount
        String totalProductPriceChange = PriceHelper.substract(newTotalProductPrice, PriceHelper.format(oldTotalProductPrice));

        Assert.assertEquals(productPrice, totalProductPriceChange);
    }

    @Step("validate sub total and line item total after removing on the cart page")
    public static void validateTotalAfterRemove(String oldSubTotal, String oldTotalProductPrice)
    {
        String newSubTotal = PriceHelper.substract(oldSubTotal, oldTotalProductPrice);
        subTotal.shouldHave(exactText(newSubTotal));
    }
}
