package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

import cucumber.api.java.en.Then;

public class Footer extends AbstractComponent
{
    final static String footerText = "Copyright (c) 2016 Xceptance Software Technologies -- This software is for demo purposes only and not meant to be used in production.";

    public void isComponentAvailable()
    {
        $("body > footer#footer").should(exist);
    }

    @Then("^the footer should be visible$")
    public void validate()
    {
        isComponentAvailable();
        // Asserts the footer contains the correct text.
        $("body > footer#footer").shouldHave(exactText(footerText));
    }
}
