package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import java.util.Random;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.pages.browsing.CategoryPage;

public class Pagination extends AbstractComponent
{
    private static SelenideElement pagination = $("#pagination-bottom");
    
    private static ElementsCollection paginationComponents = $$("#pagination-bottom li");

    public void isComponentAvailable()
    {
        pagination.shouldBe(visible);
    }
    
    /// ----- pagination navigation ----- ///
    
    @Step("click on specific page number")
    public static void goToPage(int pageNumber)
    {
        paginationComponents.findBy(exactText(Integer.toString(pageNumber))).shouldBe(visible).click();
    }
    
    @Step("click on go to next page")
    public static void goToNextPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("pagination.goToNextPage"))).click();
    }
    
    @Step("click on go to last page")
    public static void goToLastPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("pagination.goToLastPage"))).click();
    }
    
    @Step("click on go to previous page")
    public static void goToPrevPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("pagination.goToPrevPage"))).click();
    }
    
    @Step("click on go to first page")
    public static void goToFirstPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("pagination.goToFirstPage"))).click();
    }
    
    /// ----- validate pagination ----- ///
    
    @Step("validate visibility numbers in navigation")
    public static void validateElementNumbers(int expectedResultCount) 
    {
        int numberOfPages = expectedResultCount / 6;
        for (int i = 1; i < numberOfPages; i++)
        {
            paginationComponents.findBy(exactText(Integer.toString(i))).shouldBe(visible);
        }
    }
    
    @Step("validate visibility right navigation")
    public static void validateRightNavigation()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("pagination.goToNextPage"))).shouldBe(visible);
        paginationComponents.findBy(exactText(Neodymium.localizedText("pagination.goToLastPage"))).shouldBe(visible);
    }
    
    @Step("validate visibility left navigation")
    public static void validateLeftNavigation()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("pagination.goToPrevPage"))).shouldBe(visible);
        paginationComponents.findBy(exactText(Neodymium.localizedText("pagination.goToFirstPage"))).shouldBe(visible);
    }
    
    @Step("validate pagination")
    public static void validateStructure(int expectedResultCount)
    {
        int numberOfPages = expectedResultCount / 6;
        if (numberOfPages > 1)
        {
            pagination.shouldBe(visible);
       
            validateElementNumbers(expectedResultCount);
            validateRightNavigation();
            
            for (int j = 2; j < numberOfPages; j++)
            {
                goToPage(j);
                validateLeftNavigation();
                validateElementNumbers(expectedResultCount);
                validateRightNavigation();
            }
            
            goToLastPage();
            validateLeftNavigation();
            validateElementNumbers(expectedResultCount);
        }
        else return;    
    }
    
    // ---------------------------------------------
    
    @Step("get number of available pages")
    public int getNumberOfSites()
    {
        return pagination.findAll("li > a").filter(matchText("\\d+")).size();
    }

    @Step("get the link to a specific page")
    public SelenideElement getSite(String stringNumber)
    {
        return pagination.find(Selectors.withText(stringNumber));
    }

    @Step("click on any link represented by a number")
    public CategoryPage clickOnRandomSite(Random random)
    {
        String stringChosenElementValue = Integer.toString(random.nextInt(getNumberOfSites()) + 1);
        SelenideElement chosenPaginationLink = getSite(stringChosenElementValue);
        chosenPaginationLink.scrollTo();
        chosenPaginationLink.click();

        return new CategoryPage().isExpectedPage();
    }
}
