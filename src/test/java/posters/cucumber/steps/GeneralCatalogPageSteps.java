package posters.cucumber.steps;

import static com.codeborne.selenide.Selenide.$;

import cucumber.api.java8.En;

public class GeneralCatalogPageSteps implements En
{
    public GeneralCatalogPageSteps()
    {
        When("^I search for \"([^\"]*)\"$", (String searchTerm) -> {
            $("#header-search-trigger").click();
            $("#s").sendKeys(searchTerm);
            $("#btnSearch").click();
        });
    }
}
