package posters.pageobjects.pages.browsing;

import com.xceptance.neodymium.util.Context;

import io.qameta.allure.Step;

public class NoHitsPage extends AbstractBrowsingPage
{

    @Step("validate no hits page structure")
    public void validateStructure()
    {
        super.validateStructure();
    }

    @Step("validate that no products are on no hits page")
    public void validateNoProductsFound()
    {
        errorMessage.validateErrorMessage(Context.localizedText("NoHitsPage.validation.noProductsFound"));
    }

    @Step("validate no hits page")
    public void validate()
    {
        validateStructure();
        validateNoProductsFound();
    }
}
