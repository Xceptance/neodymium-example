package posters.pageobjects.pages.user;

import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.components.AddressForm;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AddNewShippingAddressPage extends AbstractBrowsingPage<AddNewShippingAddressPage>
{
    public AddressForm addressForm = new AddressForm();

    @Override
    @Step("ensure this is an add new shipping address page")
    public AddNewShippingAddressPage assertExpectedPage()
    {
        super.assertExpectedPage();
        $("#form-add-del-addr").should(exist);
        return this;
    }

    @Override
    @Step("check if this is an add new shipping address page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition($(".h2"), exist);
        return $(".h2").text().contains(Neodymium.localizedText("addNewShippingAddressPage.title"));
    }

    @Override
    @Step("validate add new shipping address page structure")
    public AddNewShippingAddressPage validateStructure()
    {
        super.validateStructure();

        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("addNewShippingAddressPage.title"))).shouldBe(visible);

        // validate address form
        addressForm.validateStructure();

        // validate continue button
        $("#btn-add-shipp-addr").shouldHave(exactText(Neodymium.localizedText("button.addNewAddress"))).shouldBe(visible);

        return this;
    }
}
