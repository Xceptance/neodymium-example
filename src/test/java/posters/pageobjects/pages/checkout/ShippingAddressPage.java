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
public class ShippingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement headline = $("#titleDelAddr");

    @Override
    @Step("ensure this is a shipping address page")
    public void isExpectedPage()
    {
        headline.should(exist);
    }

    @Override
    @Step("validate shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // Headline
        // Assert the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // First address
        // Makes sure at least one address is visible
        $("#delAddr0").shouldBe(visible);
    }

    /**
     * @param position
     *            position of the shipping address
     * @return BillingAddressPage
     */
    @Step("select a shipping address")
    public BillingAddressPage selectShippingAddress(int position)
    {
        final int index = position - 1;
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#delAddr" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseAddressContinue").scrollTo().click();

        return new BillingAddressPage();
    }
}
