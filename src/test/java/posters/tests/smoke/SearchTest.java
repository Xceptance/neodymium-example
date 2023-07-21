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
import posters.pageobjects.pages.browsing.AbstractBrowsingPage;
import posters.tests.AbstractTest;

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
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to category page
        var categoryPage = homePage.search.categoryPageResult(searchTerm);
        categoryPage.validateStructure();
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);
        validateHeaderAndFooter(categoryPage);

        //go to product detail page
        final String productName = categoryPage.getProductNameByPosition(position);
        var productDetailPage = categoryPage.clickProductByPosition(position);
        productDetailPage.validate(productName);
        validateHeaderAndFooter(productDetailPage);
    }

    @Test
    @DataSet(2)
    public void testSearchingWithoutResult()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to no hits page
        var noHitsPage = homePage.search.noHitsPageResult(searchTerm);
        noHitsPage.validateStructure();
        validateHeaderAndFooter(noHitsPage);
    }
    
    public void validateHeaderAndFooter(AbstractBrowsingPage page) 
    {
        page.header.validateStructure();
        page.footer.validateStructure();
    }
}
