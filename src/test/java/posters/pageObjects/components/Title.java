package posters.pageObjects.components;

import static com.codeborne.selenide.Selenide.title;

import org.junit.Assert;

import cucumber.api.java.en.Then;

public class Title extends AbstractComponent
{

    public void isComponentAvailable()
    {
    }

    @Then("^the page title should be \"([^\"]*)\"$")
    public void validateTitle(String title)
    {
        Assert.assertEquals(title, title());
    }
}
