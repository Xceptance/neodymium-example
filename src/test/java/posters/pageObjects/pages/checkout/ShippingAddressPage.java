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
public class ShippingAddressPage extends AbstractCheckoutPage
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
        // Assert the headline is there and starts with a capital letter
        $("#titleDelAddr").should(matchText("[A-Z].{3,}"));
        // First address
        // Makes sure at least one address is visible
        $("#delAddr0").shouldBe(visible);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.PageObject#isExpectedPage()
     */
    public void isExpectedPage()
    {
        $("#titleDelAddr").should(exist);
    }

    /**
     * @param position
     *            position of the shipping address
     * @return PBillingAddress
     */
    public BillingAddressPage selectShippingAddress(int position)
    {
        final int index = position - 1;
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#delAddr" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseAddressContinue").scrollTo().click();

        return page(BillingAddressPage.class);
    }
}
