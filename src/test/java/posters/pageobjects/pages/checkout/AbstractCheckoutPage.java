package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.components.CheckoutHeader;
import posters.pageobjects.components.Footer;
import posters.pageobjects.components.UserMenu;
import posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractCheckoutPage extends AbstractPageObject
{
    public CheckoutHeader checkoutHeader = new CheckoutHeader();

    public Footer footer = new Footer();

    public UserMenu userMenu = new UserMenu();

    @Override
    public void validateStructure()
    {
        isExpectedPage();

        checkoutHeader.isComponentAvailable();
        footer.isComponentAvailable();
        userMenu.isComponentAvailable();
    }
    
    /// ----- ShippingAddressPage, BillingAddressPage, PaymentPage, PlaceOrderPage ----- ///
    
    @Step("validate breadcrumb")
    public void validateBreadcrumb()
    {
        $("#btnToCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.cart"))).shouldBe(visible);
        $("#btnShippAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.shippingAddress"))).shouldBe(visible);
        $("#btnBillAddr").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.billingAddress"))).shouldBe(visible);
        $("#btnCreditCard").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.payment"))).shouldBe(visible);
        $("#btnPlaceOrder").shouldHave(exactText(Neodymium.localizedText("AddressPages.breadcrumb.placeOrder"))).shouldBe(visible);
    }
    
    /// ----- ShippingAddressPage, BillingAddressPage ----- ///
    
    @Step("validate fill-in form headlines")
    public void validateFillInHeadlines()
    {
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.fullName")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.company")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.address")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.city")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.state")));
        $$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.zip")));
        //$$(".form-group").findBy(exactText(Neodymium.localizedText("AddressPages.fillIn.headlines.country")));
    }
    
    @Step("validate fill-in form placeholder")
    public void validateFillInPlaceholder()
    {
        $("#fullName").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.yourName")))).shouldBe(visible);
        $("#company").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.companyName")))).shouldBe(visible);
        $("#addressLine").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.address")))).shouldBe(visible);
        $("#zip").shouldHave(attribute("placeholder", (Neodymium.localizedText("AddressPages.fillIn.placeholder.zip")))).shouldBe(visible);
    }
    
    @Step("validate country dropdown")
    public void validateCountryDropdown()
    {
        $("#country").shouldBe(matchText(Neodymium.localizedText("AddressPages.fillIn.dropdown.usa"))).should(exist);
        $("#country").shouldBe(matchText(Neodymium.localizedText("AddressPages.fillIn.dropdown.germany"))).should(exist);
    }
}
