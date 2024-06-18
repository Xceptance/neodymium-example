package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.codeborne.selenide.CheckResult;
import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.Driver;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebElementCondition;
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
    public void validateCreditCardContainer(CreditCard creditCard) 
    {
        // selector for product
        final SelenideElement creditCardContainer = $$(".thumbnail").shouldHave(sizeGreaterThan(0))
                                                                 .findBy(new WebElementCondition("has full name " + creditCard.getFullName() + ", card number " + creditCard.getCrypticCardNumber()
                                                                                                 + ", expire date " + creditCard.getExpDate())
                                                                 {
                                                                     @Override
                                                                     public CheckResult check(Driver driver, WebElement element)
                                                                     {
                                                                         boolean matchesName = element.findElement(By.cssSelector(".name")).getText()
                                                                                                      .equals(creditCard.getFullName());
                                                                         boolean matchesCreditCard = element.findElement(By.cssSelector(".creditcard")).getText()
                                                                                                         .equals(creditCard.getCrypticCardNumber());
                                                                         boolean matchesExpDate = element.findElement(By.cssSelector(".valid-to")).getText()
                                                                                                        .equals(creditCard.getExpDate());
                                                                         return new CheckResult(matchesName && matchesCreditCard && matchesExpDate, "");
                                                                     }
                                                                 }).shouldBe(visible, Duration.ofMillis(9000));
        
        // validate address data
        creditCardContainer.find(".name").shouldHave(exactText(creditCard.getFullName())).shouldBe(visible);
        creditCardContainer.find(".creditcard").shouldHave(exactText(creditCard.getCrypticCardNumber())).shouldBe(visible);
        creditCardContainer.find(".valid-to").shouldHave(exactText(creditCard.getExpDate())).shouldBe(visible);
    }
    
    /// ========== select credit card ========== ///

    @Step("select a credit card on position '{position}'")
    public PlaceOrderPage selectCreditCard(CreditCard creditCard)
    {
        // selector for product
        final SelenideElement creditCardContainer = $$(".thumbnail").shouldHave(sizeGreaterThan(0))
                                                                 .findBy(new WebElementCondition("has full name " + creditCard.getFullName() + ", card number " + creditCard.getCrypticCardNumber()
                                                                                                 + ", expire date " + creditCard.getExpDate())
                                                                 {
                                                                     @Override
                                                                     public CheckResult check(Driver driver, WebElement element)
                                                                     {
                                                                         boolean matchesName = element.findElement(By.cssSelector(".name")).getText()
                                                                                                      .equals(creditCard.getFullName());
                                                                         boolean matchesCreditCard = element.findElement(By.cssSelector(".creditcard")).getText()
                                                                                                         .equals(creditCard.getCrypticCardNumber());
                                                                         boolean matchesExpDate = element.findElement(By.cssSelector(".valid-to")).getText()
                                                                                                        .equals(creditCard.getExpDate());
                                                                         return new CheckResult(matchesName && matchesCreditCard && matchesExpDate, "");
                                                                     }
                                                                 }).shouldBe(visible, Duration.ofMillis(9000));
        
        // select credit card, press "Use this credit card"
        creditCardContainer.find("input").click(ClickOptions.usingJavaScript());
        useCreditCardButton.click(ClickOptions.usingJavaScript());

        return new PlaceOrderPage().isExpectedPage();
    }
}