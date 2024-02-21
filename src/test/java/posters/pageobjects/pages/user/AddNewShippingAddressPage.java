package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.components.AddressForm;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class AddNewShippingAddressPage extends AbstractBrowsingPage
{        
    private SelenideElement addNewShippingAddressButton = $("#btnAddShippAddr");
    
    public AddressForm addressForm = new AddressForm();

    @Override
    @Step("ensure this is a add new shipping address page")
    public AddNewShippingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#formAddDelAddr .h2").should(exist);
        return this;
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
        addNewShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewAddress"))).shouldBe(visible);
    }
}