package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class AddressOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titleAddressOverview");
    
    private SelenideElement addNewShippingAddressButton = $("#linkAddNewShipAddr");
    
    private SelenideElement addNewBillingAddressButton = $("#linkAddNewBillAddr");

    @Override
    @Step("ensure this is a personal data page")
    public AddressOverviewPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ========== validate content address overview page ========== ///

    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("myAddressesPage.title"))).shouldBe(visible);

        // validate shipping addresses overview
        $("#titleDelAddr").shouldHave(exactText(Neodymium.localizedText("account.shippingAddress"))).shouldBe(visible);
        addNewShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).shouldBe(visible);

        // validate billing addresses overview
        $("#titleBillAddr").shouldHave(exactText(Neodymium.localizedText("account.billingAddress"))).shouldBe(visible);
        addNewBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).shouldBe(visible);
    }
    
    @Step("validate successful saved change")
    public void validateSuccessfulSave()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulSave"));
    }
    
    /// ========== address overview page navigation ========== ///
    
    @Step("add new shipping address")
    public AddNewShippingAddressPage openAddNewShippingAddressPage() 
    {
        addNewShippingAddressButton.click();
        return new AddNewShippingAddressPage().isExpectedPage();
    }
    
    @Step("add new billing address")
    public AddNewBillingAddressPage openAddNewBillingAddressPage() 
    {
        addNewBillingAddressButton.click();
        return new AddNewBillingAddressPage().isExpectedPage();
    }
}