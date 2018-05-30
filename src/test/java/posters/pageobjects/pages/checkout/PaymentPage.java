/**
 * 
 */
package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

/**
 * @author pfotenhauer
 */
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
    public void isExpectedPage()
    {
        headline.should(exist);
    }

    @Override
    @Step("validate payment page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Makes sure the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // First credit card
        // Makes sure at least one credit card is saved
        $("#payment0").shouldBe(visible);
    }

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

        return new PlaceOrderPage();
    }

    @Step("fill and send payment form")
    public PlaceOrderPage sendPaymentForm(String number, String name, String month, String year)
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
        // Opens the order overview page
        // Clicks the Continue button
        addPaymentButton.scrollTo().click();

        return new PlaceOrderPage();
    }
}
