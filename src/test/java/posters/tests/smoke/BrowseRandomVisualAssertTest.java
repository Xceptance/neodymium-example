/**
 * 
 */
package posters.tests.smoke;

import java.util.Random;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.util.DataUtils;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.tests.AbstractTest;

/**
 * @author schaefer
 */
@Owner("Tim Brown")
@Severity(SeverityLevel.NORMAL)
@Tag("smoke")
@Browser("Firefox_headless")
@Ignore
public class BrowseRandomVisualAssertTest extends AbstractTest
{
    private static int numberOfProductDetailPages;

    private static Random random;

    @Before
    public void setup()
    {
        try
        {
            random = new Random(DataUtils.asLong("seed"));
        }
        catch (Exception e)
        {
            random = new Random();
        }

        try
        {
            numberOfProductDetailPages = DataUtils.asInt("numberOfProductDetailPages");
        }
        catch (Exception e)
        {
            numberOfProductDetailPages = random.nextInt(10) + 1;
        }
    }

    @Test
    public void browseRandomCategoriesAndProducts()
    {
        CategoryPage categoryPage;
        ProductdetailPage productPage;

        for (int i = 1; i <= numberOfProductDetailPages; i++)
        {
            // Go to homepage
            LOGGER.info("Homepage");
            HomePage homePage = OpenHomePageFlow.flow();
            homePage.validateAndVisualAssert();

            // Go to category
            LOGGER.info("Category page");
            String categoryName = homePage.topNav.getRandomSubcategoryName(random);
            categoryPage = homePage.topNav.clickSubcategoryByName(categoryName);
            categoryPage.isExpectedPage();
            categoryPage.validateAndVisualAssert(categoryName);
            categoryPage = categoryPage.pagination.clickOnRandomSite(random);
            categoryPage.scrollToTop();
            categoryPage.validateAndVisualAssert(categoryName);

            // Go to any product detail page
            LOGGER.info("Product detail page");
            String productName = categoryPage.getRandomProductDetailName(random);
            productPage = categoryPage.clickProductByName(productName);
            productPage.validateAndVisualAssert(productName);
        }
    }
}