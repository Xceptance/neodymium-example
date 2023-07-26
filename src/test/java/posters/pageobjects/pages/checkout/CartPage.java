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
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductDetailPage;
import posters.pageobjects.utility.PriceHelper;

public class CartPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titleCart");
    
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

    /// ----- validate content cart page ----- ///

    @Step("validate empty cart page")
    public void validateEmptyCartPage() 
    {
        title.shouldHave(exactText(Neodymium.localizedText("CartPage.title"))).shouldBe(visible);
        $("#errorCartMessage").shouldHave(exactText(Neodymium.localizedText("CartPage.errorMessage"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate cart page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate process sequence
        $(".process-wrap").shouldBe(visible);
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("CartPage.title"))).shouldBe(visible);

        // validate product list
        cartTable.shouldBe(visible);

        // validate at least 1 product in product list
        $("#product0").shouldBe(visible);

        // validate cart summary list
        $("#cartSummaryList").shouldBe(visible);

        // validate checkout button
        $("#btnStartCheckout").should(visible);
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
    
    @Step("validate sub total and line item total after adding on the cart page")
    public void validateTotalAfterAdd(int position, String oldSubTotal, double totalPrice)
    {
        /// ----- validate total unit price of specified product ----- ///

        SelenideElement productContainer = $("#product" + (position - 1));

        // store product unit price (without $ sign) for 1 unit
        String unitPrice = PriceHelper.removeCurrency(productContainer.find(".unitPriceShort").text());

        // store product count
        String quantity = $("#productCount" + (position - 1)).val();

        // calculate price of specified product
        String subOrderPrice = PriceHelper.computeRowPrice(PriceHelper.addCurrency(unitPrice), quantity);

        // verify calculated unit price equals the displayed total unit price
        productContainer.find(".productTotalUnitPrice").shouldHave(exactText(subOrderPrice));

        /// ----- validate sub total ----- ///

        String newSubTotal = subTotal.text();

        // new total - old total = price of item you just added
        String productPrice = PriceHelper.subtractFromPrice(newSubTotal, oldSubTotal);

        // price difference for specific product after changing product amount
        String productPriceAmountChange = PriceHelper.subtractFromPrice(subOrderPrice, PriceHelper.format(totalPrice));

        // validate {price} equals {price2}
        Assert.assertEquals(productPrice, productPriceAmountChange);
    }
    
    @Step("validate sub total and line item total after removing on the cart page")
    public void validateTotalAfterRemove(String oldSubTotal, String oldLineItemTotal)
    {
        String newSubTotal = PriceHelper.subtractFromPrice(oldSubTotal, oldLineItemTotal);
        subTotal.shouldHave(exactText(newSubTotal));
    }

    /// ----- validate product data in cart item ----- ///

    @Step("validate item amount on the cart page")
    public void validateProductAmount(int position, int amount)
    {
        $("#product" + (position - 1) + " .productCount").shouldHave(exactValue(Integer.toString(amount)));
    }

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
        validateProductAmount(position, productAmount);

        // validate product name is same as {productName}
        productContainer.find(".productUnitPrice").shouldHave(exactText(productPrice));
    }

    @Step("validate '{product}' on the cart page")
    public void validateCartItem(int position, Product product)
    {
        validateCartItem(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), product.getUnitPrice());
    }
   
    @Step("validate '{product}' on the cart page")
    public void validateCartItem(int position, Product product, int productAmount)
    {
        validateCartItem(position, product.getName(), product.getStyle(), product.getSize(), productAmount, product.getUnitPrice());
    }

    /// ----- get product data ----- ///

    @Step("get product name from line item on the cart page")
    public String getProductName(int position)
    {
        return $("#product" + (position - 1) + " .productName").text();
    }

    @Step("get product style from line item on the cart page")
    public String getProductStyle(int position)
    {
        return $("#product" + (position - 1) + " .productStyle").text();
    }

    @Step("get product size from line item on the cart page")
    public String getProductSize(int position)
    {
        return $("#product" + (position - 1) + " .productSize").text();
    }

    @Step("get product count from line item on the cart page")
    public String getProductCount(int position)
    {
        return $("#product" + (position - 1) + " .productCount").val();
    }

    @Step("get product unit price from line item on the cart page")
    public String getProductUnitPrice(int position)
    {
        return $("#product" + (position - 1) + " .productUnitPrice").text();
    }

    @Step("get product total price from line item on the cart page")
    public String getProductTotalUnitPrice(int position)
    {
        return $("#product" + (position - 1) + " .productTotalUnitPrice").text();
    }

    @Step("get product from line item on the cart page")
    public Product getProduct(int position)
    {
        return new Product(getProductName(position), getProductUnitPrice(position), getProductStyle(position), getProductSize(position), Integer.parseInt(getProductCount(position)));
    }
    
    /// ----- update product data ----- ///
    
    @Step("update product count on the cart page")
    public void updateProductCount(int position, int amount)
    {
        SelenideElement productContainer = $("#product" + (position - 1));
        
        // type new amount
        productContainer.find(".productCount").setValue(Integer.toString(amount));
        
        // click update button
        productContainer.find(".btnUpdateProduct").scrollTo().click();
    }
    
    @Step("remove product on the cart page")
    public void removeProduct(int position)
    { 
        // click delete button
        $("#btnRemoveProdCount" + (position - 1)).scrollTo().click();
        
        // click delete confirmation button
        $("#buttonDelete").scrollTo().click();
    }
    
    /// ----- cart page navigation ----- ///
    
    @Step("click on a product on the cart page")
    public ProductDetailPage openProductDetailPage(int position)
    {
        $("#product" + (position - 1) + " .product-img").scrollTo().click();
        return new ProductDetailPage().isExpectedPage();
    }
    
    public HomePage openHomePage() 
    {
        $("#brand").scrollTo().click();
        return new HomePage().isExpectedPage();
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

    @Step("check if there are product on the cart page")
    public boolean hasProductsInCart()
    {
        return $("#btnRemoveProdCount0").exists();
    }
}
