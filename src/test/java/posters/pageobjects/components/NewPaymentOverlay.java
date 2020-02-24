package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;
import posters.dataobjects.AddressContainer;
import posters.dataobjects.CreditCard;
import posters.pageobjects.pages.checkout.PlaceOrderPage;

public class NewPaymentOverlay extends AbstractOverlayComponent
{

    @Override
    public void isComponentAvailable()
    {
        $("#formAddPaymentModal").shouldBe(visible);
    }

    @Step("add second payment")
    public NewPaymentOverlay fillForm(String cardNumber, String cardName, String expMonth, String expYear)
    {
        $("#creditCardNumber").setValue(cardNumber);
        $("#name").setValue(cardName);
        $("#expirationDateMonth").selectOption(expMonth);
        $("#expirationDateYear").selectOption(expYear);
        return this;
    }

    @Step("add second payment")
    public PlaceOrderPage addPayment(CreditCard creditCard)
    {
        fillForm(creditCard.getCardNumber(), creditCard.getFullName(), creditCard.getExpDateMonth(), creditCard.getExpDateYear());
        return sendForm();
    }

    @Override
    public AbstractOverlayComponent fillForm(AddressContainer address)
    {
        CreditCard creditCard = (CreditCard) address;
        return fillForm(creditCard.getCardNumber(), creditCard.getFullName(), creditCard.getExpDateMonth(), creditCard.getExpDateYear());
    }

    @Override
    public PlaceOrderPage sendForm()
    {
        $("#btnAddPayment").click();
        return new PlaceOrderPage().isExpectedPage();
    }

}
