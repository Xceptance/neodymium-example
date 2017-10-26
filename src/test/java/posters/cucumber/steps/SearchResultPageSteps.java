package posters.cucumber.steps;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.conditions.Text;

import cucumber.api.java8.En;

public class SearchResultPageSteps implements En
{
    public SearchResultPageSteps()
    {
        // validate search result text
        Then("^the search result text should be \"([^\"]*)\"$", (String resultText) -> {

            Selenide.$("#titleSearchText").shouldHave(new Text(resultText));
        });
    }
}
