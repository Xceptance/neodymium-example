package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.junit.Assert;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Product;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductDetailPage;
import posters.pageobjects.utility.PriceHelper;

public class CartPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titleCart");
    
    private SelenideElement cartTable = $("#cartOverviewTable");

    private ElementsCollection tableHead = $$(".product-name span");
    
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
    
    @Step("validate product table head")
    public void validateTableHead() 
    {
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.product"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.unitPrice"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.quantity"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.update"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.totalPrice"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate cart page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate process wrap
        //validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("CartPage.title"))).shouldBe(visible);
        
        // validate product table head
        validateTableHead();

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
    
    @Step("validate tax on cart page")
    public void validateTax(String shippingCosts, String subtotal) 
    {
        String tax = PriceHelper.calculateTax(shippingCosts, subtotal);
        $("#orderSubTotalTaxValue").shouldHave(exactText(tax));
    }

    @Step("validate cart page with shipping costs: '{shippingCosts}'")
    public void validate(String shippingCosts, String subtotal)
    {
        validateStructure();
        validateShippingCosts(shippingCosts);
        validateTax(shippingCosts, subtotal);
    }
    
    @Step("validate sub total and line item total after adding on the cart page")
    public void validateTotalAfterAdd(int position, String oldSubTotal, double oldTotalProductPrice)
    {
        /// ----- validate total unit price of specified product ----- ///

        SelenideElement productContainer = $("#product" + (position - 1));

        // store product unit price (without $ sign) for 1 unit
        String unitPrice = productContainer.find(".unitPriceShort").text();

        // store product count
        String quantity = $("#productCount" + (position - 1)).val();

        // calculate price of specified product
        String newTotalProductPrice = PriceHelper.totalProductPrice(unitPrice, quantity);

        // verify calculated unit price equals the displayed total unit price
        productContainer.find(".productTotalUnitPrice").shouldHave(exactText(newTotalProductPrice));

        /// ----- validate sub total ----- ///

        String newSubTotal = subTotal.text();

        // new total - old total = price of item you just added
        String productPrice = PriceHelper.substract(newSubTotal, oldSubTotal);

        // price difference for specific product after changing product amount
        String totalProductPriceChange = PriceHelper.substract(newTotalProductPrice, PriceHelper.format(oldTotalProductPrice));

        // validate {price} equals {price2}
        Assert.assertEquals(productPrice, totalProductPriceChange);
    }
    
    @Step("validate sub total and line item total after removing on the cart page")
    public void validateTotalAfterRemove(String oldSubTotal, String oldTotalProductPrice)
    {
        String newSubTotal = PriceHelper.substract(oldSubTotal, oldTotalProductPrice);
        subTotal.shouldHave(exactText(newSubTotal));
    }

    /// ----- validate product in cart ----- ///

    @Step("validate '{product}' on the cart page")
    private void validateCartItem(int position, String productName, String productStyle, String productSize, int productAmount, String productPrice)
    {
        // selector for product
        SelenideElement productContainer = $("#product" + (position - 1));

        // validate product image
        productContainer.find(".product-img").shouldBe(visible);
        
        // validate parameters
        productContainer.find(".productName").shouldHave(exactText(productName));
        productContainer.find(".productStyle").shouldHave(exactText(productStyle));
        productContainer.find(".productSize").shouldHave(exactText(productSize));
        productContainer.find(".productUnitPrice").shouldHave(exactText(productPrice));
        productContainer.find(".productCount").shouldHave(exactValue(Integer.toString(productAmount)));
        
        // validate remove and update button
        $("#btnRemoveProdCount" + (position - 1)).shouldBe(visible);
        $("#btnUpdateProdCount" + (position - 1)).shouldBe(visible);
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
    public String getProductTotalPrice(int position)
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
    
    @Step("open homepage from cart page")
    public HomePage openHomePage() 
    {
        $("#brand").scrollTo().click();
        return new HomePage().isExpectedPage();
    }
    
    @Step("open shipping address from the cart page")
    public ShippingAddressPage openShippingAddressPage()
    {
        $("#btnStartCheckout").scrollTo().click();
        return new ShippingAddressPage().isExpectedPage();
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

    // needed???
    private void clickCheckoutButton()
    {
        $("#btnStartCheckout").scrollTo().click();
    }

    @Step("check if there are product on the cart page")
    public boolean hasProductsInCart()
    {
        return $("#btnRemoveProdCount0").exists();
    }
}
