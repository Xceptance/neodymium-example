/**
 * 
 */
package posters.pageObjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import posters.dataObjects.CreditCard;

/**
 * @author pfotenhauer
 */
public class NewPaymentPage extends AbstractCheckoutPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#validateStructure()
     */
    @Override
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Makes sure the headline is there and starts with a capital letter
        $("#titlePayment").should(matchText("[A-Z].{3,}"));
        // Form
        // Make sure the form is there to begin with
        $("#formAddPayment").should(exist);
        // Credit Card Number
        // Makes sure the label next to the card number field shows the correct text
        $("label[for=\"creditCardNumber\"]").shouldHave(exactText("Card number*"));
        // Makes sure the card number field is there
        $("#creditCardNumber").shouldBe(visible);
        // Name
        // Makes sure the label next to the card holder field shows the correct text
        $("label[for=\"name\"]").shouldHave(exactText("Cardholder's name*"));
        // Makes sure the card holder field is there
        $("#name").shouldBe(visible);
        // Expiration
        // Makes sure the label next to the expiration date fields shows the correct text
        $("label[for=\"expirationDateMonth\"]").shouldHave(exactText("Expiration date*"));
        // Makes sure the expiration month field is there
        $("#expirationDateMonth").shouldBe(visible);
        // Makes sure the expiration year field is there
        $("#expirationDateYear").shouldBe(visible);
        // Continue Button
        // Makes sure the continue button is there
        $("#btnAddPayment").should(exist);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public void isExpectedPage()
    {
        $("#titlePayment").should(exist);
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
    public PlaceOrderPlace sendPaymentForm(String number, String name, String month, String year)
    {
        // Credit Card Number
        // Fills the card number field with the parameter
        $("#creditCardNumber").val(number);
        // Name
        // Fills the card holder field with the parameter
        $("#name").val(name);
        // Expiration
        // Chooses the expiration month matching the parameter
        $("#expirationDateMonth").selectOption(month);
        // Chooses the expiration year matching the parameter
        $("#expirationDateYear").selectOption(year);
        // Opens the order overview page
        // Clicks the Continue button
        $("#btnAddPayment").scrollTo().click();

        return page(PlaceOrderPlace.class);
    }

    /**
     * @param creditcard
     * @return
     */
    public PlaceOrderPlace sendPaymentForm(CreditCard creditcard)
    {
        return sendPaymentForm(creditcard.getCardNumber(), creditcard.getFullName(), creditcard.getExpDateMonth(), creditcard.getExpDateYear());
    }
}
