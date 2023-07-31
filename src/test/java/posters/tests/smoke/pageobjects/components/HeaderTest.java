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
public class HeaderTest extends AbstractTest
{       
    @Test
    @DataSet(1)
    public void testSearching()
    {  
        // go to homepage
        var homePage = OpenHomePageFlow.flow();
        homePage.header.validateStructure();

        // go to category page
        var categoryPage = homePage.topNav.clickCategory(headerTestData.getTopCategory());
        categoryPage.header.validateStructure();

        //go to product detail page, add product to cart
        var productDetailPage = categoryPage.clickProductByPosition(headerTestData.getResultPosition());
        productDetailPage.header.validateStructure();
        
        // go to cart page
        var cartPage = productDetailPage.miniCart.openCartPage();
        cartPage.header.validateStructure();
    }
}