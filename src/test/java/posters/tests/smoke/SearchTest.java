/**
 * 
 */
package posters.tests.smoke;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.module.statement.testdata.SuppressDataSets;
import com.xceptance.neodymium.util.DataUtils;

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
@SuppressDataSets
public class SearchTest extends AbstractTest
{
    private String searchTerm;

    private int position;

    private int searchTermExpectedCount;

    @Before
    public void setup()
    {
        searchTerm = DataUtils.asString("searchTerm");
        position = DataUtils.asInt("position", 0);
        searchTermExpectedCount = DataUtils.asInt("searchTermExpectedCount", 0);
    }

    @Test
    @DataSet(1)
    public void testSearching()
    {
        // Go to homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Search
        CategoryPage categoryPage = homePage.search.categoryPageResult(searchTerm);
        categoryPage.validateStructure();
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);

        final String productName = categoryPage.getProductNameByPosition(position);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(position);
        productPage.validate(productName);
    }

    @Test
    @DataSet(2)
    public void testSearchingWithoutResult()
    {
        // Go to homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Search
        NoHitsPage noHitsPage = homePage.search.noResult(searchTerm);
        noHitsPage.validate();
    }
}
