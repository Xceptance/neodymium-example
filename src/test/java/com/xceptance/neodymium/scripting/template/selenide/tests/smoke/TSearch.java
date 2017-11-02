/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.OpenHomePageFlow;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.CategoryPage;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.ProductdetailPage;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"
})
public class TSearch extends BasicTest
{
    @Test
    public void test()
    {
        // Goto homepage
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Search
        final String searchTerm = "bear";
        final int searchTermExpectedCount = 3;
        CategoryPage categoryPage = homePage.search().categoryPageResult(searchTerm);
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);

        final String productName = categoryPage.getProductNameByPosition(1);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(1);
        productPage.validate(productName);
    }
}
