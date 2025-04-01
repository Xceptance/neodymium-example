package posters.pageobjects.components;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Pagination extends AbstractComponent<Pagination>
{
    private SelenideElement pagination = $("#pagination-bottom");

    private SelenideElement activePage = $(".page-item .active");

    private SelenideElement firstPage = $("#pagfirst");

    private SelenideElement nextPage = $("#pagnext");

    private SelenideElement previousPage = $("#pagprev");

    private SelenideElement lastPage = $("#paglast");

    private ElementsCollection paginationComponents = $$(".page-item .page-link");

    @Override
    @Step("check availability of pagination")
    public Pagination assertComponentAvailable()
    {
        return super.assertComponentAvailable();
    }

    @Override
    @Step("check availability of pagination")
    public boolean isComponentAvailable()
    {
        SelenideAddons.optionalWaitUntilCondition(pagination, exist);
        return pagination.exists();
    }

    /// ========== pagination navigation ========== ///

    @Step("click on page number '{pageNumber}'")
    public void goToPage(int pageNumber)
    {
        paginationComponents.findBy(exactText(Integer.toString(pageNumber))).click(ClickOptions.usingJavaScript());
    }

    @Step("click on go to first page")
    public void goToFirstPage()
    {
        firstPage.click(ClickOptions.usingJavaScript());
    }

    @Step("click on go to first page")
    public void goToPreviousPage()
    {
        previousPage.click(ClickOptions.usingJavaScript());
    }

    @Step("click on go to last page")
    public void goToNextPage()
    {
        nextPage.click(ClickOptions.usingJavaScript());
    }

    @Step("click on go to last page")
    public void goToLastPage()
    {
        lastPage.click(ClickOptions.usingJavaScript());
    }

    /// ========== validate pagination ========== ///

    @Step("validate the navigation")
    private void validateNavigation()
    {
        firstPage.shouldBe(visible);
        previousPage.shouldBe(visible);
        nextPage.shouldBe(visible);
        lastPage.shouldBe(visible);

        $(".icon-first").shouldBe(visible);
        $(".icon-backward2").shouldBe(visible);
        $(".icon-forward3").shouldBe(visible);
        $(".icon-last").shouldBe(visible);
    }

    @Step("validate if left pagination navigation should be disabled or not")
    private void validateAvailabilityLeftNavigation(Boolean available)
    {
        if (!available)
        {
            $(".disabled #pagfirst").should(exist);
            $(".disabled #pagprev").should(exist);
        }
        else
        {
            $(".disabled #pagfirst").shouldNot(exist);
            $(".disabled #pagprev").shouldNot(exist);
        }
    }

    @Step("validate if right pagination navigation should be disabled or not")
    private void validateAvailabilityLRightNavigation(Boolean available)
    {
        if (!available)
        {
            $(".disabled #pagnext").should(exist);
            $(".disabled #paglast").should(exist);
        }
        else
        {
            $(".disabled #pagnext").shouldNot(exist);
            $(".disabled #paglast").shouldNot(exist);
        }
    }

    /**
     * To get {numberOfPages} it divides {expectedResultCount} by 8 because only 8 results can be displayed per page. Then it loops through the page numbers and
     * validates its existence and visibility.
     *
     * @param expectedResultCount
     *     number of results for specific category/search
     */
    @Step("validate visibility of {expectedResultCount} in pagination")
    private void validateElementNumbers(int expectedResultCount)
    {
        int numberOfPages = expectedResultCount / 8;
        for (int i = 1; i <= numberOfPages; i++)
        {
            paginationComponents.findBy(exactText(Integer.toString(i))).shouldBe(visible);
        }
    }

    /**
     * If there is more than 1 result page it validates the pagination. It loops through the page numbers and validates the existence and visibility of the
     * numbers themselves and the icon navigation
     *
     * @param expectedResultCount
     *     number of results for specific category/search
     */
    @Step("validate pagination")
    public void validateStructure(int expectedResultCount)
    {
        int numberOfPages = (expectedResultCount % 8 == 0) ? (expectedResultCount / 8) : (expectedResultCount / 8 + 1);

        if (numberOfPages <= 1)
        {
            return;
        }

        pagination.shouldBe(visible);

        // validate click on pagination numbers works properly
        for (int i = 1; i <= numberOfPages; i++)
        {
            goToPage(i);
            activePage.shouldHave(exactText(Integer.toString(i)));
            validateElementNumbers(expectedResultCount);
            validateNavigation();

            validateAvailabilityLeftNavigation(i > 1);
            validateAvailabilityLRightNavigation(i < numberOfPages);

            if (i < numberOfPages)
            {
                goToNextPage();
                activePage.shouldHave(exactText(Integer.toString(i + 1)));
                goToPreviousPage();
                activePage.shouldHave(exactText(Integer.toString(i)));
            }
            else
            {
                goToPreviousPage();
                activePage.shouldHave(exactText(Integer.toString(i - 1)));
                goToNextPage();
                activePage.shouldHave(exactText(Integer.toString(i)));
            }
        }

        goToFirstPage();
        activePage.shouldHave(exactText(Integer.toString(1)));

        goToLastPage();
        activePage.shouldHave(exactText(Integer.toString(numberOfPages)));
    }
}
