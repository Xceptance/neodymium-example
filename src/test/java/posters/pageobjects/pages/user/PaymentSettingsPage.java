package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;
import posters.dataobjects.CreditCard;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class PaymentSettingsPage extends AbstractBrowsingPage
{
    private SelenideElement addNewPaymentButton = $("#linkAddNewPayment");

    @Override
    public PaymentSettingsPage isExpectedPage()
    {
        $("#titlePaymentOverview").shouldBe(visible);
        return this;
    }

    @Override
    public PaymentSettingsPage validateStructure()
    {
        isExpectedPage();
        addNewPaymentButton.shouldBe(visible);
        return this;
    }

    private PaymentSettingsPage enterPayment(String cardNumber, String cardHoldersName, String expMonth, String expYear)
    {
        $("#creditCardNumber").setValue(cardNumber);
        $("#name").setValue(cardHoldersName);
        $("#expirationDateMonth").selectOption(expMonth);
        $("#expirationDateYear").selectOption(expYear);
        return this;
    }

    @Step("add payment")
    public PaymentSettingsPage addPayment(String cardNumber, String cardHoldersName, String expMonth, String expYear)
    {
        addNewPaymentButton.click();
        enterPayment(cardNumber, cardHoldersName, expMonth, expYear);
        $("#btnAddPayment").click();
        return this;
    }

    @Step("add payment")
    public PaymentSettingsPage addPayment(CreditCard creditCard)
    {
        return addPayment(creditCard.getCardNumber(), creditCard.getFullName(), creditCard.getExpDateMonth(), creditCard.getExpDateYear());
    }

    private SelenideElement getPaymentCardByEncryptedCardNumber(String cryptedCardNumber)
    {
        return $$(".list-unstyled.address>li>div.paymentCardNumber").findBy(matchText(cryptedCardNumber)).parent().parent();
    }

    @Step("validate payment")
    public PaymentSettingsPage validatePayment(String cardNumber, String cardHoldersName, String expMonth, String expYear)
    {
        return validatePayment(new CreditCard(cardHoldersName, cardNumber, expMonth, expYear));
    }

    @Step("validate payment")
    public PaymentSettingsPage validatePayment(CreditCard creditCard)
    {
        getPaymentCardByEncryptedCardNumber(creditCard.getCrypticCardNumber()).find("div.paymentName").shouldHave(exactText(creditCard.getFullName()));
        getPaymentCardByEncryptedCardNumber(creditCard.getCrypticCardNumber()).find("div.paymentCardNumber")
                                                                              .shouldHave(exactText(creditCard.getCrypticCardNumber()));
        getPaymentCardByEncryptedCardNumber(creditCard.getCrypticCardNumber()).find("span.expMonth").shouldHave(exactText(creditCard.getExpDateMonth()));
        getPaymentCardByEncryptedCardNumber(creditCard.getCrypticCardNumber()).find("span.expYear").shouldHave(exactText(creditCard.getExpDateYear()));

        return this;
    }

    @Step("edit payment with card number {oldCardNumber}")
    public PaymentSettingsPage editPayment(String oldCardNumber, CreditCard creditCard)
    {
        return editPayment(oldCardNumber, creditCard.getCardNumber(), creditCard.getFullName(), creditCard.getExpDateMonth(), creditCard.getExpDateYear());
    }

    @Step("edit payment with card number {oldCardNumber}")
    public PaymentSettingsPage editPayment(String oldCardNumber, String cardNumber, String cardHoldersName, String expMonth, String expYear)
    {
        String oldCrypticCardNumber = new CreditCard("", oldCardNumber, "", "").getCrypticCardNumber();
        getPaymentCardByEncryptedCardNumber(oldCrypticCardNumber).parent().find("form[action='/posters/updatePaymentMethod']>button").click();
        enterPayment(cardNumber, cardHoldersName, expMonth, expYear);
        $("#btnUpdateDelAddr").click();
        return this;
    }

    @Step("delete payment with card number {cardNumber}")
    public DeleteConfirmationPage deletePayment(String cardNumber)
    {
        String cryptidCardNumber = new CreditCard("", cardNumber, "", "").getCrypticCardNumber();
        getPaymentCardByEncryptedCardNumber(cryptidCardNumber).parent().find("form[action='/posters/confirmDeletePayment']>button").click();
        return new DeleteConfirmationPage().isExpectedPage();
    }

    @Step("validate payment with card number {cardNumber} doesn't exist")
    public PaymentSettingsPage validatePaymentDoesntExist(String cardNumber)
    {
        String cryptidCardNumber = new CreditCard("", cardNumber, "", "").getCrypticCardNumber();
        getPaymentCardByEncryptedCardNumber(cryptidCardNumber).shouldNot(exist);
        return this;
    }
}
