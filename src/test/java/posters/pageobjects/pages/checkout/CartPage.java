package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.junit.Assert;

import com.codeborne.selenide.ClickOptions;
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
    private SelenideElement title = $(".container h2");

    private SelenideElement cartTable = $(".table");

    private ElementsCollection tableHead = $$(".coulmn-name th");

    private SelenideElement subTotal = $("#orderSubTotalValue");

    @Override
    @Step("ensure this is a cart page")
    public CartPage isExpectedPage()
    {
        super.isExpectedPage();
        title.shouldBe(visible);
        return this;
    }

    /// ========== validate content cart page ========== ///

    @Step("validate product table head")
    public void validateTableHead()
    {
        tableHead.findBy(exactText(Neodymium.localizedText("cartPage.number"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("product.title"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("product.unitPrice"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("product.quantity"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("cartPage.update"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("product.totalPrice"))).shouldBe(visible);
    }

    @Override
    @Step("validate cart page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("cartPage.title"))).shouldBe(visible);

        // check if cart contains items, validate
        if (header.miniCart.getTotalCount() == 0)
        {
            $("#errorCartMessage").shouldHave(exactText(Neodymium.localizedText("errorMessage.emptyCart"))).shouldBe(visible);
        }
        else
        {

            // validate product table head
            validateTableHead();

            // validate product list
            cartTable.shouldBe(visible);

            // validate at least 1 product in product list
            $("#product0").shouldBe(visible);

            // validate cart summary list
            $(".price-summary-row").shouldBe(visible);

            // validate checkout button
            $("#btnStartCheckout").should(visible);
        }
    }

    @Step("validate shipping costs '{shippingCosts}' on cart page")
    public void validateShippingCosts(String shippingCosts)
    {
        $("#priceShippingValue").shouldHave(exactText(shippingCosts));
    }

    @Step("validate tax on cart page")
    public void validateTax(String shippingCosts, String subtotal)
    {
        String tax = PriceHelper.calculateTax(shippingCosts, subtotal);
        $("#orderSubTotalTaxValue").shouldHave(exactText(tax));
    }

    @Step("validate cart page")
    public void validate(String shippingCosts, String subtotal)
    {
        validateStructure();
        validateShippingCosts(shippingCosts);
        validateTax(shippingCosts, subtotal);
    }

    /**
     * Note: It checks if the price change of subtotal is equal to the price change of the product.
     * 
     * @param position (of specific product in cart)
     * @param oldSubTotal (subtotal before adding new product to cart/ increasing product quantity)
     * @param oldTotalProductPrice (product price before adding/ increasing product quantity)
     */
    @Step("validate sub total and line item total after adding on the cart page")
    public void validateTotalAfterAdd(int position, String oldSubTotal, double oldTotalProductPrice)
    {
        /// ========== validate total unit price of specified product ========== ///

        SelenideElement productContainer = $("#product" + (position - 1));

        // store product unit price (without $ sign) for 1 unit
        String unitPrice = productContainer.find(".product-unit-price").text();

        // store product count
        String quantity = $("#productCount" + (position - 1)).val();

        // calculate price of specified product
        String newTotalProductPrice = PriceHelper.totalProductPrice(unitPrice, quantity);

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
    public void validateTotalAfterRemove(String oldSubTotal, String oldTotalProductPrice)
    {
        String newSubTotal = PriceHelper.substract(oldSubTotal, oldTotalProductPrice);
        subTotal.shouldHave(exactText(newSubTotal));
    }

    /// ========== validate product in cart ========== ///

    private void validateCartItem(int position, String productName, String productStyle, String productSize, int productAmount, String productPrice)
    {
        // selector for product
        SelenideElement productContainer = $("#product" + (position - 1));

        // validate product image
        productContainer.find(".img-thumbnail").shouldBe(visible);

        // validate parameters
        productContainer.find(".product-name").shouldHave(exactText(productName));
        productContainer.find(".product-style").shouldHave(exactText(productStyle));
        productContainer.find(".product-size").shouldHave(exactText(productSize));
        productContainer.find(".product-unit-price").shouldHave(exactText(productPrice));
        productContainer.find(".product-count").shouldHave(exactValue(Integer.toString(productAmount)));

        // validate remove and update button
        $("#btnRemoveProdCount" + (position - 1)).shouldBe(visible);
        $("#btnUpdateProdCount" + (position - 1)).shouldBe(visible);
    }

    @Step("validate '{product}' on the cart page")
    public void validateCartItem(int position, Product product)
    {
        validateCartItem(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), product.getUnitPrice());
    }

    @Step("validate '{product}' on the cart page after changing it's quantity")
    public void validateCartItem(int position, Product product, int productAmount)
    {
        validateCartItem(position, product.getName(), product.getStyle(), product.getSize(), productAmount, product.getUnitPrice());
    }

    /// ========== get product data ========== ///

    @Step("get product name from line item on position '{position}' on the cart page")
    public String getProductName(int position)
    {
        return $("#product" + (position - 1) + " .product-name").text();
    }

    @Step("get product style from line item on position '{position}' on the cart page")
    public String getProductStyle(int position)
    {
        return $("#product" + (position - 1) + " .product-style").text();
    }

    @Step("get product size from line item on position '{position}' on the cart page")
    public String getProductSize(int position)
    {
        return $("#product" + (position - 1) + " .product-size").text();
    }

    @Step("get product count from line item on position '{position}' on the cart page")
    public String getProductCount(int position)
    {
        return $("#product" + (position - 1) + " .product-count").val();
    }

    @Step("get product unit price from line item on position '{position}' on the cart page")
    public String getProductUnitPrice(int position)
    {
        return $("#product" + (position - 1) + " .product-unit-price").text();
    }

    @Step("get product total price from line item on position '{position}' on the cart page")
    public String getProductTotalPrice(int position)
    {
        return $("#product" + (position - 1) + " .product-total-unit-price").text();
    }

    @Step("get product from line item on position '{position}' on the cart page")
    public Product getProduct(int position)
    {
        return new Product(getProductName(position), getProductUnitPrice(position), getProductStyle(position), getProductSize(position), Integer.parseInt(getProductCount(position)));
    }

    /// ========== update product data ========== ///

    @Step("update product count of product on position '{position}' on the cart page to '{amount}'")
    public void updateProductCount(int position, int amount)
    {
        SelenideElement productContainer = $("#product" + (position - 1));

        // type new amount
        productContainer.find(".product-count").setValue(Integer.toString(amount));

        // click update button
        productContainer.find(".btnUpdateProduct").click(ClickOptions.usingJavaScript());
    }

    @Step("remove product on position '{position}' on the cart page")
    public void removeProduct(int position)
    {
        // click delete button
        $("#btnRemoveProdCount" + (position - 1)).click(ClickOptions.usingJavaScript());

        // click delete confirmation button
        $("#buttonDelete").click(ClickOptions.usingJavaScript());
    }

    /// ========== cart page navigation ========== ///

    @Step("click on a product on position '{position}' on the cart page")
    public ProductDetailPage openProductDetailPage(int position)
    {
        $("#product" + (position - 1) + " .img-thumbnail").click(ClickOptions.usingJavaScript());
        return new ProductDetailPage().isExpectedPage();
    }

    @Step("open homepage from cart page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }

    @Step("open guest shipping address from the cart page")
    public GuestShippingAddressPage openGuestShippingAddressPage()
    {
        $("#btnStartCheckout").click(ClickOptions.usingJavaScript());
        return new GuestShippingAddressPage().isExpectedPage();
    }

    @Step("open returning customer shipping address from the cart page")
    public ReturningCustomerShippingAddressPage openReturningCustomerShippingAddressPage()
    {
        $("#btnStartCheckout").click(ClickOptions.usingJavaScript());
        return new ReturningCustomerShippingAddressPage().isExpectedPage();
    }
}
