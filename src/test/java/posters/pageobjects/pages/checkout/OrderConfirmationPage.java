package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;

public class OrderConfirmationPage extends AbstractBrowsingPage{
    
    private SelenideElement homePageButton = $("#goHome");

    @Step("ensure this is the Order Confirmation page")
    public OrderConfirmationPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#tYouText").should(exist);
        return this;
    }
    
    /// ----- validate content order confirmation page ----- ///
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        // validate process numbers
        $("#crt span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.number"))).shouldBe(visible);
        $("#ship span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.number"))).shouldBe(visible);
        $("#bill span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.number"))).shouldBe(visible);
        $("#payment span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.number"))).shouldBe(visible);
        $("#chkout span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.number"))).shouldBe(visible);
        $("#orderCmplt span").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.number"))).shouldBe(visible);
        
        // validate process names
        $("#crt h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.1.name"))).shouldBe(visible);
        $("#ship h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.2.name"))).shouldBe(visible);
        $("#bill h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.3.name"))).shouldBe(visible);
        $("#payment h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.4.name"))).shouldBe(visible);
        $("#chkout h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.5.name"))).shouldBe(visible);
        $("#orderCmplt h3").shouldHave(exactText(Neodymium.localizedText("AddressPages.processWrap.6.name"))).shouldBe(visible);
    }
    
    @Step("validate order confirmation page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate success message
        successMessage.validateSuccessMessage(Neodymium.localizedText("HomePage.validation.successfulOrder"));
        
        // validate process wrap
        validateProcessWrap();
        
        // validate check icon
        $(".icon-check").shouldBe(visible);

        // validate thank you message
        $("#tYouText").shouldHave(exactText(Neodymium.localizedText("OrderConfirmationPage.thankYouMessage"))).shouldBe(visible);
        
        // Verifies GoTo HomePage button is visible
        $("#goHome .icon-shopping-cart").shouldBe(visible);
        $("#goHome").shouldHave(exactText(Neodymium.localizedText("OrderConfirmationPage.button"))).shouldBe(visible);
    }
    
    /// ----- order confirmation page navigation ----- ///
    
    @Step("go to homepage")
    public HomePage openHomePage()
    {
        homePageButton.scrollTo().click();

        return new HomePage().isExpectedPage();
    }
}
