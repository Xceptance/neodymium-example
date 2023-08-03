package posters.pageobjects.pages.checkout;

import java.time.LocalDate;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.CreditCard;

public class PaymentPage extends AbstractCheckoutPage
{
    private SelenideElement headline = $("#titlePayment");

    private SelenideElement creditCardNumber = $("#creditCardNumber");

    private SelenideElement creditCardName = $("#name");

    private SelenideElement expirationMonth = $("#expirationDateMonth");

    private SelenideElement expirationYear = $("#expirationDateYear");

    private SelenideElement addPaymentButton = $("#btnAddPayment");
    

    @Override
    @Step("ensure this is a payment page")
    public PaymentPage isExpectedPage()
    {
        super.isExpectedPage();
        headline.should(exist);
        return this;
    }

    /// ----- validate payment page ----- ///

    @Step("validate breadcrumb")
    public void validateBreadcrumb()
    {
        $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.cart"))).shouldBe(visible);
        $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.shippingAddress"))).shouldBe(visible);
        $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.billingAddress"))).shouldBe(visible);
        $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.payment"))).shouldBe(visible);
        $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.placeOrder"))).shouldBe(visible);
    }
    
    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines(String headline)
    {
        $$(".form-group label").findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines("PaymentPage.fillIn.headlines.cardNumber");
        validateFillInHeadlines("PaymentPage.fillIn.headlines.cardHolderName");
        validateFillInHeadlines("PaymentPage.fillIn.headlines.expirationDate");
    }
    
    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {        
        creditCardNumber.shouldHave(attribute("placeholder", (Neodymium.localizedText("PaymentPage.fillIn.placeholder.cardNumber")))).shouldBe(visible);
        creditCardName.shouldHave(attribute("placeholder", (Neodymium.localizedText("PaymentPage.fillIn.placeholder.cardOwnerName")))).shouldBe(visible);
        $("#expirationDateMonth [selected]").shouldHave(exactText("0" + Integer.toString(LocalDate.now().getMonthValue()))).shouldBe(visible);
        $("#expirationDateYear [selected]").shouldHave(exactText(Integer.toString(LocalDate.now().getYear()))).shouldBe(visible);
    }
    
    @Step("validate month dropdown")
    public void validateMonthDropdown(String month) 
    {
        $$("#expirationDateMonth").findBy(matchText(Neodymium.localizedText(month))).shouldBe(visible);
    }
    
    @Step("validate month dropdown")
    public void validateMonthDropdown() 
    {
        // open dropdown
        $("#expirationDateMonth").scrollTo().click();
        
        // validate months
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.january");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.february");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.march");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.april");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.may");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.june");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.july");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.august");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.september");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.october");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.november");
        validateMonthDropdown("PaymentPage.fillIn.expireMonth.december");
    }
    
    @Step("validate year dropdown")
    public void validateYearDropdown(String year) 
    {
        $$("#expirationDateYear").findBy(matchText(Neodymium.localizedText(year))).shouldBe(visible);
    }
    
    @Step("validate year dropdown")
    public void validateYearDropdown() 
    {        
        // open dropdown
        $("#expirationDateYear").scrollTo().click();
        
        // validate years
        validateYearDropdown("PaymentPage.fillIn.expireYear.2023");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2024");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2025");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2026");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2027");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2028");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2029");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2030");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2031");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2032");
        validateYearDropdown("PaymentPage.fillIn.expireYear.2033");
    }
    
    @Override
    @Step("validate payment page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate breadcrumb
        validateBreadcrumb();
        
        // validate fill in form headline
        headline.shouldHave(exactText(Neodymium.localizedText("PaymentPage.fillIn.title"))).shouldBe(visible);
        validateFillInHeadlines();
        
        // validate placeholder
        validateFillInPlaceholder();
        
        // validate month dropdown
        validateMonthDropdown();
        
        // validate year dropdown
        validateYearDropdown();
    }
    
    /// ----- send payment form ----- ///
    
    @Step("fill and send payment form")
    public PlaceOrderPage sendPaymentForm(CreditCard creditcard)
    {
        return sendPaymentForm(creditcard.getCardNumber(), creditcard.getFullName(), creditcard.getExpDateMonth(), creditcard.getExpDateYear());
    }
    
    @Step("fill and send payment form")
    public PlaceOrderPage sendPaymentForm(String number, String name, String month, String year)
    {
        // enter parameters
        creditCardNumber.val(number);
        creditCardName.val(name);
        expirationMonth.selectOption(month);
        expirationYear.selectOption(year);

        // click on "Continue" button
        addPaymentButton.scrollTo().click();

        return new PlaceOrderPage().isExpectedPage();
    }
    
    // --------------------------------------------------------

    /**
     * @param position
     *            The position of the credit card you want to select
     * @return PPlaceOrder
     */
    @Step("select a payment")
    public PlaceOrderPage selectCreditCard(int position)
    {
        final int index = position - 1;
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#payment" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUsePayment").scrollTo().click();

        return new PlaceOrderPage().isExpectedPage();
    }
}
