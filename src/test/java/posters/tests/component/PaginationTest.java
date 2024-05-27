package posters.tests.component;

import com.xceptance.neodymium.common.testdata.DataSet;
import com.xceptance.neodymium.common.testdata.SuppressDataSets;
import com.xceptance.neodymium.junit5.NeodymiumTest;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.pageobjects.components.PaginationTestData;

@Owner("Tim Brown")
@Severity(SeverityLevel.MINOR)
@Tag("smoke")
@SuppressDataSets
public class PaginationTest extends AbstractTest
{
    @NeodymiumTest
    @DataSet(1)
    public void testPagination()
    {
        // use test data
        final PaginationTestData paginationTestData = DataUtils.get(PaginationTestData.class);

        // go to homepage
        var homePage = OpenHomePageFlow.flow();

        // go to category page
        var categoryPage = homePage.header.topNav.clickCategory(Neodymium.localizedText(paginationTestData.getTopCategory()));
        categoryPage.pagination.validateStructure(paginationTestData.getExpectedResultCount());

        // go to homepage
        homePage = categoryPage.openHomePage();
    }
}