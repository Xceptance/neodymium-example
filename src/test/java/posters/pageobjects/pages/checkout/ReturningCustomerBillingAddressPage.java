package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.Address;

public class ReturningCustomerBillingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titleBillAddr");
    
    private SelenideElement addBillingAddressButton = $(".form-group .btn");
    
    private SelenideElement useBillingAddressButton = $("#btnUseBillAddress");

    @Override
    @Step("ensure this is a billing address page")
    public ReturningCustomerBillingAddressPage isExpectedPage()
    {
        $("#billAddr0").should(exist);
        return this;
    }
    
    /// ----- validate content returning customer billing address page ----- ///
    
    @Override
    @Step("validate returning customer billing address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();
        
        // validate process wrap
        // TODO - after fixing issue 171: consistent element selectors for all checkout pages with progress indicator
        //validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("ReturningCustomerBillingAddressPage.title"))).shouldBe(visible);
        
        // TODO - validate address count, get function needed
        
        // validate add new shipping address button
        addBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("ReturningCustomerBillingAddressPage.button.addNewBillAddr"))).shouldBe(visible);
        
        // validate continue button
        useBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("ReturningCustomerBillingAddressPage.button.useBillAddr"))).shouldBe(visible);
    }
    
    @Step("validate specific position in address container")
    public void validateAddressContainer(int position, Address billingAddress) 
    {
        final int index = position - 1;
        final SelenideElement addressContainer = $("#billAddr" + index);
        final String fullName = billingAddress.getFirstName() + " " + billingAddress.getLastName();
        
        // validate address data
        addressContainer.find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        addressContainer.find(".company").shouldHave(exactText(billingAddress.getCompany())).shouldBe(visible);
        addressContainer.find(".city").shouldHave(exactText(billingAddress.getCity())).shouldBe(visible);
        addressContainer.find(".state").shouldHave(exactText(billingAddress.getState())).shouldBe(visible);
        addressContainer.find(".zip").shouldHave(exactText(billingAddress.getZip())).shouldBe(visible);
        addressContainer.find(".country").shouldHave(exactText(billingAddress.getCountry())).shouldBe(visible);  
    }

    /// ----- select billing address ----- ///
    
    @Step("select a billing address")
    public ReturningCustomerPaymentPage selectBillingAddress(int position)
    {
        final int index = position - 1;
        
        // select address, press "Use this billing address"
        $("#billAddr" + index + " input").scrollTo().click();
        useBillingAddressButton.scrollTo().click();

        return new ReturningCustomerPaymentPage().isExpectedPage();
    }
}