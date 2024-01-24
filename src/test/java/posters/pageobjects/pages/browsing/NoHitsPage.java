package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.ClickOptions;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class NoHitsPage extends AbstractBrowsingPage
{   
    @Override
    @Step("ensure this is a no hits page")
    public NoHitsPage isExpectedPage()
    {
        super.isExpectedPage();
        $("#errorMessage").should(exist);
        return this;
    }
    
    /// ========== validate no hits page ========== ///
    
    @Step("validate that no products are on no hits page")
    public void validateNoProductsFound()
    {
        errorMessage.validateErrorMessage(Neodymium.localizedText("NoHitsPage.validation.noProductsFound"));
    }
    
    @Override
    @Step("validate structure no hits page")
    public void validateStructure()
    {
        super.validateStructure();
        validateNoProductsFound();
    }
    
    /// ========== no hits page navigation ========== ///
    
    @Step("open homepage from no hits page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}
