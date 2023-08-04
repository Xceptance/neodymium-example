package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.apache.commons.lang3.StringUtils;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.tests.testdata.dataobjects.Product;
import posters.pageobjects.utility.PriceHelper;

public class PlaceOrderPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titleOrderOverview");
    
    private ElementsCollection headlines = $$(".total-wrap h3");
    
    private ElementsCollection tableHead = $$(".product-name span");

    private SelenideElement shippingAddressForm = $("#shippingAddr");

    private SelenideElement billingAddressForm = $("#billingAddr");

    private SelenideElement paymentForm = $("#payment");
    
    private ElementsCollection totalProductPrices = $$(".totalUnitPriceShort");

    private SelenideElement orderButton = $("#btnOrder");
    
    @Override
    @Step("ensure this is a place order page")
    public PlaceOrderPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate structure ----- ///
    
    @Step("validate product table head")
    public void validateTableHead() 
    {
        //tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.product"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.unitPrice"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.quantity"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.totalPrice"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate place order page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();
        
        // validate process wrap
        //validateProcessWrap();
        
        // validate product table head
        validateTableHead();
        
        // validate order with costs button
        $("#btnOrder").shouldHave(exactText(Neodymium.localizedText("PlaceOrderPage.button"))).shouldBe(visible);
    }
    
    /// ----- validate order overview ----- ///
    
    @Step("validate shipping address overview")
    public void validateShippingAddressOverview(Address shippingAddress, String headline) 
    {
        // validate headline
        headlines.findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
        
        // validate name
        String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();
        shippingAddressForm.find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        
        // TODO - fix, so it also works for empty string/ null
        // validate optional company name
        if (!StringUtils.isBlank(shippingAddress.getCompany()));
        {
            shippingAddressForm.find(".company").shouldHave(exactText(shippingAddress.getCompany())).shouldBe(visible);
        }
        
        // validate address
        shippingAddressForm.find(".addressLine").shouldHave(exactText(shippingAddress.getStreet())).shouldBe(visible);
        
        // validate city, state, zip
        shippingAddressForm.find(".city").shouldHave(exactText(shippingAddress.getCity())).shouldBe(visible);
        shippingAddressForm.find(".state").shouldHave(exactText(shippingAddress.getState())).shouldBe(visible);
        shippingAddressForm.find(".zip").shouldHave(exactText(shippingAddress.getZip())).shouldBe(visible);
        
        // validate country
        shippingAddressForm.find(".country").shouldHave(exactText(shippingAddress.getCountry())).shouldBe(visible);
    }
    
    @Step("validate billing address overview")
    public void validateBillingAddressOverview(Address billingAddress, String headline) 
    {
        // validate headline
        headlines.findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
        
        // validate name
        String fullName = billingAddress.getFirstName() + " " + billingAddress.getLastName();
        billingAddressForm.find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        
        // TODO - fix, so it also works for empty string/ null
        // validate optional company name
        if (!StringUtils.isBlank(billingAddress.getCompany()));
        {
            billingAddressForm.find(".company").shouldHave(exactText(billingAddress.getCompany())).shouldBe(visible);
        }
        
        // validate address
        billingAddressForm.find(".addressLine").shouldHave(exactText(billingAddress.getStreet())).shouldBe(visible);
        
        // validate city, state, zip
        billingAddressForm.find(".city").shouldHave(exactText(billingAddress.getCity())).shouldBe(visible);
        billingAddressForm.find(".state").shouldHave(exactText(billingAddress.getState())).shouldBe(visible);
        billingAddressForm.find(".zip").shouldHave(exactText(billingAddress.getZip())).shouldBe(visible);
        
        // validate country
        billingAddressForm.find(".country").shouldHave(exactText(billingAddress.getCountry())).shouldBe(visible);
    }
    
    @Step("validate payment overview")
    public void validatePaymentOverview(CreditCard creditCard, String headline) 
    {
        // validate headline
        headlines.findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
        
        // validate name
        paymentForm.find(".name").shouldHave(exactText(creditCard.getFullName())).shouldBe(visible);
        
        // validate censored card number
        paymentForm.find(".cardNumber").shouldHave(exactText(creditCard.getCrypticCardNumber())).shouldBe(visible);
        
        // validate expiration date
        paymentForm.find(".month").shouldHave(exactText(creditCard.getExpDateMonth())).shouldBe(visible);
        paymentForm.find(".year").shouldHave(exactText(creditCard.getExpDateYear())).shouldBe(visible);
    }
    
    @Step("validate order overview")
    public void validateOrderOverview(Address shippingAddress, Address billingAddress, CreditCard creditCard) 
    {
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("PlaceOrderPage.overview.title"))).shouldBe(visible);
        
        // validate shipping address
        validateShippingAddressOverview(shippingAddress, "PlaceOrderPage.overview.headlines.shippingAddress");
        
        // validate billing address
        validateBillingAddressOverview(billingAddress, "PlaceOrderPage.overview.headlines.billingAddress");
        
        // validate payment
        validatePaymentOverview(creditCard, "PlaceOrderPage.overview.headlines.payment");
    }
    
    /// ----- validate products ----- ///
    
    @Step("validate '{product}' on the place order page")
    private void validateProduct(int position, String productName, String productStyle, String productSize, int productAmount, String productPrice)
    {
        // selector for product
        SelenideElement productContainer = $("#product" + (position - 1));

        // validate product image
        productContainer.find(".product-img").shouldBe(visible);
        
        // validate parameters
        productContainer.find(".pName").shouldHave(exactText(productName));
        productContainer.find(".productStyle").shouldHave(exactText(productStyle));
        productContainer.find(".productSize").shouldHave(exactText(productSize));
        // TODO - fix to TA doesn'T break
        //productContainer.find(".productUnitPrice").shouldHave(value(productPrice));
        productContainer.find(".productCount").shouldHave(exactValue(Integer.toString(productAmount)));
    }
    
    @Step("validate '{product}' on the place order page")
    public void validateProduct(int position, Product product)
    {
        validateProduct(position, product.getName(), product.getStyle(), product.getSize(), product.getAmount(), product.getUnitPrice());
    }
    
    /// ----- get price summary information ----- ///
    
    @Step("get sum of all total product prices")
    public String getSubtotal() 
    {
        return $("#SubTotalValue").text();
    }
    
    @Step("get tax costs")
    public String getTax() 
    {
        return $("#SubTotalTaxValue").text();
    }
    
    /// ----- validate price summary ----- ///
    
    @Step("calculate sum of all total product prices")
    public String calculateSubtotal() 
    {
        double subtotal = 0;
        
        for (SelenideElement totalProductPrice : totalProductPrices) 
        {
            subtotal = PriceHelper.calculateSubtotal(subtotal, totalProductPrice.getText());
        }

        return PriceHelper.format(subtotal);
    }
    
    @Step("validate description strings")
    public void validateDescriptionStrings() 
    {
        $$(".sub p").findBy(matchText(Neodymium.localizedText("General.priceSummary.subtotal"))).shouldBe(visible);
        $$(".sub p").findBy(text(Neodymium.localizedText("General.priceSummary.shipping"))).shouldBe(visible);
        $$(".sub p").findBy(text(Neodymium.localizedText("General.priceSummary.tax"))).shouldBe(visible);
        $$(".grand-total p").findBy(text(Neodymium.localizedText("General.priceSummary.grandTotal"))).shouldBe(visible);
    }
    
    @Step("validate price summary")
    public void validatePriceSummary(String subtotal, String shippingCosts) 
    {
        // validate title
        $$(".sub").findBy(text(Neodymium.localizedText("General.priceSummary.title"))).shouldBe(visible);
        
        // validate descriptions
        validateDescriptionStrings();
        
        // validate subtotal
        $("#SubTotalValue").shouldHave(exactText(calculateSubtotal()));
        
        // validate shipping costs
        $("#shippingCosts").shouldHave(exactText(shippingCosts));
       
        // validate tax
        $("#SubTotalTaxValue").shouldHave(exactText(PriceHelper.calculateTax(shippingCosts, subtotal)));
        
        // validate grand total
        $("#orderTotal").shouldHave(exactText(PriceHelper.calculateGrandTotal(subtotal, shippingCosts, getTax())));
    }
    
    /// ----- place order page navigation ----- ///
   
    @Step("place the order")
    public OrderConfirmationPage placeOrder()
    {
        // click on "Order with costs" button
        orderButton.scrollTo().click();

        return new OrderConfirmationPage().isExpectedPage();
    }
    
    // --------------------------------------------------------------

    @Step("validate order contains product '{product.name}'")
    public void validateContainsProduct(Product product)
    {
        SelenideElement productContainer = $$("div.hidden-xs").filter((matchText(product.getRowRegex()))).shouldHaveSize(1).first()
                                                              .parent().parent();

        productContainer.find(".pName").shouldHave(exactText(product.getName()));
        productContainer.find(".pSize").shouldHave(exactText(product.getSize()));
        productContainer.find(".pStyle").shouldHave(exactText(product.getStyle()));
        productContainer.find(".pCount").shouldHave(exactText(Integer.toString(product.getAmount())));
        productContainer.find(".pPrice").shouldHave(exactText(product.getUnitPrice()));
        productContainer.find(".productLineItemPrice").shouldHave(exactText(PriceHelper.format(product.getTotalPrice())));
    }
}
