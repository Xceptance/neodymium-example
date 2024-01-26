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

public class ReturningCustomerShippingAddressPage extends AbstractCheckoutPage
{
    private SelenideElement title = $("#titleDelAddr");
    
    private SelenideElement addShippingAddressButton = $(".form-group .btn");
    
    private SelenideElement useShippingAddressButton = $("#btnUseAddressContinue");

    @Override
    @Step("ensure this is a shipping address page")
    public ReturningCustomerShippingAddressPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#delAddr0").should(exist);
        return this;
    }

    /// ========== validate content returning customer shipping address page ========== ///
    
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
    @Step("validate returning customer shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();
        
        // validate process wrap
        validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("returningCustomerShippingAddressPage.title"))).shouldBe(visible);
        
        // TODO - validate address count, get function needed
        
        // validate add new shipping address button
        addShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).shouldBe(visible);
        
        // validate continue button
        useShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("button.continue"))).shouldBe(visible);
    }
    
    @Step("validate shipping address '{shippingAddress}' on position '{position}' in address container")
    public void validateAddressContainer(int position, Address shippingAddress) 
    {
        final int index = position - 1;
        final SelenideElement addressContainer = $("#delAddr" + index);
        final String fullName = shippingAddress.getFirstName() + " " + shippingAddress.getLastName();
        
        // validate address data
        addressContainer.find(".name").shouldHave(exactText(fullName)).shouldBe(visible);
        addressContainer.find(".company").shouldHave(exactText(shippingAddress.getCompany())).shouldBe(visible);
        addressContainer.find(".city").shouldHave(exactText(shippingAddress.getCity())).shouldBe(visible);
        addressContainer.find(".state").shouldHave(exactText(shippingAddress.getState())).shouldBe(visible);
        addressContainer.find(".zip").shouldHave(exactText(shippingAddress.getZip())).shouldBe(visible);
        addressContainer.find(".country").shouldHave(exactText(shippingAddress.getCountry())).shouldBe(visible);  
    }

    /// ========== select shipping address ========== ///

    @Step("select a shipping address on position '{position}'")
    public ReturningCustomerBillingAddressPage selectShippingAddress(int position)
    {
        final int index = position - 1;
        
        // select address, press "Continue"
        $("#delAddr" + index + " input").click(ClickOptions.usingJavaScript());
        useShippingAddressButton.click(ClickOptions.usingJavaScript());

        return new ReturningCustomerBillingAddressPage().isExpectedPage();
    }
}
