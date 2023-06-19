package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;

public class OrderConfirmationPage extends AbstractBrowsingPage{
    
    @Step("ensure this is the Order Confirmation page")
    public OrderConfirmationPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#tYouText").should(exist);
        return this;
    }
    
    public void validateStructure()
    {
        super.validateStructure();



        // Verifies the Navigation bar is visible
        $("#categoryMenu .navi").shouldBe(visible);
        
        // Asserts there's categories in the nav bar.
        $$("#categoryMenu .has-dropdown").shouldHave(sizeGreaterThan(0));

        // Asserts the first headline is there.
        // $("#titleIndex").shouldBe(matchText("[A-Z].{3,}"));
        
        // Verifies the Process chain is there.
        $(".process-wrap").shouldBe(visible);
        
        // Asserts the Confirmation Check is there.
        $(".confirmation #cnfmCheck").shouldBe(visible);

        // Verifies the Thank you text is visible
        $("#tYouText").shouldBe(visible);
        
        // Verifies GoTo HomePage button is visible
        $("#goHome").shouldBe(visible);
        
       
    }
    
    @Step("validate Order Confirmation page")
    public void validate()
    {
        validateStructure();
        footer.validate();
    }

    @Step("validate successful order on Confirmation page")
    public void validateSuccessfulOrder()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("HomePage.validation.successfulOrder"));
        // Verify that the mini cart is empty again
        miniCart.validateTotalCount(0);
        miniCart.validateSubtotal("$0.00");
    }
    
    
    

}
