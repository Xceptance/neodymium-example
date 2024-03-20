package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;

public class Pagination extends AbstractComponent
{
    private SelenideElement pagination = $("#pagination-bottom");

    private ElementsCollection paginationComponents = $$("#pagination-bottom li");

    public void isComponentAvailable()
    {
        pagination.shouldBe(visible);
    }

    /// ========== pagination navigation ========== ///

    @Step("click on page number '{pageNumber}'")
    public void goToPage(int pageNumber)
    {
        paginationComponents.findBy(exactText(Integer.toString(pageNumber))).click();
    }

    @Step("click on go to last page")
    public void goToLastPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("categoryPage.pagination.goToLastPage"))).click();
    }

    @Step("click on go to first page")
    public void goToFirstPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("categoryPage.pagination.goToFirstPage"))).click();
    }

    /// ========== validate pagination ========== ///

    /**
     * To get {numberOfPages} it divides {expectedResultCount} by 8 because only 8 results can be displayed
     * per page. Then it loops through the page numbers and validates its existence and visibility.
     * 
     * @param expectedResultCount number of results for specific category/search
     */
    @Step("validate visibility of {expectedResultCount} in pagination")
    public void validateElementNumbers(int expectedResultCount)
    {
        int numberOfPages = expectedResultCount / 8;
        for (int i = 1; i <= numberOfPages; i++)
        {
            paginationComponents.findBy(exactText(Integer.toString(i))).shouldBe(visible);
        }
    }

    @Step("validate visibility right navigation")
    public void validateRightNavigation()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("categoryPage.pagination.goToLastPage"))).shouldBe(visible);
    }

    @Step("validate visibility left navigation")
    public void validateLeftNavigation()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("categoryPage.pagination.goToFirstPage"))).shouldBe(visible);
    }

    /**
     * If there is more than 1 result page it validates the pagination. It loops through the page numbers
     * and validates the existence and visibility of the numbers themselves and the icon navigation (<<, >>)
     * 
     * @param expectedResultCount number of results for specific category/search
     */
    @Step("validate pagination")
    public void validateStructure(int expectedResultCount)
    {
        int numberOfPages;
        if (expectedResultCount % 8 == 0) 
        {
            numberOfPages = expectedResultCount / 8;
        }
        else 
        {
            numberOfPages = expectedResultCount / 8 + 1;
        }
        
        if (numberOfPages > 1)
        {
            pagination.shouldBe(visible);

            // validate click on pagination numbers works properly
            for (int i = 1; i <= numberOfPages; i++)
            {
                goToPage(i);
                $(".active").shouldHave(exactText(Integer.toString(i)));
                validateElementNumbers(expectedResultCount);
                if (i > 1) 
                {
                    validateLeftNavigation();
                }
                if (i < numberOfPages) 
                {
                    validateRightNavigation();                    
                }
            }

            // validate that jump to first page button works properly
            goToFirstPage();
            $(".active").shouldHave(exactText(Integer.toString(1)));            
            validateRightNavigation();
            validateElementNumbers(expectedResultCount);
            
            // validate that jump to last page button works properly
            goToLastPage();
            $(".active").shouldHave(exactText(Integer.toString(numberOfPages)));            
            validateLeftNavigation();
            validateElementNumbers(expectedResultCount);
        }
        else return;
    }
}
