package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import org.apache.commons.lang3.StringUtils;

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

    private SelenideElement shippingAddressForm = $("#shippingAddr");

    private SelenideElement billingAddressForm = $("#billingAddr");

    private SelenideElement paymentForm = $("#payment");

    private SelenideElement orderButton = $("#btnOrder");
    
    @Override
    @Step("ensure this is a place order page")
    public PlaceOrderPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate place order page ----- ///

    @Step("validate breadcrumb")
    public void validateBreadcrumb()
    {
        $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.cart"))).shouldBe(visible);
        $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.shippingAddress"))).shouldBe(visible);
        $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.billingAddress"))).shouldBe(visible);
        $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.payment"))).shouldBe(visible);
        $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.placeOrder"))).shouldBe(visible);
    }
    
    @Step("validate shipping address overview")
    public void validateShippingAddressOverview(Address shippingAddress, String headline) 
    {
        // validate headline
        $$(".total-wrap h3").findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
        
        // validate name
        String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();
        $("#shippingAddr").find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        
        // TODO - fix, change company in .json to null/empty String
        // validate optional company name
        if (!StringUtils.isBlank(shippingAddress.getCompany()));
        {
            $("#shippingAddr").find(".company").shouldHave(exactText(shippingAddress.getCompany())).shouldBe(visible);
        }
        
        // validate address
        $("#shippingAddr").find(".addressLine").shouldHave(exactText(shippingAddress.getStreet())).shouldBe(visible);
        
        // validate city, state, zip
        $("#shippingAddr").find(".city").shouldHave(exactText(shippingAddress.getCity())).shouldBe(visible);
        $("#shippingAddr").find(".state").shouldHave(exactText(shippingAddress.getState())).shouldBe(visible);
        $("#shippingAddr").find(".zip").shouldHave(exactText(shippingAddress.getZip())).shouldBe(visible);
        
        // validate country
        $("#shippingAddr").find(".country").shouldHave(exactText(shippingAddress.getCountry())).shouldBe(visible);
    }
    
    @Step("validate billing address overview")
    public void validateBillingAddressOverview(Address billingAddress, String headline) 
    {
        // validate headline
        $$(".total-wrap h3").findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
        
        // validate name
        String fullName = billingAddress.getFirstName() + " " + billingAddress.getLastName();
        $("#billingAddr").find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        
        // TODO - fix, change company in .json to null/empty String
        // validate optional company name
        if (!StringUtils.isBlank(billingAddress.getCompany()));
        {
            $("#billingAddr").find(".company").shouldHave(exactText(billingAddress.getCompany())).shouldBe(visible);
        }
        
        // validate address
        $("#billingAddr").find(".addressLine").shouldHave(exactText(billingAddress.getStreet())).shouldBe(visible);
        
        // validate city, state, zip
        $("#billingAddr").find(".city").shouldHave(exactText(billingAddress.getCity())).shouldBe(visible);
        $("#billingAddr").find(".state").shouldHave(exactText(billingAddress.getState())).shouldBe(visible);
        $("#billingAddr").find(".zip").shouldHave(exactText(billingAddress.getZip())).shouldBe(visible);
        
        // validate country
        $("#billingAddr").find(".country").shouldHave(exactText(billingAddress.getCountry())).shouldBe(visible);
    }
    
    @Step("validate payment overview")
    public void validatePaymentOverview(CreditCard creditCard, String headline) 
    {
        // validate headline
        $$(".total-wrap h3").findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
        
        // validate name
        $("#payment").find(".name").shouldHave(exactText(creditCard.getFullName())).shouldBe(visible);
        
        // validate censored card number
        $("#payment").find(".cardNumber").shouldHave(exactText(creditCard.getCrypticCardNumber())).shouldBe(visible);
        
        // validate expiration date
        $("#payment").find(".month").shouldHave(exactText(creditCard.getExpDateMonth())).shouldBe(visible);
        $("#payment").find(".year").shouldHave(exactText(creditCard.getExpDateYear())).shouldBe(visible);
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
    
    @Override
    @Step("validate place order page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();
    }
    
    // --------------------------------------------------------------

    /**
     * @param product
     *            The product
     */
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

    @Step("validate subtotal on the place order page")
    public void validateSubtotal(String subtotal)
    {
        $$("#checkoutSummaryList li").findBy(text("Subtotal")).find(".text-right").shouldBe(exactText(subtotal));
    }

    @Step("validate product '{productName}' on place order page")
    public void validateProduct(int position, String productName, int productCount, String productStyle, String productSize)
    {
        final int index = position - 1;
        // Item info evaluation
        // The product at index @{index} exists
        SelenideElement productContainer = $("#checkoutOverviewTable #product" + index);
        productContainer.should(exist);
        // Name
        // The name equals the parameter
        productContainer.find(".pName").shouldHave(exactText(productName));
        // Amount
        // The amount equals the parameter
        //String Val = productContainer.find(".productCount").getAttribute("value");
       
        productContainer.find(".productCount").shouldHave(value(Integer.toString(productCount)));
        // Style
        // The style equals the parameter
        productContainer.find(".productStyle").shouldHave(exactText(productStyle));
        // Size
        // The size equals the parameter
        productContainer.find(".productSize").shouldHave(exactText(productSize));
    }

    @Step("get order total costs from place order page")
    public String getTotalCosts()
    {
        return $("#totalCosts").text();
    }

    @Step("place the order")
    public OrderConfirmationPage placeOrder()
    {
        // Opens the OrderConfirmationPage
        // Clicks the Order button
        orderButton.scrollTo().click();

        return new OrderConfirmationPage().isExpectedPage();
        
        
    }

   

}
