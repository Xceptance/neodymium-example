package posters.cucumber.steps;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;

import org.junit.Assert;

import com.codeborne.selenide.conditions.Text;

import cucumber.api.java8.En;
import posters.cucumber.util.Driver;

public class HomePageSteps implements En
{
    public HomePageSteps()
    {
        // open home page
        Given("^I am on the homepage of the Posters shop in browser \"([^\"]*)\"$", (String browser) -> {
            Driver.setUp(browser);
            open("https://localhost:8443/posters/");
        });

        // validate page title
        Then("^the page title should be \"([^\"]*)\"$", (String title) -> {
            Assert.assertEquals(title, title());
        });

        // validate error message
        Then("^a message with text \"([^\"]*)\" should appear$", (String message) -> {
            $("div.alert-danger strong").shouldHave(new Text(message));
        });
    }
}
