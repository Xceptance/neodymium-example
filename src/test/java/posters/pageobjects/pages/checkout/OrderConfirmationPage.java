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
