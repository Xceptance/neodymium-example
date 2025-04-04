package posters.tests.smoke;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Tag;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.pageobjects.components.SearchTestData;

@Owner("Tim Brown")
@Severity(SeverityLevel.MINOR)
@Tag("smoke")
public class SearchTest extends AbstractTest
{
    @DataItem
    private SearchTestData searchTestData;

    @NeodymiumTest
    @DataSet(id = "search with expected result US")
    @DataSet(id = "search with expected result DE")
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

    @NeodymiumTest
    @DataSet(id = "search with no result US")
    @DataSet(id = "search with no result DE")
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
