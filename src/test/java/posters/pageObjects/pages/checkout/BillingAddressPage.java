/**
 * 
 */
package posters.pageObjects.pages.checkout;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

/**
 * @author pfotenhauer
 */
public class BillingAddressPage extends AbstractCheckoutPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    @Override
    public void isExpectedPage()
    {
        $("#titleBillAddr").should(exist);
    }

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
        // Assert the headline is there and starts with a capital letter
        $("#titleBillAddr").should(matchText("[A-Z].{3,}"));
        // First address
        // Makes sure at least one address is visible
        $("#billAddr0").shouldBe(visible);
    }

    /**
     * @param position
     *            The position of the billing address you want to choose
     * @return PPayment
     */
    public PaymentPage selectBillingAddress(int position)
    {
        final int index = position - 1;
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#billAddr" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseBillAddress").scrollTo().click();

        return page(PaymentPage.class);
    }
}
