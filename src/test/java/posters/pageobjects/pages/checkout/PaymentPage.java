/**
 * 
 */
package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.AddressContainer;
import posters.dataobjects.CreditCard;

/**
 * @author pfotenhauer
 */
public class PaymentPage extends AbstractEnterAddressPage
{
    private SelenideElement headline = $("#titlePayment");

    private SelenideElement creditCardNumber = $("#creditCardNumber");

    private SelenideElement creditCardName = $("#name");

    private SelenideElement expirationMonth = $("#expirationDateMonth");

    private SelenideElement expirationYear = $("#expirationDateYear");

    private SelenideElement addPaymentButton = $("#btnAddPayment");

    @Override
    @Step("ensure this is a new payment page")
    public PaymentPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Override
    @Step("validate new payment page structure")
    public PaymentPage validateStructure()
    {
        super.validateStructure();

        // Headline
        // Makes sure the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // Form
        // Make sure the form is there to begin with
        $("#formAddPayment").should(exist);
        // Credit Card Number
        // Makes sure the label next to the card number field shows the correct text
        $("label[for=\"creditCardNumber\"]").shouldHave(exactText(Neodymium.localizedText("General.payment.cardnumber")));
        // Makes sure the card number field is there
        creditCardNumber.shouldBe(visible);
        // Name
        // Makes sure the label next to the card holder field shows the correct text
        $("label[for=\"name\"]").shouldHave(exactText(Neodymium.localizedText("General.payment.cardholdername")));
        // Makes sure the card holder field is there
        creditCardName.shouldBe(visible);
        // Expiration
        // Makes sure the label next to the expiration date fields shows the correct text
        $("label[for=\"expirationDateMonth\"]").shouldHave(exactText(Neodymium.localizedText("General.payment.expirationdate")));
        // Makes sure the expiration month field is there
        expirationMonth.shouldBe(visible);
        // Makes sure the expiration year field is there
        expirationYear.shouldBe(visible);
        // Continue Button
        // Makes sure the continue button is there
        addPaymentButton.should(exist);
        return this;
    }

    /**
     * @param number
     *            The credit card number, has to be 16 numbers
     * @param name
     *            The full name
     * @param month
     *            Expiration Month in numbers
     * @param year
     *            Expiration year
     */
    @Step("fill and send new payment form")
    public PlaceOrderPage sendPaymentForm(String number, String name, String month, String year)
    {
        fillForm(number, name, month, year);
        // Opens the order overview page
        // Clicks the Continue button
        addPaymentButton.scrollTo().click();
        PlaceOrderPage placeOrderPage = new PlaceOrderPage();
        placeOrderPage.isExpectedPage();
        return placeOrderPage;
    }

    @Step("fill payment form")
    public PaymentPage fillForm(String number, String name, String month, String year)
    {
        // Credit Card Number
        // Fills the card number field with the parameter
        creditCardNumber.val(number);
        // Name
        // Fills the card holder field with the parameter
        creditCardName.val(name);
        // Expiration
        // Chooses the expiration month matching the parameter
        expirationMonth.selectOption(month);
        // Chooses the expiration year matching the parameter
        expirationYear.selectOption(year);
        return this;
    }

    @Override
    @Step("fill payment form")
    public PaymentPage fillForm(AddressContainer address)
    {
        CreditCard creditCard = (CreditCard) address;
        return fillForm(creditCard.getCardNumber(), creditCard.getFullName(), creditCard.getExpDateMonth(), creditCard.getExpDateYear());
    }

    @Override
    @Step("send incorrect payment")
    public PaymentPage sendIncorrectForm()
    {
        addPaymentButton.click();
        return this;
    }

    @Step("send correct payment")
    public PlaceOrderPage sendCorrectForm()
    {
        addPaymentButton.click();
        return new PlaceOrderPage().isExpectedPage();
    }

    @Step("validate entered payment")
    public PaymentPage validateEnteredData(String number, String name, String month, String year)
    {
        // Credit Card Number
        // Fills the card number field with the parameter
        creditCardNumber.shouldHave(value(number));
        // Name
        // Fills the card holder field with the parameter
        creditCardName.shouldHave(value(name));
        // Expiration
        // Chooses the expiration month matching the parameter
        expirationMonth.getSelectedOption().shouldHave(value(month));
        // Chooses the expiration year matching the parameter
        expirationYear.getSelectedOption().shouldHave(value(year));
        return this;
    }

    @Step("validate entered payment")
    public PaymentPage validateEnteredData(AddressContainer address)
    {
        CreditCard creditCard = (CreditCard) address;
        return validateEnteredData(creditCard.getCardNumber(), creditCard.getFullName(), creditCard.getExpDateMonth(), creditCard.getExpDateYear());
    }

    @Step("place order")
    public PlaceOrderPage placeOrder()
    {
        // Clicks the Continue button
        addPaymentButton.scrollTo().click();
        return new PlaceOrderPage().isExpectedPage();
    }

    /**
     * @param creditcard
     * @return
     */
    public PlaceOrderPage sendPaymentForm(CreditCard creditcard)
    {
        return sendPaymentForm(creditcard.getCardNumber(), creditcard.getFullName(), creditcard.getExpDateMonth(), creditcard.getExpDateYear());
    }

}
