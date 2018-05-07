/**
 * 
 */
package posters.tests.smoke;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

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
public class BrowseRandom extends AbstractTest
{
    private static int numberOfProductDetailPages;

    private static Random random;

    @Before
    public void setup()
    {
        numberOfProductDetailPages = DataUtils.asInt("numberOfProductDetailPages");
        random = new Random(DataUtils.asLong("seed"));
    }

    @Test
    public void browseRandomCategoriesAndProducts()
    {
        CategoryPage categoryPage;
        ProductdetailPage productPage;

        for (int i = 1; i <= numberOfProductDetailPages; i++)
        {
            // Goto homepage
            HomePage homePage = OpenHomePageFlow.flow();
            homePage.validate();

            // Goto category
            categoryPage = homePage.topNav.getRandomSubcategoryAndValidateAndVisualAssert(random);
            categoryPage.pagination.clickOnRandomSite(random);

            // Goto any product detail page
            String productName = categoryPage.getRandomProductDetailPage(random);
            productPage = categoryPage.clickProductByName(productName);
            productPage.validateAndVisualAssert(productName);
        }
    }
}