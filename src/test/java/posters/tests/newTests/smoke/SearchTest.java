package posters.tests.newTests.smoke;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.Neodymium;

import posters.flows.OpenPageFlow;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.NoHitsPage;
import posters.tests.AbstractTest;

public class SearchTest extends AbstractTest
{
    String searchTerm;

    @Before
    public void dataSetup()
    {
        searchTerm = Neodymium.dataValue("searchTerm");
    }

    @DataSet(id = "search test with results")
    @Test
    public void testSearch()
    {
        HomePage homePage = OpenPageFlow.openHomePage();
        CategoryPage searchResults = homePage.search.categoryPageResult(searchTerm);
        searchResults.validateStructure();
        searchResults.validateSearchHits(searchTerm);

    }

    @DataSet(id = "search test with no results")
    @Test
    public void testSearchWithNoResults()
    {
        HomePage homePage = OpenPageFlow.openHomePage();

        NoHitsPage noHitsPage = homePage.search.noResult(searchTerm);
        noHitsPage.validate();
    }
}
