/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.Test;

import posters.flows.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.NoHitsPage;
import posters.pageobjects.pages.browsing.ProductdetailPage;

/**
 * @author pfotenhauer
 */
public class SearchTest extends BasicTest
{
    @Test
    public void testSearching()
    {
        // Goto homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Search
        final String searchTerm = "bear";
        final int searchTermExpectedCount = 3;
        CategoryPage categoryPage = homePage.search.categoryPageResult(searchTerm);
        categoryPage.validateStructure();
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);

        final String productName = categoryPage.getProductNameByPosition(1);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(1);
        productPage.validate(productName);
    }

    @Test
    public void testSearchingWithoutResult()
    {
        // Goto homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Search
        final String searchTerm = "Foobar";
        NoHitsPage noHitsPage = homePage.search.noResult(searchTerm);
        noHitsPage.validate();
    }

}
