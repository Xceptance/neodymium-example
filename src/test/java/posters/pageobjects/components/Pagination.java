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

    /// ----- pagination navigation ----- ///

    @Step("click on page number '{pageNumber}'")
    public void goToPage(int pageNumber)
    {
        paginationComponents.findBy(exactText(Integer.toString(pageNumber))).click();
    }

    @Step("click on go to next page")
    public void goToNextPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("CategoryPage.pagination.goToNextPage"))).click();
    }

    @Step("click on go to last page")
    public void goToLastPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("CategoryPage.pagination.goToLastPage"))).click();
    }

    @Step("click on go to previous page")
    public void goToPrevPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("CategoryPage.pagination.goToPrevPage"))).click();
    }

    @Step("click on go to first page")
    public void goToFirstPage()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("CategoryPage.pagination.goToFirstPage"))).click();
    }

    /// ----- validate pagination ----- ///

    /**
     * Note: To get {numberOfPages} it divides {expectedResultCount} by 6 because only 6 results can be displayed
     * per page. Then it loops through the page numbers and validates its existence and visibility.
     * 
     * @param expectedResultCount
     *            (number of results for specific category/search)
     */
    @Step("validate visibility numbers in pagination")
    public void validateElementNumbers(int expectedResultCount)
    {
        int numberOfPages = expectedResultCount / 6;
        for (int i = 1; i < numberOfPages; i++)
        {
            paginationComponents.findBy(exactText(Integer.toString(i))).shouldBe(visible);
        }
    }

    @Step("validate visibility right navigation")
    public void validateRightNavigation()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("CategoryPage.pagination.goToLastPage"))).shouldBe(visible);
    }

    @Step("validate visibility left navigation")
    public void validateLeftNavigation()
    {
        paginationComponents.findBy(exactText(Neodymium.localizedText("CategoryPage.pagination.goToFirstPage"))).shouldBe(visible);
    }

    /**
     * Note: If there is more than 1 result page it validates the pagination. It loops through the page numbers
     * and validates the existence and visibility of the numbers themselves and the icon navigation (<, <<, >, >>)
     * 
     * @param expectedResultCount
     *            (number of results for specific category/search)
     */
    @Step("validate pagination")
    public void validateStructure(int expectedResultCount)
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
}
