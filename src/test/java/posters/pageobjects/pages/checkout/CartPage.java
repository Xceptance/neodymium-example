/**
 * 
 */
package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.Product;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.utility.PriceHelper;

/**
 * @author pfotenhauer
 */
public class CartPage extends AbstractBrowsingPage
{
    private SelenideElement cartTable = $("#cartOverviewTable");

    private SelenideElement subTotal = $("#orderSubTotalValue");

    @Override
    @Step("ensure this is a cart page")
    public CartPage isExpectedPage()
    {
        cartTable.should(exist);
        return this;
    }

    @Step("validate subtotal in the cart")
    public CartPage validateSubtotal(String subtotal)
    {
        $$("#cartSummaryList li").findBy(text("Subtotal")).find(".text-right").shouldHave(exactText(subtotal));
        return this;
    }

    @Override
    @Step("validate cart page structure")
    public CartPage validateStructure()
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
        return this;
    }

    @Step("validate product in the cart")
    public CartPage validateContainsProduct(Product product)
    {
        SelenideElement productContainer = $$("div.hidden-xs").filter((matchText(product.getRowRegex()))).shouldHaveSize(1).first()
                                                              .parent().parent();

        productContainer.find(".productName").shouldHave(exactText(product.getName()));
        productContainer.find(".productSize").shouldHave(exactText(product.getSize()));
        productContainer.find(".productStyle").shouldHave(exactText(product.getStyle()));
        productContainer.find(".productCount").shouldHave(value(Integer.toString(product.getAmount())));
        productContainer.find(".productUnitPrice").shouldHave(exactText(product.getUnitPrice()));
        productContainer.find(".productTotalUnitPrice").shouldHave(exactText(PriceHelper.format(product.getTotalPrice())));
        return this;
    }

    @Step("validate shipping costs on cart page")
    public CartPage validateShippingCosts(String shippingCosts)
    {
        // Assert the correct shipping price is shown
        $("#orderShippingCosts").shouldHave(exactText(shippingCosts));
        return this;
    }

    /**
     * @param shippingcosts
     */
    @Step("validate cart page with shipping costs: \"{shippingCosts}\"")
    public CartPage validate(String shippingCosts)
    {
        validateStructure();
        validateShippingCosts(shippingCosts);
        return this;
    }

    private SelenideElement findProductContainer(Product product)
    {
        SelenideElement productContainer = $$("div.hidden-xs").filter(text(product.getName())).first().parent().parent();
        for (int i = 0; i < $$("div.hidden-xs").filter(text(product.getName())).size(); i++)
        {
            SelenideElement productCard = $$("div.hidden-xs").filter(text(product.getName())).get(i);
            if (productCard.find(".productStyle").text().equals(product.getStyle()) && productCard.find(".productSize").text().equals(product.getSize()))
            {
                productContainer = productCard.parent().parent();
            }
        }
        return productContainer;
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
    public CartPage updateProductCountByName(Product product, int amount)
    {
        SelenideElement productContainer = findProductContainer(product);
        productContainer.find(".productCount").setValue(Integer.toString(amount));
        productContainer.find(".btnUpdateProduct").scrollTo().click();
        return this;
    }

    @Step("remove product by name")
    public CartPage removeProductByName(Product product)
    {
        SelenideElement productContainer = findProductContainer(product);
        productContainer.find(".btnRemoveProduct").click();
        $("#buttonDelete").click();
        return this;
    }

    private CartPage clickCheckoutButton()
    {
        $("#btnStartCheckout").scrollTo().click();
        return this;
    }

    @Step("open shipping address for registered user from the cart page")
    public ShippingAddressSelectionListPage openShippingPageForRegisteredUserWithSavedAddress()
    {
        clickCheckoutButton();
        ShippingAddressSelectionListPage shippingAddressPage = new ShippingAddressSelectionListPage();
        shippingAddressPage.isExpectedPage();
        return shippingAddressPage;
    }

    @Step("open shipping address from the cart page")
    public ShippingAddressPage openShippingPage()
    {
        clickCheckoutButton();
        ShippingAddressPage shippingAddressPage = new ShippingAddressPage();
        shippingAddressPage.isExpectedPage();
        return shippingAddressPage;
    }

    /**
     * @param position
     */
    @Step("remove product on the cart page")
    public CartPage removeProduct(int position)
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
        return this;
    }

    /**
     * @return
     */
    @Step("check if there are product on the cart page")
    public boolean hasProductsInCart()
    {
        return $("#btnRemoveProdCount0").exists();
    }

    @Step("validate cart is empty")
    public CartPage validateIsEmpty()
    {
        $("#btnRemoveProdCount0").shouldNot(exist);
        $("#errorCartMessage").shouldHave(exactText(Neodymium.localizedText("CartPage.emptyCartInfo")));
        return this;
    }
}
