package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class AddressOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#title-address-overview");
    
    private ElementsCollection addNewShippingAddressButton = $$("#link-add-ship-addr");
    
    // TODO - change selector for add billing address button
    //private SelenideElement addNewBillingAddressButton = $("#link-add-bill-addr");

    @Override
    @Step("ensure this is a address overview page")
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
        $("#title-del-addr").shouldHave(exactText(Neodymium.localizedText("account.shippingAddress"))).shouldBe(visible);
        addNewShippingAddressButton.findBy(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).shouldBe(visible);

        // validate billing addresses overview
        $("#titleBillAddr").shouldHave(exactText(Neodymium.localizedText("account.billingAddress"))).shouldBe(visible);
        addNewShippingAddressButton.findBy(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).shouldBe(visible);
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
        addNewShippingAddressButton.findBy(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).click(ClickOptions.usingJavaScript());
        return new AddNewShippingAddressPage().isExpectedPage();
    }
    
    @Step("add new billing address")
    public AddNewBillingAddressPage openAddNewBillingAddressPage() 
    {
        addNewShippingAddressButton.findBy(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).click(ClickOptions.usingJavaScript());
        return new AddNewBillingAddressPage().isExpectedPage();
    }
}