package posters.pageObjects.pages.browsing;

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
        errorMessage.validateErrorMessage("Sorry! No results found matching your search. Please try again.");
    }

    public void validate()
    {
        validateNoProductsFound();
    }
}
