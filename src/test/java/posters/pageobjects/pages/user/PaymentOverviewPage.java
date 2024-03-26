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

public class PaymentOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#title-payment-overview");
    
    private SelenideElement addNewCreditCardButton = $("#link-add-new-payment");

    @Override
    @Step("ensure this is a payment overview page")
    public PaymentOverviewPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ========== validate content payment overview page ========== ///
    
    @Override
    @Step("validate payment overview page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("account.paymentSettings"))).shouldBe(visible);
        
        addNewCreditCardButton.shouldHave(exactText(Neodymium.localizedText("button.addNewCreditCard"))).shouldBe(visible);
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
        return new AddNewCreditCardPage().isExpectedPage();
    }
}