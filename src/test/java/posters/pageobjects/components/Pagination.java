package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import java.util.Random;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

public class Pagination extends AbstractComponent
{
    private SelenideElement pagination = $("#pagination-bottom");

    public void isComponentAvailable()
    {
        pagination.shouldBe(visible);
    }

    @Step("get number of available pages")
    public int getNumberOfSites()
    {
        return pagination.findAll("li > a").filter(matchText("\\d+")).size() + 1;
    }

    @Step("get the link to a specific page")
    public SelenideElement getSite(String stringNumber)
    {
        return pagination.find(Selectors.withText(stringNumber));
    }

    @Step("click on any link represented by a number")
    public String clickOnRandomSite(Random random)
    {
        String stringChosenElementValue = Integer.toString(random.nextInt(getNumberOfSites()));
        SelenideElement chosenPaginationLink = getSite(stringChosenElementValue);
        chosenPaginationLink.scrollTo();
        chosenPaginationLink.click();

        return stringChosenElementValue;
    }
}
