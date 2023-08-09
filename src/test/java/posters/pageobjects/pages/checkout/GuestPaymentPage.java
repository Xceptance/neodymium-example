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

    /// ----- validate guest payment page ----- ///
    
    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines(String headline)
    {
        $$(".form-group label").findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines("GuestPaymentPage.fillIn.headlines.cardNumber");
        validateFillInHeadlines("GuestPaymentPage.fillIn.headlines.cardHolderName");
        validateFillInHeadlines("GuestPaymentPage.fillIn.headlines.expirationDate");
    }
    
    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {        
        creditCardNumber.shouldHave(attribute("placeholder", (Neodymium.localizedText("GuestPaymentPage.fillIn.placeholder.cardNumber")))).shouldBe(visible);
        creditCardName.shouldHave(attribute("placeholder", (Neodymium.localizedText("GuestPaymentPage.fillIn.placeholder.cardOwnerName")))).shouldBe(visible);
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
        expirationMonth.scrollTo().click();
        
        // validate months
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.january");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.february");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.march");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.april");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.may");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.june");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.july");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.august");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.september");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.october");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.november");
        validateMonthDropdown("GuestPaymentPage.fillIn.expireMonth.december");
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
        expirationYear.scrollTo().click();
        
        // validate years
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2023");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2024");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2025");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2026");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2027");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2028");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2029");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2030");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2031");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2032");
        validateYearDropdown("GuestPaymentPage.fillIn.expireYear.2033");
    }
    
    @Override
    @Step("validate payment page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate breadcrumb
        validateBreadcrumb();
        
        // validate process wrap
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
    
    @Step("fill and send payment form")
    public PlaceOrderPage goToPlaceOrderPage(CreditCard creditcard)
    {
        return goToPlaceOrderPage(creditcard.getCardNumber(), creditcard.getFullName(), creditcard.getExpDateMonth(), creditcard.getExpDateYear());
    }
    
    @Step("fill and send payment form")
    public PlaceOrderPage goToPlaceOrderPage(String number, String name, String month, String year)
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
}
