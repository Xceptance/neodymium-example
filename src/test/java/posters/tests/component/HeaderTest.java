package posters.tests.component;

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
import posters.tests.testdata.pageobjects.components.HeaderTestData;

@Owner("Tim Brown")
@Severity(SeverityLevel.MINOR)
@Tag("smoke")
@SuppressDataSets
public class HeaderTest extends AbstractTest
{           
    @Test
    @DataSet(1)
    public void testHeader()
    {  
        // use test data
        final HeaderTestData headerTestData = DataUtils.get(HeaderTestData.class);
        
        // go to homepage
        var homePage = OpenHomePageFlow.flow();
        homePage.header.validateStructure();

        // go to category page
        var categoryPage = homePage.header.topNav.clickCategory(headerTestData.getTopCategory());
        categoryPage.header.validateStructure();

        //go to product detail page, add product to cart
        var productDetailPage = categoryPage.clickProductByPosition(headerTestData.getResultPosition());
        productDetailPage.header.validateStructure();
        
        // go to cart page
        var cartPage = productDetailPage.header.miniCart.openCartPage();
        cartPage.header.validateStructure();
    }
}