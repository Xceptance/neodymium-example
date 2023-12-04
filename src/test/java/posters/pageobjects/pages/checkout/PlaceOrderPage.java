package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.apache.commons.lang3.StringUtils;

import com.codeborne.selenide.ElementsCollection;
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
    
    private ElementsCollection headlines = $$(".checkout-overview-position h3");
    
    private ElementsCollection tableHead = $$(".order-overview-position th");

    private SelenideElement shippingAddressForm = $("#shippingAddr");

    private SelenideElement billingAddressForm = $("#billingAddr");

    private SelenideElement paymentForm = $("#payment");
    
    private ElementsCollection totalProductPrices = $$(".productUnitPrice");

    private SelenideElement subtotalContainer = $("#SubTotalValue");
    
    private SelenideElement taxContainer = $("#SubTotalTaxValue");
    
    private SelenideElement orderButton = $("#btnOrder");
    
    @Override
    @Step("ensure this is a place order page")
    public PlaceOrderPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate content place order page ----- ///
    
    // @Step("validate breadcrumb")
    // public void validateBreadcrumb()
    // {
    //     $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.cart"))).shouldBe(visible);
    //     $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.shippingAddress"))).shouldBe(visible);
    //     $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.billingAddress"))).shouldBe(visible);
    //     $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.payment"))).shouldBe(visible);
    //     $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.placeOrder"))).shouldBe(visible);
    // }
    
    @Step("validate product table head")
     public void validateTableHead() 
    {
        //tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.product"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.unitPrice"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.quantity"))).shouldBe(visible);
        tableHead.findBy(exactText(Neodymium.localizedText("General.productTable.totalPrice"))).shouldBe(visible);
    }
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        // validate process numbers
        $(".progress-step-1 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.number"))).shouldBe(visible);
        $(".progress-step-2 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.number"))).shouldBe(visible);
        $(".progress-step-3 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.number"))).shouldBe(visible);
        $(".progress-step-4 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.number"))).shouldBe(visible);
        $(".progress-step-5 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.number"))).shouldBe(visible);
        $(".progress-step-6 .progress-bubble").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.number"))).shouldBe(visible);
        
        // validate process names
        $(".progress-step-1 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.name"))).shouldBe(visible);
        $(".progress-step-2 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.name"))).shouldBe(visible);
        $(".progress-step-3 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.name"))).shouldBe(visible);
        $(".progress-step-4 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.name"))).shouldBe(visible);
        $(".progress-step-5 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.name"))).shouldBe(visible);
        $(".progress-step-6 .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.name"))).shouldBe(visible);

    }
    
    @Override
    @Step("validate place order page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        // validateBreadcrumb();
        
        // validate process wrap
        validateProcessWrap();
        
        // validate product table head
        validateTableHead();
        
        // validate order with costs button
        $("#btnOrder").shouldHave(exactText(Neodymium.localizedText("PlaceOrderPage.button"))).shouldBe(visible);
    }
    
    /// ----- validate order overview ----- ///
    
    private void validateShippingAddressOverview(Address shippingAddress, String headline) 
    {
        // validate headline
        headlines.findBy(exactText(headline)).shouldBe(visible);
        
        // validate name
        String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();
        shippingAddressForm.find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        
        // validate optional company name
        if (!StringUtils.isBlank(shippingAddress.getCompany()))
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
    
    private void validateBillingAddressOverview(Address billingAddress, String headline) 
    {
        // validate headline
        headlines.findBy(exactText(headline)).shouldBe(visible);
        
        // validate name
        String fullName = billingAddress.getFirstName() + " " + billingAddress.getLastName();
        billingAddressForm.find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        
        // validate optional company name
        if (!StringUtils.isBlank(billingAddress.getCompany()))
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
    
    private void validatePaymentOverview(CreditCard creditCard, String headline) 
    {
        // validate headline
        headlines.findBy(exactText(headline)).shouldBe(visible);
        
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
         validateShippingAddressOverview(shippingAddress, Neodymium.localizedText("PlaceOrderPage.overview.headlines.shippingAddress"));
        
        // validate billing address
         validateBillingAddressOverview(billingAddress, Neodymium.localizedText("PlaceOrderPage.overview.headlines.billingAddress"));
        
        // validate payment
         validatePaymentOverview(creditCard, Neodymium.localizedText("PlaceOrderPage.overview.headlines.payment"));
    }
    
    /// ----- validate products ----- ///
    
    private void validateProduct(int position, String productName, String productStyle, String productSize, int productAmount, String productPrice)
    {
        // selector for product
        SelenideElement productContainer = $("#product" + (position - 1));

        // validate product image
        productContainer.find(".img-thumbnail").shouldBe(visible);
        
        // validate parameters
        productContainer.find(".pName").shouldHave(exactText(productName));
        productContainer.find(".productStyle span").shouldHave(exactText(productStyle));
        productContainer.find(".productSize span").shouldHave(exactText(productSize));
        productContainer.find(".productUnitPrice").shouldHave(exactText(productPrice));
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
        return subtotalContainer.text();
    }
    
    @Step("get tax costs")
    public String getTax() 
    {
        return taxContainer.text();
    }
    
    /**
     * Note: Loops through all total product prices on the place order page and adds it to the "subtotal" variable.
     * 
     * @return subtotal (The sum of all total product prices)
     */
    @Step("calculate sum of all total product prices")
    public String calculateSubtotal() 
    {
        double subtotal = 0;
        
        for (SelenideElement totalProductPrice : totalProductPrices) 
        {
            subtotal = PriceHelper.calculateSubtotalPlaceOrderPage(subtotal, totalProductPrice.getText());
        }

        return PriceHelper.format(subtotal);
    }
    
    /// ----- validate price summary ----- ///
    
    @Step("validate description strings")
    public void validateDescriptionStrings() 
    {
        $$(".price-summary-row .price-summary-position").findBy(matchText(Neodymium.localizedText("General.priceSummary.subtotal"))).shouldBe(visible);
        $$(".price-summary-row .price-summary-position").findBy(text(Neodymium.localizedText("General.priceSummary.shipping"))).shouldBe(visible);
        $$(".price-summary-row .price-summary-position").findBy(text(Neodymium.localizedText("General.priceSummary.tax"))).shouldBe(visible);
        $$(".price-summary-row .price-summary-position").findBy(text(Neodymium.localizedText("General.priceSummary.grandTotal"))).shouldBe(visible);
    }
    
    @Step("validate price summary")
    public void validatePriceSummary(String subtotal, String shippingCosts) 
    {
        // validate title
        $$(".price-summary-row").findBy(text(Neodymium.localizedText("General.priceSummary.title"))).shouldBe(visible);
        
        // validate descriptions
        validateDescriptionStrings();
        
        // validate subtotal
        subtotalContainer.shouldHave(exactText(calculateSubtotal()));
        
        // validate shipping costs
        $("#shippingCosts").shouldHave(exactText(shippingCosts));
       
        // validate tax
        taxContainer.shouldHave(exactText(PriceHelper.calculateTax(shippingCosts, subtotal)));
        
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
}
