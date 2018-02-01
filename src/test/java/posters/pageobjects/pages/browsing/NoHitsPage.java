package posters.pageobjects.pages.browsing;

import com.xceptance.neodymium.util.Context;

public class NoHitsPage extends AbstractBrowsingPage
{
    public void validateStructure()
    {
        super.validateStructure();
    }

    /**
     * 
     */
    public void validateNoProductsFound()
    {
        errorMessage.validateErrorMessage(Context.localizedText("NoHitsPage.validation.noProductsFound"));
    }

    public void validate()
    {
        validateNoProductsFound();
    }
}
