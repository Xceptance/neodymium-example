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

public class GuestPaymentPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titlePayment");

    private SelenideElement creditCardNumber = $("#creditCardNumber");

    private SelenideElement creditCardName = $("#name");

    private SelenideElement expirationMonth = $("#expirationDateMonth");

    private SelenideElement expirationYear = $("#expirationDateYear");

    private SelenideElement addPaymentButton = $("#btnAddPayment");
    
    @Override
    @Step("ensure this is a payment page")
    public GuestPaymentPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate content guest payment page ----- ///
    
    @Step("validate breadcrumb")
    public void validateBreadcrumb()
    {
        $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.cart"))).shouldBe(visible);
        $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.shippingAddress"))).shouldBe(visible);
        $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.billingAddress"))).shouldBe(visible);
        $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.payment"))).shouldBe(visible);
        $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.placeOrder"))).shouldBe(visible);
    }
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        // validate process numbers
        $("#crt span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.number"))).shouldBe(visible);
        $("#ship span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.number"))).shouldBe(visible);
        $("#bill span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.number"))).shouldBe(visible);
        $("#payment span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.number"))).shouldBe(visible);
        $("#chkout span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.number"))).shouldBe(visible);
        $("#orderCmplt span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.number"))).shouldBe(visible);
        
        // validate process names
        $("#crt h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.name"))).shouldBe(visible);
        $("#ship h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.name"))).shouldBe(visible);
        $("#bill h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.name"))).shouldBe(visible);
        $("#payment h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.name"))).shouldBe(visible);
        $("#chkout h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.name"))).shouldBe(visible);
        $("#orderCmplt h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.name"))).shouldBe(visible);
    }
    
    private void validateFillInHeadlines(String headline)
    {
        $$(".form-group label").findBy(exactText(headline)).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("GuestPaymentPage.fillIn.headlines.cardNumber"));
        validateFillInHeadlines(Neodymium.localizedText("GuestPaymentPage.fillIn.headlines.cardHolderName"));
        validateFillInHeadlines(Neodymium.localizedText("GuestPaymentPage.fillIn.headlines.expirationDate"));
    }
    
    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {        
        creditCardNumber.shouldHave(attribute("placeholder", (Neodymium.localizedText("GuestPaymentPage.fillIn.placeholder.cardNumber")))).shouldBe(visible);
        creditCardName.shouldHave(attribute("placeholder", (Neodymium.localizedText("GuestPaymentPage.fillIn.placeholder.cardOwnerName")))).shouldBe(visible);
        $("#expirationDateMonth [selected]").shouldHave(exactText("0" + Integer.toString(LocalDate.now().getMonthValue()))).shouldBe(visible);
        $("#expirationDateYear [selected]").shouldHave(exactText(Integer.toString(LocalDate.now().getYear()))).shouldBe(visible);
    }
    
    private void validateMonthDropdown(String month) 
    {
        $$("#expirationDateMonth").findBy(matchText(month)).shouldBe(visible);
    }
    
    @Step("validate month dropdown")
    public void validateMonthDropdown() 
    {
        // open dropdown
        expirationMonth.scrollTo().click();
        
        // validate months
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.january"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.february"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.march"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.april"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.may"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.june"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.july"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.august"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.september"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.october"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.november"));
        validateMonthDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireMonth.december"));
    }
    
    private void validateYearDropdown(String year) 
    {
        $$("#expirationDateYear").findBy(matchText(year)).shouldBe(visible);
    }
    
    @Step("validate year dropdown")
    public void validateYearDropdown() 
    {        
        // open dropdown
        expirationYear.scrollTo().click();
        
        // validate years
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2023"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2024"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2025"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2026"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2027"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2028"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2029"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2030"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2031"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2032"));
        validateYearDropdown(Neodymium.localizedText("GuestPaymentPage.fillIn.expireYear.2033"));
    }
    
    @Step("validate required string")
    public void validateRequiredString() 
    {
        $(".reqField").shouldHave(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.requiredFields"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate payment page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate breadcrumb
        validateBreadcrumb();
        
        // validate process wrap
        // TODO - after fixing issue 171: consistent element selectors for all checkout pages with progress indicator
        //validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("GuestPaymentPage.title"))).shouldBe(visible);
        
        // validate fill in form headlines
        validateFillInHeadlines();
        
        // validate placeholder
        validateFillInPlaceholder();
        
        // validate month dropdown
        validateMonthDropdown();
        
        // validate year dropdown
        validateYearDropdown();
        
        // validate "required fields" string
        validateRequiredString();
        
        // validate continue button
        addPaymentButton.shouldHave(exactText(Neodymium.localizedText("GuestPaymentPage.button"))).shouldBe(visible);
    }
    
    /// ----- send payment form ----- ///
    
    private PlaceOrderPage goToPlaceOrderPage(String number, String name, String month, String year)
    {
        // fill in form with parameters
        creditCardNumber.val(number);
        creditCardName.val(name);
        expirationMonth.selectOption(month);
        expirationYear.selectOption(year);

        // go to place order page
        addPaymentButton.scrollTo().click();

        return new PlaceOrderPage().isExpectedPage();
    }
    
    @Step("fill and send payment form with '{creditCard}'")
    public PlaceOrderPage goToPlaceOrderPage(CreditCard creditCard)
    {
        return goToPlaceOrderPage(creditCard.getCardNumber(), creditCard.getFullName(), creditCard.getExpDateMonth(), creditCard.getExpDateYear());
    }
}
