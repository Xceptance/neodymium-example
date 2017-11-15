package posters.cucumber.steps;

import cucumber.api.java8.En;
import posters.cucumber.util.Driver;
import posters.neodymium.flow.OpenHomePageFlow;
import posters.pageObjects.pages.browsing.HomePage;

public class HomePageSteps implements En
{
    public HomePageSteps()
    {
        // open home page
        Given("^I am on the homepage of the Posters shop in browser \"([^\"]*)\"$", (String browser) -> {
            Driver.setUp(browser);
            new OpenHomePageFlow().flow();
        });

        // validate page title
        Then("^the page title should be \"([^\"]*)\"$", (String title) -> {
            new HomePage().validateTitle(title);
        });

        // validate error message
        Then("^a message with text \"([^\"]*)\" should appear$", (String message) -> {
            new HomePage().validateErrorMessage(message);
        });

    }
}
