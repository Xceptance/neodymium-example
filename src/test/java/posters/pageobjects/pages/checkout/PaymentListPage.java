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
import posters.pageobjects.components.AbstractOverlayComponent;
import posters.pageobjects.components.NewPaymentOverlay;

/**
 * This page is only for registered user with saved billing address available
 * 
 * @author pfotenhauer
 */
public class PaymentListPage extends AbstractListPage
{
    private SelenideElement headline = $("#titlePayment");

    private SelenideElement creditCardNumber = $("#creditCardNumber");

    private SelenideElement creditCardName = $("#name");

    private SelenideElement expirationMonth = $("#expirationDateMonth");

    private SelenideElement expirationYear = $("#expirationDateYear");

    private SelenideElement addPaymentButton = $("#btnAddPayment");

    public NewPaymentOverlay newPaymentOverlay = new NewPaymentOverlay();

    @Override
    @Step("ensure this is a payment page")
    public PaymentListPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Override
    @Step("validate payment page structure")
    public PaymentListPage validateStructure()
    {
        super.validateStructure();

        // Headline
        // Makes sure the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // First credit card
        // Makes sure at least one credit card is saved
        $("#payment0").shouldBe(visible);
        return this;
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
        PlaceOrderPage placeOrderPage = new PlaceOrderPage();
        placeOrderPage.isExpectedPage();
        return placeOrderPage;
    }

    @Override
    public AbstractCheckoutPage selectAddressByPosition(int position)
    {
        return selectCreditCard(position);
    }

    @Override
    public AbstractOverlayComponent getOverlayComponent()
    {
        $("button[data-target='#addPaymentModal']").click();
        return newPaymentOverlay;
    }
}
