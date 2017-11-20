package posters.pageObjects.pages.browsing;

import cucumber.api.java.en.Then;

public class NoHitsPage extends AbstractBrowsingPage
{
    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.AbstractPage()
     */
    public void validateStructure()
    {
        super.validateStructure();
    }

    /**
     * 
     */
    public void validateNoProductsFound()
    {
        errorMessage().validateErrorMessage("Sorry! No results found matching your search. Please try again.");
    }

    @Then("^I want to be on a no hits page$")
    public void validate()
    {
        validateNoProductsFound();
    }
}
