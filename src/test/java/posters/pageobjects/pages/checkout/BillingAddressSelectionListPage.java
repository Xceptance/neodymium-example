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
 * This page is only for registered user with saved billing address available
 * 
 * @author pfotenhauer
 */
public class BillingAddressSelectionListPage extends AbstractCheckoutPage
{
    private SelenideElement headline = $("#titleBillAddr");

    @Override
    @Step("ensure this is a billing address page")
    public BillingAddressSelectionListPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Override
    @Step("validate billing address page structure")
    public BillingAddressSelectionListPage validateStructure()
    {
        super.validateStructure();

        // Headline
        // Assert the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // First address
        // Makes sure at least one address is visible
        $("#billAddr0").shouldBe(visible);
        return this;
    }

    /**
     * @param position
     *            The position of the billing address you want to choose
     * @return PaymentPage
     */
    @Step("select a billing address")
    public PaymentSelectionListPage selectBillingAddress(int position)
    {
        final int index = position - 1;
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#billAddr" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseBillAddress").scrollTo().click();
        PaymentSelectionListPage paymentPage = new PaymentSelectionListPage();
        paymentPage.isExpectedPage();
        return paymentPage;
    }
}
