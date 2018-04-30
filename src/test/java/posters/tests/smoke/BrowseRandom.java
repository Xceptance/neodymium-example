/**
 * 
 */
package posters.tests.smoke;

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
import posters.pageobjects.pages.checkout.SubCategoryPage;
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

    private SubCategoryPage subCategoryPage;

    @Before
    public void setup()
    {
        numberOfProductDetailPages = DataUtils.asInt("numberOfProductDetailPages");
        subCategoryPage = new SubCategoryPage(DataUtils.asLong("seed"));
    }

    @Test
    public void searchProductDetailPage()
    {
        for (int i = 1; i <= numberOfProductDetailPages; i++)
        {
            // Goto homepage
            HomePage homePage = OpenHomePageFlow.flow();
            homePage.validate();

            // Goto subcategory
            subCategoryPage.choseCategory();
            final String categoryName = homePage.topNav.getSubCategoryNameByPosition(subCategoryPage.getCategoryPositionX(),
                                                                                     subCategoryPage.getCategoryPositionY());
            CategoryPage categoryPage = homePage.topNav.clickSubCategoryByPosition(subCategoryPage.getCategoryPositionX(),
                                                                                   subCategoryPage.getCategoryPositionY());
            categoryPage.validate(categoryName);

            // Goto any product overview page
            subCategoryPage.goToOverviewPage();

            // Goto product detail page
            subCategoryPage.choseDetailPage();
            final String productName = categoryPage.getProductNameByPosition(subCategoryPage.getProductPositionX(),
                                                                             subCategoryPage.getProductPositionY());
            ProductdetailPage productPage = categoryPage.clickProductByPosition(subCategoryPage.getProductPositionX(),
                                                                                subCategoryPage.getProductPositionY());
            productPage.validate(productName);
        }

    }
}