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
import posters.tests.AbstractTest;
import posters.tests.testdata.pageobjects.components.SearchTestData;

@Owner("Tim Brown")
@Severity(SeverityLevel.MINOR)
@Tag("smoke")
@SuppressDataSets
public class SearchTest extends AbstractTest
{       
    private SearchTestData searchTestData;

    @Before
    public void setup()
    {
        searchTestData = DataUtils.get(SearchTestData.class);
    }
    
    @Test
    @DataSet(1)
    public void testSearching()
    {  
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to category page
        var categoryPage = homePage.header.search.categoryPageResult(searchTestData.getSearchTerm());
        categoryPage.validate(searchTestData.getSearchTerm(), searchTestData.getExpectedResultCount());

        //go to product detail page
        final String productName = categoryPage.getProductNameByPosition(searchTestData.getResultPosition());
        var productDetailPage = categoryPage.clickProductByPosition(searchTestData.getResultPosition());
        productDetailPage.validate(productName);
        
        // go to homepage
        homePage = productDetailPage.openHomePage();
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
