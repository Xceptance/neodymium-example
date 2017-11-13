package com.xceptance.neodymium.scripting.template.selenide.page.browsing;

public class NoHitsPage extends AbstractBrowsingPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.AbstractPage()
     */
    public void validateStructure()
    {
    }

    /**
     * 
     */
    public void validateNoProductsfound()
    {
        validateErrorMessage("Sorry! No results found matching your search. Please try again.");
    }
}
