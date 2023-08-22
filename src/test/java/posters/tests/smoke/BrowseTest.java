package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;
import posters.tests.testdata.processes.BrowseTestData;

@Owner("Tim Brown")
@Severity(SeverityLevel.NORMAL)
@Tag("smoke")
public class BrowseTest extends AbstractTest
{
    @Test
    @DataSet(1)
    public void testBrowsing()
    {
        // use test data
        final BrowseTestData browseTestData = DataUtils.get(BrowseTestData.class);
        
        // go to homepage
        var homePage = OpenHomePageFlow.flow();
        homePage.validateStructure();

        // go to category page and validate
        var categoryPage = homePage.header.topNav.clickCategory(Neodymium.localizedText(browseTestData.getTopCategory1()));
        categoryPage.validate(Neodymium.localizedText(browseTestData.getTopCategory1()), browseTestData.getExpectedResultCount1());
        
        // go to product page and validate
        final String productName = categoryPage.getProductNameByPosition(browseTestData.getResultPosition1());
        var productDetailPage = categoryPage.clickProductByPosition(browseTestData.getResultPosition1());
        productDetailPage.validate(productName);

        // go to category page and validate
        categoryPage = productDetailPage.header.topNav.clickCategory(Neodymium.localizedText(browseTestData.getTopCategory2()));
        categoryPage.validate(Neodymium.localizedText(browseTestData.getTopCategory2()), browseTestData.getExpectedResultCount2());
       
        // go to product page and validate
        final String productName2 = categoryPage.getProductNameByPosition(browseTestData.getResultPosition2());
        productDetailPage = categoryPage.clickProductByPosition(browseTestData.getResultPosition2());
        productDetailPage.validate(productName2);
        
        // go to category page and validate
        categoryPage = productDetailPage.header.topNav.clickCategory(Neodymium.localizedText(browseTestData.getTopCategory3()));
        categoryPage.validate(Neodymium.localizedText(browseTestData.getTopCategory3()), browseTestData.getExpectedResultCount3());
       
        // go to product page and validate
        final String productName3 = categoryPage.getProductNameByPosition(browseTestData.getResultPosition3());
        productDetailPage = categoryPage.clickProductByPosition(browseTestData.getResultPosition3());
        productDetailPage.validate(productName3);
        
        // go to category page and validate
        categoryPage = productDetailPage.header.topNav.clickCategory(Neodymium.localizedText(browseTestData.getTopCategory4()));
        categoryPage.validate(Neodymium.localizedText(browseTestData.getTopCategory4()), browseTestData.getExpectedResultCount4());
       
        // go to product page and validate
        final String productName4 = categoryPage.getProductNameByPosition(browseTestData.getResultPosition4());
        productDetailPage = categoryPage.clickProductByPosition(browseTestData.getResultPosition4());
        productDetailPage.validate(productName4);
    }
}