package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class PaymentSettingsPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titlePaymentOverview");

    @Override
    @Step("ensure this is a personal data page")
    public PaymentSettingsPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate content payment settings page ----- ///
    
    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("PaymentSettingsPage.title"))).shouldBe(visible);
        
        $("#linkAddNewPayment").shouldHave(exactText(Neodymium.localizedText("PaymentSettingsPage.button.addNewCreditCard"))).shouldBe(visible);
    }
}