/**
 * 
 */
package posters.tests.smoke;

import org.junit.Test;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.NoHitsPage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Owner("Tim Brown")
@Severity(SeverityLevel.MINOR)
@Tag("smoke")
public class SearchTest extends AbstractTest
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
