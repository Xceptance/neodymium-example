package posters.pageobjects.pages.user;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AddressOverviewPage extends AbstractBrowsingPage<AddressOverviewPage>
{
    private SelenideElement title = $("#title-address-overview");

    private SelenideElement addNewShippingAddressButton = $("#link-add-ship-addr");

    private SelenideElement addNewBillingAddressButton = $("#link-add-bill-addr");

    private SelenideElement goBackButton = $("#link-acc-overview");

    @Override
    @Step("ensure this is an address overview page")
    public AddressOverviewPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is an address overview page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(title, exist);
        return title.exists();
    }

    /// ========== validate content address overview page ========== ///
    ///
    /// @return

    @Override
    @Step("validate personal data page structure")
    public AddressOverviewPage validateStructure()
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

        return this;
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

        // for firefox the JavaScript click didn't work sometimes, so in this case click again normally
        AddNewShippingAddressPage addNewShippingAddressPage = new AddNewShippingAddressPage();
        if (!addNewShippingAddressPage.isExpectedPage())
        {
            addNewShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).click();
        }

        return addNewShippingAddressPage.assertExpectedPage();
    }

    @Step("add new billing address")
    public AddNewBillingAddressPage openAddNewBillingAddressPage()
    {
        addNewBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).click(ClickOptions.usingJavaScript());

        // for firefox the JavaScript click didn't work sometimes, so in this case click again normally
        AddNewBillingAddressPage addNewBillingAddressPage = new AddNewBillingAddressPage();
        if (!addNewBillingAddressPage.isExpectedPage())
        {
            addNewBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).click();
        }

        return new AddNewBillingAddressPage().assertExpectedPage();
    }

    @Step("go to account overview page")
    public AccountOverviewPage openAccountOverviewPage()
    {
        goBackButton.click(ClickOptions.usingJavaScript());
        return new AccountOverviewPage().assertExpectedPage();
    }
}
