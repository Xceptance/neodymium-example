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

public class AccountOverviewPage extends AbstractBrowsingPage<AccountOverviewPage>
{
    private SelenideElement title = $("#title-account-overview");

    private SelenideElement orderOverviewLink = $("#link-order-overview");

    private SelenideElement myAddressesLink = $("#link-address-overview");

    private SelenideElement paymentSettingsLink = $("#link-payment-overview");

    private SelenideElement personalDataLink = $("#link-setting-overview");

    @Override
    @Step("ensure this is an account overview page")
    public AccountOverviewPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is an account overview page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(title, exist);
        return title.exists();
    }

    /// ========== validate content account overview page ========== ///
    ///
    /// @return

    @Override
    @Step("validate account overview page structure")
    public AccountOverviewPage validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("accountOverviewPage.title"))).shouldBe(visible);

        // validate navigation buttons
        orderOverviewLink.shouldHave(exactText(Neodymium.localizedText("button.orderHistory"))).shouldBe(visible);
        myAddressesLink.shouldHave(exactText(Neodymium.localizedText("button.myAddresses"))).shouldBe(visible);
        paymentSettingsLink.shouldHave(exactText(Neodymium.localizedText("button.paymentSettings"))).shouldBe(visible);
        personalDataLink.shouldHave(exactText(Neodymium.localizedText("button.personalData"))).shouldBe(visible);

        $(".icon-book").shouldBe(visible);
        $(".icon-history").shouldBe(visible);
        $(".icon-credit-card").shouldBe(visible);
        $(".icon-cog").shouldBe(visible);

        return this;
    }

    @Step("validate successful login of user '{firstName}' on home page")
    public void validateSuccessfulLogin(String firstName)
    {
        // validate success message
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulLogin"));

        // validate {firstName} in user menu
        header.userMenu.validateLoggedInUserName(firstName);
    }

    /// ========== account overview page navigation ========== ///

    @Step("open order history page")
    public OrderHistoryPage openOrderHistory()
    {
        orderOverviewLink.click(ClickOptions.usingJavaScript());
        return new OrderHistoryPage().assertExpectedPage();
    }

    @Step("open my addresses page")
    public AddressOverviewPage openMyAddresses()
    {
        myAddressesLink.click(ClickOptions.usingJavaScript());
        return new AddressOverviewPage().assertExpectedPage();
    }

    @Step("open payment settings page")
    public PaymentOverviewPage openPaymentSettings()
    {
        paymentSettingsLink.click(ClickOptions.usingJavaScript());
        return new PaymentOverviewPage().assertExpectedPage();
    }

    @Step("open personal data page")
    public PersonalDataPage openPersonalData()
    {
        personalDataLink.click(ClickOptions.usingJavaScript());
        return new PersonalDataPage().assertExpectedPage();
    }
}
