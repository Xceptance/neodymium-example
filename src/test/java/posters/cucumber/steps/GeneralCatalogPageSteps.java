package posters.cucumber.steps;

import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;

import cucumber.api.java8.En;

public class GeneralCatalogPageSteps implements En
{
    public GeneralCatalogPageSteps()
    {
        When("^I search for \"([^\"]*)\"$", (String searchTerm) -> {
            new HomePage().search().search(searchTerm);
        });
    }
}
