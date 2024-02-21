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

public class AddNewBillingAddressPage extends AbstractBrowsingPage
{        
    private SelenideElement addNewBillingAddressButton = $("#btnAddBillAddr");
    
    public AddressForm addressForm = new AddressForm();

    @Override
    @Step("ensure this is a add new billing address page")
    public AddNewBillingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#formAddBillAddr .h2").should(exist);
        return this;
    }
    
    @Override
    @Step("validate add new billing address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        $(".h2").shouldHave(exactText(Neodymium.localizedText("addNewBillingAddressPage.title"))).shouldBe(visible);
        
        // validate address form
        addressForm.validateStructure();

        // validate continue button
        addNewBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewAddress"))).shouldBe(visible);
    }
}