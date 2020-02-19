/**
 * 
 */
package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.dataobjects.Address;
import posters.dataobjects.CreditCard;
import posters.dataobjects.OrderData;
import posters.dataobjects.Product;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.utility.PriceHelper;

/**
 * @author pfotenhauer
 */
public class PlaceOrderPage extends AbstractCheckoutPage
{
    private SelenideElement headline = $("#titleOrderOverview");

    private SelenideElement shippingAddressForm = $("#shippingAddr");

    private SelenideElement billingAddressForm = $("#billingAddr");

    private SelenideElement paymentForm = $("#payment");

    private SelenideElement orderButton = $("#btnOrder");

    @Override
    @Step("ensure this is a place order page")
    public PlaceOrderPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Override
    @Step("validate place order page structure")
    public PlaceOrderPage validateStructure()
    {
        super.validateStructure();

        // Headline
        // Headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        shippingAddressForm.shouldBe(visible);
        billingAddressForm.shouldBe(visible);
        paymentForm.shouldBe(visible);
        orderButton.shouldBe(visible);
        return this;
    }

    /**
     * @param position
     *            The position of the product you want to check
     * @param productName
     *            The Product name of that product
     * @param productCount
     *            The amount
     * @param productStyle
     *            The style
     * @param productSize
     *            The size
     */
    @Step("validate order contains product \"{product.name}\"")
    public PlaceOrderPage validateContainsProduct(Product product)
    {
        SelenideElement productContainer = $$("div.hidden-xs").filter((matchText(product.getRowRegex()))).shouldHaveSize(1).first()
                                                              .parent().parent();

        productContainer.find(".pName").shouldHave(exactText(product.getName()));
        productContainer.find(".pSize").shouldHave(exactText(product.getSize()));
        productContainer.find(".pStyle").shouldHave(exactText(product.getStyle()));
        productContainer.find(".pCount").shouldHave(exactText(Integer.toString(product.getAmount())));
        productContainer.find(".pPrice").shouldHave(exactText(product.getUnitPrice()));
        productContainer.find(".productLineItemPrice").shouldHave(exactText(PriceHelper.format(product.getTotalPrice())));
        return this;
    }

    @Step("validate subtotal on the place order page")
    public PlaceOrderPage validateSubtotal(String subtotal)
    {
        $$("#checkoutSummaryList li").findBy(text("Subtotal")).find(".text-right").shouldBe(exactText(subtotal));
        return this;
    }

    @Step("validate addresses and payment on place order page")
    public PlaceOrderPage validateAddressesAndPayment(Address shippingAddress, Address billingAddress, CreditCard creditcard)
    {
        // Shipping address
        // Name
        // Makes sure the shipping address name matches the parameter
        shippingAddressForm.find(".name").shouldHave(exactText(shippingAddress.getFullName()));
        // Company
        // Makes sure the shipping address company matches the parameter
        shippingAddressForm.find(".company").shouldHave(exactText(shippingAddress.getCompany()));
        // Address
        // Makes sure the shipping address matches the parameter
        shippingAddressForm.find(".addressLine").shouldHave(exactText(shippingAddress.getAddressLine()));
        // City
        // Makes sure the shipping address city matches the parameter
        shippingAddressForm.find(".city").shouldHave(exactText(shippingAddress.getCity()));
        // State
        // Makes sure the shipping address state matches the parameter
        shippingAddressForm.find(".state").shouldHave(exactText(shippingAddress.getState()));
        // ZIP
        // Makes sure the shipping address ZIP matches the parameter
        shippingAddressForm.find(".zip").shouldHave(exactText(" " + shippingAddress.getZip()));
        // Country
        // Makes sure the shipping address country matches the parameter
        shippingAddressForm.find(".country").shouldHave(exactText(shippingAddress.getCountry()));
        if (billingAddress != null)
        {
            // Billing address
            // Name
            // Makes sure the billing address name matches the parameter
            billingAddressForm.find(".name").shouldHave(exactText(billingAddress.getFullName()));
            // Company
            // Makes sure the billing address company matches the parameter
            billingAddressForm.find(".company").shouldHave(exactText(billingAddress.getCompany()));
            // Address
            // Makes sure the billing address matches the parameter
            billingAddressForm.find(".addressLine").shouldHave(exactText(billingAddress.getAddressLine()));
            // City
            // Makes sure the billing address city matches the parameter
            billingAddressForm.find(".city").shouldHave(exactText(billingAddress.getCity()));
            // State
            // Makes sure the billing address state matches the parameter
            billingAddressForm.find(".state").shouldHave(exactText(billingAddress.getState()));
            // ZIP
            // Makes sure the billing address ZIP matches the parameter
            billingAddressForm.find(".zip").shouldHave(exactText(billingAddress.getZip()));
            // Country
            // Makes sure the billing address country matches the parameter
            billingAddressForm.find(".country").shouldHave(exactText(billingAddress.getCountry()));
        }
        // Payment
        // Name
        // Makes sure the credit card holder matches the parameter
        paymentForm.find(" .name .value").shouldHave(exactText(creditcard.getFullName()));
        // Credit Card Number
        // Makes sure the anonymized credit card number matches the parameter
        paymentForm.find(" .cardNumber .value").shouldHave(exactText(creditcard.getCrypticCardNumber()));
        // Expiration
        // Makes sure the credit card expiration month matches the parameter
        paymentForm.find(" .exp .month").shouldHave(exactText(creditcard.getExpDateMonth()));
        // Makes sure the credit card expiration year matches the parameter
        paymentForm.find(" .exp .year").shouldHave(exactText(creditcard.getExpDateYear()));
        return this;
    }

    @Step("place the order")
    public HomePage placeOrder()
    {
        // Opens the homepage
        // Clicks the Order button
        orderButton.scrollTo().click();
        HomePage homePage = new HomePage();
        homePage.isExpectedPage();
        return homePage;
    }

    @Step("validate shipping costs are equal {shippingCosts}")
    public PlaceOrderPage validateShippingCosts(String shippingCosts)
    {
        $("#shippingCosts").shouldHave(exactText(shippingCosts));
        return this;
    }

    @Step("validate order tax value matches {taxValue}")
    public PlaceOrderPage validateOrderTax(String taxValue)
    {
        $("#subTotalTaxValue").shouldHave(exactText(taxValue));
        return this;
    }

    @Step("validate order total price is {orderTotalPrice}")
    public PlaceOrderPage validateOrderTotal(String orderTotalPrice)
    {
        $("#totalCosts").shouldHave(exactText(orderTotalPrice));
        return this;
    }

    @Step("valiadate order data on place order page")
    public PlaceOrderPage validateOrder(OrderData orderData)
    {
        orderData.getProducts().getAll().forEach(product -> validateContainsProduct(product));
        validateAddressesAndPayment(orderData.getShippingAddress(), orderData.getBillingAddress(), orderData.getPayment());
        validateSubtotal(orderData.getProducts().getProductsTotalPrice());
        validateShippingCosts(orderData.getShippingCost());
        validateOrderTax(PriceHelper.format(orderData.getOrderTax()));
        validateOrderTotal(orderData.getOrderTotal());
        return this;
    }
}
