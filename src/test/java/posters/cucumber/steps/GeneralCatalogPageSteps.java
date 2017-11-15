package posters.cucumber.steps;

import cucumber.api.java8.En;
import posters.pageObjects.pages.browsing.HomePage;

public class GeneralCatalogPageSteps implements En
{
    public GeneralCatalogPageSteps()
    {
        When("^I search for \"([^\"]*)\"$", (String searchTerm) -> {
            new HomePage().search().search(searchTerm);
        });
    }
}
