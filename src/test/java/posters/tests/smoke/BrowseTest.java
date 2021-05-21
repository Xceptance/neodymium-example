package posters.tests.smoke;

import org.junit.Test;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Owner("Tim Brown")
@Severity(SeverityLevel.NORMAL)
@Tag("smoke")
public class BrowseTest extends AbstractTest
{
    @Test
    public void testBrowsing()
    {
        // Go to homepage
        homePage.validate();

        // Go to category
        final String categoryName = homePage.topNav.getSubCategoryNameByPosition(1, 1);
        var categoryPage = homePage.topNav.clickSubCategoryByPosition(1, 1);
        categoryPage.validate(categoryName);

        // Go to product page
        final String productName = categoryPage.getProductNameByPosition(1, 1);
        var productDetailPage = categoryPage.clickProductByPosition(1, 1);
        productDetailPage.validate(productName);

        // Go to category
        final String categoryName2 = productDetailPage.topNav.getSubCategoryNameByPosition(2, 2);
        categoryPage = productDetailPage.topNav.clickSubCategoryByPosition(2, 2);
        categoryPage.validate(categoryName2);

        // Go to product page
        final String productName2 = categoryPage.getProductNameByPosition(2, 2);
        productDetailPage = categoryPage.clickProductByPosition(2, 2);
        productDetailPage.validate(productName2);

        // Go to category
        final String categoryName3 = productDetailPage.topNav.getSubCategoryNameByPosition(2, 3);
        categoryPage = productDetailPage.topNav.clickSubCategoryByPosition(2, 3);
        categoryPage.validate(categoryName3);

        // Go to product page
        final String productName3 = categoryPage.getProductNameByPosition(2, 3);
        productDetailPage = categoryPage.clickProductByPosition(2, 3);
        productDetailPage.validate(productName3);
    }
}
