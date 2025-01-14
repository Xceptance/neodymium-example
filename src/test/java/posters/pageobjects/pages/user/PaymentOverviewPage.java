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

public class PaymentOverviewPage extends AbstractBrowsingPage<PaymentOverviewPage>
{
    private SelenideElement title = $("#title-payment-overview");

    private SelenideElement addNewCreditCardButton = $("#link-add-new-payment");

    @Override
    @Step("ensure this is a payment overview page")
    public PaymentOverviewPage assertExpectedPage()
    {
        return super.assertExpectedPage();
    }

    @Override
    @Step("check if this is a payment overview page")
    public boolean isExpectedPage()
    {
        SelenideAddons.optionalWaitUntilCondition(title, exist);
        return title.exists();
    }

    /// ========== validate content payment overview page ========== ///
    ///
    /// @return

    @Override
    @Step("validate payment overview page structure")
    public PaymentOverviewPage validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("account.paymentSettings"))).shouldBe(visible);

        addNewCreditCardButton.shouldHave(exactText(Neodymium.localizedText("button.addNewCreditCard"))).shouldBe(visible);

        return this;
    }

    @Step("validate successful saved change")
    public void validateSuccessfulSave()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulSave"));
    }

    /// ========== payment overview page navigation ========== ///

    @Step("add new credit card")
    public AddNewCreditCardPage openAddNewCreditCardPage()
    {
        addNewCreditCardButton.click(ClickOptions.usingJavaScript());
        return new AddNewCreditCardPage().assertExpectedPage();
    }
}
