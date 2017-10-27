/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.OpenHomePageFlow;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.PCategory;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.PHome;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.PProduct;
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
        // TODO put in place of first usage
        // Page types to use
        PHome homePage;
        PCategory categoryPage;
        PProduct productPage;

        // Goto homepage
        homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Search
        final String searchTerm = "bear";
        final int searchTermExpectedCount = 3;
        categoryPage = homePage.search().categoryPageResult(searchTerm);
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);

        final String productName = categoryPage.getProductNameByIndex(0);
        productPage = categoryPage.clickProductByIndex(0);
        productPage.validate(productName);
    }
}
