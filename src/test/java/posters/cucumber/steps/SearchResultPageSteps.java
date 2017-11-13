package posters.cucumber.steps;

import com.xceptance.neodymium.scripting.template.selenide.page.browsing.CategoryPage;

import cucumber.api.java8.En;

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
