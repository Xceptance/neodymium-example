package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.pageobjects.pages.browsing.HomePage;

public class OrderConfirmationPage extends AbstractBrowsingPage
{
    private SelenideElement homePageButton = $("#goHome");

    @Step("ensure this is the Order Confirmation page")
    public OrderConfirmationPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#tYouText").should(exist);
        return this;
    }
    
    /// ========== validate content order confirmation page ========== ///
    
    @Step("validate process wrap")
    public void validateProcessWrap() 
    {
        for (int i = 1; i <= 6; i++) 
        {
            $(".progress-step-" + i + " .progress-bubble").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".number"))).shouldBe(visible);
            $(".progress-step-" + i + " .progress-bubble-caption").shouldHave(exactText(Neodymium.localizedText("checkoutHeader." + i + ".name"))).shouldBe(visible);    
        }
    }
    
    @Step("validate order confirmation page structure")
    public void validateStructure()
    {
        super.validateStructure();

        // validate success message
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulOrder"));
        
        // validate process wrap
        validateProcessWrap();
        
        // validate check icon
        $(".icon-check").shouldBe(visible);

        // validate thank you message
        $("#tYouText").shouldHave(exactText(Neodymium.localizedText("orderConfirmationPage.thankYouMessage"))).shouldBe(visible);
        
        // Verifies GoTo HomePage button is visible
        $("#goHome .icon-shopping-cart").shouldBe(visible);
        $("#goHome").shouldHave(exactText(Neodymium.localizedText("button.continueShopping"))).shouldBe(visible);
    }
    
    @Step("validate successful order")
    public void validateSuccessfulOrder()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulOrder"));
    }
    
    /// ========== order confirmation page navigation ========== ///
    
    @Step("open homepage from order confirmation page")
    public HomePage openHomePage()
    {
        homePageButton.click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}
