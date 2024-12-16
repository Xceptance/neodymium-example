package posters.pageobjects.pages.user;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class AddressOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#title-address-overview");

    private SelenideElement addNewShippingAddressButton = $("#link-add-ship-addr");

    private SelenideElement addNewBillingAddressButton = $("#link-add-bill-addr");

    private SelenideElement goBackButton = $("#link-acc-overview");

    @Override
    @Step("ensure this is an address overview page")
    public AddressOverviewPage reached()
    {
        super.reached();
        title.should(exist);
        return this;
    }

    @Override
    @Step("check if this is an address overview page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(title, exist);
        return title.exists();
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
        addNewShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).shouldBe(visible);

        // validate billing addresses overview
        $("#titleBillAddr").shouldHave(exactText(Neodymium.localizedText("account.billingAddress"))).shouldBe(visible);
        addNewBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).shouldBe(visible);

        // validate button
        goBackButton.shouldHave(exactText(Neodymium.localizedText("button.back"))).shouldBe(visible);
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
        addNewShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).click(ClickOptions.usingJavaScript());
        return new AddNewShippingAddressPage().reached();
    }

    @Step("add new billing address")
    public AddNewBillingAddressPage openAddNewBillingAddressPage()
    {
        addNewBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).click(ClickOptions.usingJavaScript());
        return new AddNewBillingAddressPage().reached();
    }

    @Step("go to account overview page")
    public AccountOverviewPage openAccountOverviewPage()
    {
        goBackButton.click(ClickOptions.usingJavaScript());
        return new AccountOverviewPage().reached();
    }
}
