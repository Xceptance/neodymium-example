package posters.tests.smoke.pageobjects.components;

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
public class PaginationTest extends AbstractTest
{       
    @Test
    @DataSet(1)
    public void testPagination()
    {  
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to category page
        var categoryPage = homePage.topNav.clickCategory(paginationTestData.getTopCategory());
        categoryPage.pagination.validateStructure(paginationTestData.getExpectedResultCount());
    }
}