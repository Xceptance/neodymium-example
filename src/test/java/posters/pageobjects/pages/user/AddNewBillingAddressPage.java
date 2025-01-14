package posters.pageobjects.pages.user;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.components.AddressForm;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AddNewBillingAddressPage extends AbstractBrowsingPage<AddNewBillingAddressPage>
{

    private SelenideElement billingAddressForm = $("#form-add-bill-addr");

    public AddressForm addressForm = new AddressForm();

    @Override
    @Step("ensure this is an add new billing address page")
    public AddNewBillingAddressPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is an add new billing address page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(billingAddressForm, exist);
        return billingAddressForm.exists();
    }

    @Override
    @Step("validate add new billing address page structure")
    public AddNewBillingAddressPage validateStructure()
    {
        super.validateStructure();

        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("addNewBillingAddressPage.title"))).shouldBe(visible);

        // validate address form
        addressForm.validateStructure();

        // validate continue button
        $("#btn-add-bill-addr").shouldHave(exactText(Neodymium.localizedText("button.addNewAddress"))).shouldBe(visible);

        return this;
    }
}
