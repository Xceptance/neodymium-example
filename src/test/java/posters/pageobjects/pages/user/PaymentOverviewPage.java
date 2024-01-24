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
import posters.pageobjects.pages.browsing.HomePage;
import posters.tests.testdata.dataobjects.CreditCard;

public class PaymentOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titlePaymentOverview");
    
    private SelenideElement addNewPayment = $("#linkAddNewPayment");

    @Override
    @Step("ensure this is a personal data page")
    public PaymentOverviewPage isExpectedPage()
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
    
    @Step("validate successful saved change")
    public void validateSuccessfulSave()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("AccountOverviewPage.validation.successfulSave"));
    }
    
    /// ----- add new payment ----- ///
    
    @Step("open form to create new payment")
    public void openNewPayment() 
    {
        addNewPayment.click(ClickOptions.usingJavaScript());
    }
    
    @Step("fill in payment form")
    public void addNewPayment(CreditCard creditCard) 
    {
        // open add new payment form
        openNewPayment();
        
        // fill in payment form
        $("#creditCardNumber").val(creditCard.getCardNumber());
        $("#name").val(creditCard.getFullName());
        $("#expirationDateMonth").selectOption(creditCard.getExpDateMonth());
        $("#expirationDateYear").selectOption(creditCard.getExpDateYear());
        
        // click add new payment button
        $("#btnAddPayment").click(ClickOptions.usingJavaScript());
    }
    
    /// ----- payment overview page navigation ----- ///
    
    @Step("open homepage from payment overview page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}