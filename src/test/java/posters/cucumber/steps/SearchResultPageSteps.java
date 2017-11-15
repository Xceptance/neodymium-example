package posters.cucumber.steps;

import cucumber.api.java8.En;
import posters.pageObjects.pages.browsing.CategoryPage;

public class SearchResultPageSteps implements En
{
    public SearchResultPageSteps()
    {
        // validate search result text
        Then("^the search result text should be \"([^\"]*)\"$", (String resultText) -> {
            new CategoryPage().validateSearchHits(resultText);
        });
    }
}
