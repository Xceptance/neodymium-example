/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;

import posters.neodymium.flow.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.CategoryPage;
import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.browsing.ProductdetailPage;

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
