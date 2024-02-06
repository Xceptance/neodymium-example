package posters.pageobjects.pages.user;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
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
    
    private SelenideElement fullNameField = $("#address-form-input-full-name");
    
    private SelenideElement companyField = $("#address-form-input-company");
    
    private SelenideElement addressLineField = $("#address-form-input-adress-line");
    
    private SelenideElement cityField = $("#address-form-input-city");
    
    private SelenideElement stateField = $("#address-form-input-state");
    
    private SelenideElement zipField = $("#address-form-input-zip");
    
    private SelenideElement countryField = $("#address-form-select-country");

    @Override
    @Step("ensure this is a personal data page")
    public AddressOverviewPage isExpectedPage()
    {
        super.isExpectedPage();
        title.should(exist);
        return this;
    }

    /// ========== validate content my addresses page ========== ///

    @Override
    @Step("validate personal data page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate title
        title.shouldHave(exactText(Neodymium.localizedText("myAddressesPage.title"))).shouldBe(visible);

        // validate shipping addresses overview
        $("#titleDelAddr").shouldHave(exactText(Neodymium.localizedText("account.shippingAddress"))).shouldBe(visible);
        addNewShipAddr.shouldHave(exactText(Neodymium.localizedText("button.addNewShippingAddress"))).shouldBe(visible);

        // validate billing addresses overview
        $("#titleBillAddr").shouldHave(exactText(Neodymium.localizedText("account.billingAddress"))).shouldBe(visible);
        addNewBillAddr.shouldHave(exactText(Neodymium.localizedText("button.addNewBillingAddress"))).shouldBe(visible);
    }
    
    @Step("validate successful saved change")
    public void validateSuccessfulSave()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulSave"));
    }
    
    /// ========== add new address ========== ///
    
    @Step("open form to create new shipping address")
    public void openNewShipAddr() 
    {
        addNewShipAddr.click(ClickOptions.usingJavaScript());
    }

    @Step("open form to create new billing address")
    public void openNewBillAddr() 
    {
        addNewBillAddr.click(ClickOptions.usingJavaScript());
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
        $("#btnAddShippAddr").click(ClickOptions.usingJavaScript());
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
        $("#btnAddBillAddr").click(ClickOptions.usingJavaScript());
    }  
}