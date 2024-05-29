package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class AccountOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#title-account-overview");
    
    private SelenideElement orderOverviewLink = $("#link-order-overview");
    
    private SelenideElement myAddressesLink = $("#link-address-overview");
    
    private SelenideElement paymentSettingsLink = $("#link-payment-overview");

    private SelenideElement personalDataLink = $("#link-setting-overview");

    @Override
    @Step("ensure this is an account overview page")
    public AccountOverviewPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ========== validate content account overview page ========== ///
    
    @Override
    @Step("validate account overview page structure")
    public void validateStructure()
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
    }
    
    @Step("validate successful login of user '{firstName}' on home page")
    public void validateSuccessfulLogin(String firstName)
    {
        // validate success message
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulLogin"));
        
        // validate {firstName} in user menu
        header.userMenu.validateLoggedInName(firstName);
    }

    /// ========== account overview page navigation ========== ///
    
    @Step("open order history page")
    public OrderHistoryPage openOrderHistory()
    {
        orderOverviewLink.click(ClickOptions.usingJavaScript());
        return new OrderHistoryPage().isExpectedPage();
    }
    
    @Step("open my addresses page")
    public AddressOverviewPage openMyAddresses()
    {
        myAddressesLink.click(ClickOptions.usingJavaScript());
        return new AddressOverviewPage().isExpectedPage();
    }
    
    @Step("open payment settings page")
    public PaymentOverviewPage openPaymentSettings()
    {
        paymentSettingsLink.click(ClickOptions.usingJavaScript());
        return new PaymentOverviewPage().isExpectedPage();
    }
    
    @Step("open personal data page")
    public PersonalDataPage openPersonalData()
    {
        personalDataLink.click(ClickOptions.usingJavaScript());
        return new PersonalDataPage().isExpectedPage();
    }
}
