package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.common.testdata.SuppressDataSets;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.pageobjects.components.SearchTestData;

@Owner("Tim Brown")
@Severity(SeverityLevel.MINOR)
@Tag("smoke")
@SuppressDataSets
public class SearchTest extends AbstractTest
{
    @DataItem
    private SearchTestData searchTestData;

    @Test
    @DataSet(1)
    public void testSearching()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to search result page
        var searchResultPage = homePage.header.search.searchResult(searchTestData.getSearchTerm());
        searchResultPage.validate(searchTestData.getSearchTerm(), searchTestData.getExpectedResultCount());

        // go to homepage
        homePage = searchResultPage.openHomePage();
    }

    @Test
    @DataSet(2)
    public void testSearchingWithoutResult()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to no hits page
        var noHitsPage = homePage.header.search.noHitsPageResult(searchTestData.getSearchTerm());
        noHitsPage.validateStructure();

        // go to homepage
        homePage = noHitsPage.openHomePage();
    }
}