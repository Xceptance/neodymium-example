package posters.pageobjects.pages.checkout;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.components.AddressForm;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class GuestBillingAddressPage extends AbstractCheckoutPage<GuestBillingAddressPage>
{
    private SelenideElement title = $("#title-bill-addr");

    public AddressForm addressForm = new AddressForm();

    @Override
    @Step("ensure this is a billing address page")
    public GuestBillingAddressPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is a billing address page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(title, exist);
        return title.exists();
    }

    /// ========== validate content guest billing address page ========== ///

    @Step("validate process wrap")
    public void validateProcessWrap()
    {
        for (int i = 1; i <= 6; i++)
        {
            $(".progress-step-" + i + " .progress-bubble").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".number"))).shouldBe(visible);
            $(".progress-step-" + i + " .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".name")))
                                                                  .shouldBe(visible);
        }
    }

    @Override
    @Step("validate shipping address page structure")
    public GuestBillingAddressPage validateStructure()
    {
        super.validateStructure();

        // validate process wrap
        validateProcessWrap();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("guestBillingAddressPage.title"))).shouldBe(visible);

        // validate address form
        addressForm.validateStructure();

        // validate continue button
        $("#btn-add-bill-addr").shouldHave(exactText(Neodymium.localizedText("button.continue"))).shouldBe(visible);

        return this;
    }
}
