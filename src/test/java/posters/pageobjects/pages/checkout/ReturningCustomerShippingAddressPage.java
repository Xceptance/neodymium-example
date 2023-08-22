package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

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

    /// ----- validate content returning customer shipping address page ----- ///
    
    @Step("validate breadcrumb")
    public void validateBreadcrumb()
    {
        $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.cart"))).shouldBe(visible);
        $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.shippingAddress"))).shouldBe(visible);
        $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.billingAddress"))).shouldBe(visible);
        $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.payment"))).shouldBe(visible);
        $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.placeOrder"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate returning customer shipping address page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate breadcrumb
        validateBreadcrumb();
        
        // validate process wrap
        // TODO - after fixing issue 171: consistent element selectors for all checkout pages with progress indicator
        //validateProcessWrap();
        
        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("ReturningCustomerShippingAddressPage.title"))).shouldBe(visible);
        
        // TODO - validate address count, get function needed
        
        // validate add new shipping address button
        addShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("ReturningCustomerShippingAddressPage.button.addNewShipAddr"))).shouldBe(visible);
        
        // validate continue button
        useShippingAddressButton.shouldHave(exactText(Neodymium.localizedText("ReturningCustomerShippingAddressPage.button.useShipAddr"))).shouldBe(visible);
    }
    
    @Step("validate specific position in address container")
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

    /// ----- select shipping address ----- ///

    @Step("select a shipping address")
    public ReturningCustomerBillingAddressPage selectShippingAddress(int position)
    {
        final int index = position - 1;
        
        // select address, press "Continue"
        $("#delAddr" + index + " input").scrollTo().click();
        useShippingAddressButton.scrollTo().click();

        return new ReturningCustomerBillingAddressPage().isExpectedPage();
    }
}
