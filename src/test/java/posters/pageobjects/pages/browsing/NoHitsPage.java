package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

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
}
