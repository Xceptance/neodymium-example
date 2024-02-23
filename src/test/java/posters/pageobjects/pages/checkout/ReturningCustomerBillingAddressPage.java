package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
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
    
    /// ========== validate content returning customer billing address page ========== ///

    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        for (int i = 1; i <= 6; i++) 
        {
            $(".progress-step-" + i + " .progress-bubble").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".number"))).shouldBe(visible);
            $(".progress-step-" + i + " .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".name"))).shouldBe(visible);    
        }
    }
    
    @Override
    @Step("validate returning customer billing address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate process wrap
        validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("returningCustomerBillingAddressPage.title"))).shouldBe(visible);
        
        // validate first address
        $("#billAddr0").shouldBe(visible);
        
        // validate add new billing address button
        addBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).shouldBe(visible);
        
        // validate continue button
        useBillingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.useThisBillingAddress"))).shouldBe(visible);
    }
    
    @Step("validate billing address '{billingAddress}' on position '{position}' in address container")
    public void validateAddressContainer(int position, Address billingAddress) 
    {
        final int index = position - 1;
        final SelenideElement addressContainer = $("#billAddr" + index);
        final String fullName = billingAddress.getFirstName() + " " + billingAddress.getLastName();
        
        // validate address data
        addressContainer.find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        addressContainer.find(".company").shouldHave(exactText(billingAddress.getCompany())).shouldBe(visible);
        addressContainer.find(".addressLine").shouldHave(exactText(billingAddress.getStreet())).shouldBe(visible);
        addressContainer.find(".city").shouldHave(exactText(billingAddress.getCity())).shouldBe(visible);
        addressContainer.find(".state").shouldHave(exactText(billingAddress.getState())).shouldBe(visible);
        addressContainer.find(".zip").shouldHave(exactText(billingAddress.getZip())).shouldBe(visible);
        addressContainer.find(".country").shouldHave(exactText(billingAddress.getCountry())).shouldBe(visible);  
    }

    /// ========== select billing address ========== ///
    
    @Step("select a billing address on position '{position}'")
    public ReturningCustomerPaymentPage selectBillingAddress(int position)
    {
        final int index = position - 1;
        
        // select address, press "Use this billing address"
        $("#billAddr" + index + " input").click(ClickOptions.usingJavaScript());
        useBillingAddressButton.click(ClickOptions.usingJavaScript());

        return new ReturningCustomerPaymentPage().isExpectedPage();
    }
}