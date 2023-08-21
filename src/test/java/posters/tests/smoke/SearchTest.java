package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.module.statement.testdata.SuppressDataSets;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;

@Owner("Tim Brown")
@Severity(SeverityLevel.MINOR)
@Tag("smoke")
@SuppressDataSets
public class SearchTest extends AbstractTest
{       
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
    }
}
