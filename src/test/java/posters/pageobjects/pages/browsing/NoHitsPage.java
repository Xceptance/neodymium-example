package posters.pageobjects.pages.browsing;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class NoHitsPage extends AbstractBrowsingPage
{
    @Override
    public NoHitsPage isExpectedPage()
    {
        new HomePage().isExpectedPage();
        return this;
    }

    @Override
    @Step("validate no hits page structure")
    public NoHitsPage validateStructure()
    {
        super.validateStructure();
        return this;
    }

    @Step("validate that no products are on no hits page")
    public NoHitsPage validateNoProductsFound()
    {
        errorMessage.validateErrorMessage(Neodymium.localizedText("NoHitsPage.validation.noProductsFound"));
        return this;
    }

    @Step("validate no hits page")
    public NoHitsPage validate()
    {
        validateStructure();
        validateNoProductsFound();
        return this;
    }

}
