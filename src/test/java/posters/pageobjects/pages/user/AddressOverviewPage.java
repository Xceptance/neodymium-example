package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.tests.testdata.dataobjects.Address;

public class AddressOverviewPage extends AbstractBrowsingPage
{
    private SelenideElement title = $("#titleAddressOverview");
    
    private SelenideElement addNewShipAddr = $("#linkAddNewShipAddr");
    
    private SelenideElement addNewBillAddr = $("#linkAddNewBillAddr");
    
    private SelenideElement fullNameField = $("#fullName");
    
    private SelenideElement companyField = $("#company");
    
    private SelenideElement addressLineField = $("#addressLine");
    
    private SelenideElement cityField = $("#city");
    
    private SelenideElement stateField = $("#state");
    
    private SelenideElement zipField = $("#zip");
    
    private SelenideElement countryField = $("#country");

    @Override
    @Step("ensure this is a personal data page")
    public AddressOverviewPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ----- validate content my addresses page ----- ///

    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("MyAddressesPage.title"))).shouldBe(visible);

        // validate shipping addresses overview
        $("#titleDelAddr").shouldHave(exactText(Neodymium.localizedText("MyAddressesPage.headlines.shipAddr"))).shouldBe(visible);
        addNewShipAddr.shouldHave(exactText(Neodymium.localizedText("General.button.addNewShipAddr"))).shouldBe(visible);

        // validate billing addresses overview
        $("#titleBillAddr").shouldHave(exactText(Neodymium.localizedText("MyAddressesPage.headlines.billAddr"))).shouldBe(visible);
        addNewBillAddr.shouldHave(exactText(Neodymium.localizedText("General.button.addNewBillAddr"))).shouldBe(visible);
    }
    
    @Step("validate successful saved change")
    public void validateSuccessfulSave()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("AccountOverviewPage.validation.successfulSave"));
    }
    
    /// ----- add new address ----- ///
    
    @Step("open form to create new shipping address")
    public void openNewShipAddr() 
    {
        addNewShipAddr.click();
    }

    @Step("open form to create new billing address")
    public void openNewBillAddr() 
    {
        addNewBillAddr.click();
    }
    
    @Step("fill in shipping address form")
    public void addNewShipAddr(Address shippingAddress) 
    {
        // open add new address form
        openNewShipAddr();
        
        // fill in shipping address form
        fullNameField.val(shippingAddress.getFullName());
        companyField.val(shippingAddress.getCompany());
        addressLineField.val(shippingAddress.getStreet());
        cityField.val(shippingAddress.getCity());
        stateField.val(shippingAddress.getState());
        zipField.val(shippingAddress.getZip());
        countryField.selectOption(shippingAddress.getCountry());
        
        // click add new address button
        $("#btnAddShippAddr").scrollTo().click();
    }
    
    @Step("fill in billing address form")
    public void addNewBillAddr(Address billingAddress) 
    {
        // open add new address form
        openNewBillAddr();
        
        // fill in shipping address form
        fullNameField.val(billingAddress.getFullName());
        companyField.val(billingAddress.getCompany());
        addressLineField.val(billingAddress.getStreet());
        cityField.val(billingAddress.getCity());
        stateField.val(billingAddress.getState());
        zipField.val(billingAddress.getZip());
        countryField.selectOption(billingAddress.getCountry());
        
        // click add new address button
        $("#btnAddBillAddr").scrollTo().click();
    }  
}