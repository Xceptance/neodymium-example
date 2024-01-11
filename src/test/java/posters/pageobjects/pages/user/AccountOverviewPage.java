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
    private SelenideElement title = $("#titleAccountOverview");
    
    private SelenideElement orderOverviewLink = $("#linkOrderOverview");
    
    private SelenideElement myAddressesLink = $("#linkAddressOverview");
    
    private SelenideElement paymentSettingsLink = $("#linkPaymentOverview");

    private SelenideElement personalDataLink = $("#linkSettingOverview");

    @Override
    @Step("ensure this is an account overview page")
    public AccountOverviewPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate content account overview page ----- ///
    
    @Override
    @Step("validate account overview page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("AccountOverviewPage.title"))).shouldBe(visible);
        
        // validate navigation buttons
        orderOverviewLink.shouldHave(exactText(Neodymium.localizedText("AccountOverviewPage.button.orderHistory"))).shouldBe(visible);
        myAddressesLink.shouldHave(exactText(Neodymium.localizedText("AccountOverviewPage.button.myAddresses"))).shouldBe(visible);
        paymentSettingsLink.shouldHave(exactText(Neodymium.localizedText("AccountOverviewPage.button.paymentSettings"))).shouldBe(visible);
        personalDataLink.shouldHave(exactText(Neodymium.localizedText("AccountOverviewPage.button.personalData"))).shouldBe(visible);
    }

    /// ----- account overview page navigation ----- ///
    
    @Step("open order history page")
    public OrderHistoryPage openOrderHistory()
    {
        orderOverviewLink.scrollTo().click(ClickOptions.usingJavaScript());
        return new OrderHistoryPage().isExpectedPage();
    }
    
    @Step("open my addresses page")
    public AddressOverviewPage openMyAddresses()
    {
        myAddressesLink.scrollTo().click(ClickOptions.usingJavaScript());
        return new AddressOverviewPage().isExpectedPage();
    }
    
    @Step("open payment settings page")
    public PaymentOverviewPage openPaymentSettings()
    {
        paymentSettingsLink.scrollTo().click(ClickOptions.usingJavaScript());
        return new PaymentOverviewPage().isExpectedPage();
    }
    
    @Step("open personal data page")
    public PersonalDataPage openPersonalData()
    {
        personalDataLink.scrollTo().click(ClickOptions.usingJavaScript());
        return new PersonalDataPage().isExpectedPage();
    }
}
