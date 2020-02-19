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
import posters.pageobjects.components.NewAddressOverlay;

/**
 * This page is only for registered user with saved billing address available
 * 
 * @author pfotenhauer
 */
public class ShippingAddressListPage extends AbstractCheckoutPage
{
    private SelenideElement headline = $("#titleDelAddr");

    public NewAddressOverlay addNewAddressFromListPage = new NewAddressOverlay();

    @Override
    @Step("ensure this is a shipping address page")
    public ShippingAddressListPage isExpectedPage()
    {
        headline.should(exist);
        return this;
    }

    @Override
    @Step("validate shipping address page structure")
    public ShippingAddressListPage validateStructure()
    {
        super.validateStructure();

        // Headline
        // Assert the headline is there and starts with a capital letter
        headline.should(matchText("[A-Z].{3,}"));
        // First address
        // Makes sure at least one address is visible
        $("#delAddr0").shouldBe(visible);
        return this;
    }

    /**
     * @param position
     *            position of the shipping address
     * @return BillingAddressPage
     */
    @Step("select a shipping address")
    public BillingAddresssListPage selectShippingAddress(int position)
    {
        final int index = position - 1;
        // Select address
        // Checks the radio button belonging to the delivery address with index @{index}
        $("#delAddr" + index + " input").scrollTo().click();
        // Open the billing address page in the checkout process
        // Clicks the continue button
        $("#btnUseAddressContinue").scrollTo().click();
        BillingAddresssListPage billingAddressPage = new BillingAddresssListPage();
        billingAddressPage.isExpectedPage();
        return billingAddressPage;
    }

}
