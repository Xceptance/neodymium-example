package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;

public class PaymentOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titlePaymentOverview");
    
    private SelenideElement addNewCreditCardButton = $("#linkAddNewPayment");

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
        
        $("#linkAddNewPayment").shouldHave(exactText(Neodymium.localizedText("button.addNewCreditCard"))).shouldBe(visible);
    }
    
    @Step("validate successful saved change")
    public void validateSuccessfulSave()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulSave"));
    }    
    
    /// ========== payment overview page navigation ========== ///
    
    @Step("open homepage from payment overview page")
    public HomePage openHomePage()
    {
        $("#header-brand").click();
        return new HomePage().isExpectedPage();
    }
    
    @Step("add new credit card")
    public AddNewCreditCardPage openAddNewCreditCardPage() 
    {
        addNewCreditCardButton.click();
        return new AddNewCreditCardPage().isExpectedPage();
    }
}