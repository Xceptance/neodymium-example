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

public class AddNewShippingAddressPage extends AbstractBrowsingPage
{
    public AddressForm addressForm = new AddressForm();

    @Override
    @Step("ensure this is an add new shipping address page")
    public AddNewShippingAddressPage assertExpectedPage()
    {
        $(".h2").should(exist);
        return this;
    }
    
    @Step("check if this is a change name or email page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition($(".h2"), exist);
        return $(".h2").text().contains(Neodymium.localizedText("addNewShippingAddressPage.title"));
    }

    @Override
    @Step("validate add new shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("addNewShippingAddressPage.title"))).shouldBe(visible);

        // validate address form
        addressForm.validateStructure();

        // validate continue button
        $("#btn-add-shipp-addr").shouldHave(exactText(Neodymium.localizedText("button.addNewAddress"))).shouldBe(visible);
    }
}
