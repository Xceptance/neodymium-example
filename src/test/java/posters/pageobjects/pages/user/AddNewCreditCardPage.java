package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.time.LocalDate;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.tests.testdata.dataobjects.CreditCard;

public class AddNewCreditCardPage extends AbstractBrowsingPage
{
    private SelenideElement creditCardNumberField = $("#creditcard-number");
    
    private SelenideElement cardHolderNameField = $("#name");
    
    private SelenideElement expirationMonth = $("#expiration-date-month");

    private SelenideElement expirationYear = $("#expiration-date-year");
    
    private SelenideElement addNewCreditCardButton = $("#btn-add-payment");

    @Override
    @Step("ensure this is a add new credit card page")
    public AddNewCreditCardPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#form-add-payment").should(exist);
        return this;
    }

    /// ========== validate content add new credit card page ========== ///
    
    private void validateFillInHeadlines(String headline)
    {
        $$(".form-group label").findBy(exactText(headline)).shouldBe(visible);
    }

    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.cardNumber"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.cardHolderName"));
        validateFillInHeadlines(Neodymium.localizedText("fillIn.inputDescription.expirationDate"));
    }
    
    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {
        creditCardNumberField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.cardNumber")))).shouldBe(visible);
        cardHolderNameField.shouldHave(attribute("placeholder", (Neodymium.localizedText("fillIn.placeholder.cardHolderName")))).shouldBe(visible);
        
        String month = Integer.toString(LocalDate.now().getMonthValue());
        
        if (LocalDate.now().getMonthValue() < 10) 
        {
            month = "0" + Integer.toString(LocalDate.now().getMonthValue());
        }
        
        expirationMonth.find("[selected]").shouldHave(exactText(month)).shouldBe(visible);
        expirationYear.find("[selected]").shouldHave(exactText(Integer.toString(LocalDate.now().getYear()))).shouldBe(visible);
    }
    
    @Step("validate month dropdown")
    public void validateMonthDropdown() 
    {
        // open dropdown
        expirationMonth.click(ClickOptions.usingJavaScript());
        
        // validate months
        for (int i = 1; i <= 12; i++) 
        {
            $$("#expiration-date-month").findBy(matchText(Neodymium.localizedText("fillIn.dropdown.expireMonth." + i))).shouldBe(visible);
        }
    }

    @Step("validate year dropdown")
    public void validateYearDropdown() 
    {        
        // open dropdown
        expirationYear.click(ClickOptions.usingJavaScript());
        
        // validate years
        for (int i = 1; i <= 11; i++) 
        {
            $$("#expiration-date-year").findBy(matchText(Neodymium.localizedText("fillIn.dropdown.expireYear." + i))).shouldBe(visible);
        }
    }
    
    @Override
    @Step("validate add new credit card page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("account.paymentSettings"))).shouldBe(visible);
        
        // validate input descriptions
        validateFillInHeadlines();
        
        // validate placeholder
        validateFillInPlaceholder();
        
        // validate month dropdown
        validateMonthDropdown();
        
        // validate year dropdown
        validateYearDropdown();
        
        // validate "required fields" string
        $(".req-field").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.requiredFields"))).shouldBe(visible);
        
        // validate add new credit card button
        addNewCreditCardButton.shouldHave(exactText(Neodymium.localizedText("button.addNewCreditCard"))).shouldBe(visible);
    }
  
    /// ========== add new credit card page navigation ========== ///
    
    @Step("fill in payment form with {creditCard}")
    public PaymentOverviewPage addNewCreditCard(CreditCard creditCard) 
    {        
        // fill in payment form
        creditCardNumberField.val(creditCard.getCardNumber());
        cardHolderNameField.val(creditCard.getFullName());
        expirationMonth.selectOption(creditCard.getExpDateMonth());
        expirationYear.selectOption(creditCard.getExpDateYear());
        
        // click add new payment button
        addNewCreditCardButton.click(ClickOptions.usingJavaScript());
        
        return new PaymentOverviewPage().isExpectedPage();
    }
}