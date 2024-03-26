package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.CreditCard;

public class ReturningCustomerPaymentPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#title-payment");
    
    private SelenideElement addCreditCardButton = $(".form-group .btn");

    private SelenideElement useCreditCardButton = $("#btn-use-payment");
    

    @Override
    @Step("ensure this is a payment page")
    public ReturningCustomerPaymentPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#payment-0").should(exist);
        return this;
    }

    /// ========== validate content returning customer payment page ========== ///

    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        for (int i = 1; i <= 6; i++) 
        {
            $(".progress-step-" + i + " .progress-bubble").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".number"))).shouldBe(visible);
            $(".progress-step-" + i + " .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".name"))).shouldBe(visible);    
        }
    }
    
    @Override
    @Step("validate returning customer payment page structure")
    public void validateStructure()
    {
        super.validateStructure();
 
        // validate process wrap
        validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("returningCustomerPaymentPage.title"))).shouldBe(visible);
        
        // validate add new shipping address button
        addCreditCardButton.shouldHave(exactText(Neodymium.localizedText("button.addNewCreditCard"))).shouldBe(visible);
        
        // validate continue button
        useCreditCardButton.shouldHave(exactText(Neodymium.localizedText("button.useThisCreditCard"))).shouldBe(visible);
    }
    
    @Step("validate credit card '{creditCard}' on position '{position}' in credit card container")
    public void validateCreditCardContainer(int position, CreditCard creditCard) 
    {
        final int index = position - 1;
        final SelenideElement creditCardContainer = $("#payment-" + index);
        final String expDate = creditCard.getExpDateMonth() + "/" + creditCard.getExpDateYear();
        
        // validate address data
        creditCardContainer.find(".name").shouldHave(exactText(creditCard.getFullName())).shouldBe(visible);
        creditCardContainer.find(".creditCard").shouldHave(exactText(creditCard.getCrypticCardNumber())).shouldBe(visible);
        creditCardContainer.find(".validTo").shouldHave(exactText(expDate)).shouldBe(visible);
    }
    
    /// ========== select credit card ========== ///

    @Step("select a credit card on position '{position}'")
    public PlaceOrderPage selectCreditCard(int position)
    {
        final int index = position - 1;
        
        // select address, press "Use this credit card"
        $("#payment" + index + " input").click(ClickOptions.usingJavaScript());
        useCreditCardButton.click(ClickOptions.usingJavaScript());

        return new PlaceOrderPage().isExpectedPage();
    }
}