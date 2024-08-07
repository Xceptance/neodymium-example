package posters.tests.component;

import org.junit.jupiter.api.Tag;

import com.xceptance.neodymium.common.testdata.DataItem;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.pageobjects.components.PaginationTestData;

@Owner("Tim Brown")
@Severity(SeverityLevel.MINOR)
@Tag("smoke")
public class PaginationTest extends AbstractTest
{
    @DataItem
    private PaginationTestData paginationTestData;
    
    @NeodymiumTest
    public void testPagination()
    {
        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to category page
        var categoryPage = homePage.header.topNav.clickCategory(Neodymium.localizedText(paginationTestData.getTopCategory()));
        categoryPage.pagination.validateStructure(paginationTestData.getExpectedResultCount());

        // go to homepage
        homePage = categoryPage.openHomePage();
    }
}