package posters.pageobjects.pages.checkout;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.components.AddressForm;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class GuestShippingAddressPage extends AbstractCheckoutPage<GuestShippingAddressPage>
{
    private SelenideElement title = $("#title-del-addr");

    public AddressForm addressForm = new AddressForm();

    @Override
    @Step("ensure this is a shipping address page")
    public GuestShippingAddressPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is a shipping address page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(title, exist);
        return title.exists();
    }

    /// ========== validate content guest shipping address page ========== ///

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

    @Step("validate shipping address usage for billing address radio")
    public void validateAddressRadio()
    {
        $(".mb-1").shouldHave(exactText(Neodymium.localizedText("fillIn.inputDescription.useThisAddressForBilling"))).shouldBe(visible);
        $$(".form-check-label").findBy(attribute("for", "billEqualShipp-Yes")).shouldHave(exactText(Neodymium.localizedText("fillIn.radio.yes")))
                               .shouldBe(visible);
        $$(".form-check-label").findBy(attribute("for", "billEqualShipp-No")).shouldHave(exactText(Neodymium.localizedText("fillIn.radio.no")))
                               .shouldBe(visible);
    }

    @Override
    @Step("validate shipping address page structure")
    public GuestShippingAddressPage validateStructure()
    {
        super.validateStructure();

        // validate process wrap
        validateProcessWrap();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("guestShippingAddressPage.title"))).shouldBe(visible);

        // validate address form
        addressForm.validateStructure();

        // validate address usage radio
        validateAddressRadio();

        // validate continue button
        $("#button-add-shipping-address").shouldHave(exactText(Neodymium.localizedText("button.continue"))).shouldBe(visible);

        return this;
    }
}
