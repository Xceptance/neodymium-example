package posters.tests.newTests.smoke;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.util.Neodymium;

import posters.flows.OpenPageFlow;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.tests.AbstractTest;

public class CategoryTest extends AbstractTest
{
    private String mainCategoryName;

    private String subCategoryName;

    @Before
    public void dataSetup()
    {
        mainCategoryName = Neodymium.dataValue("mainCategoryName");
        subCategoryName = Neodymium.dataValue("subCategoryName");
    }

    @Test
    public void testCategory()
    {
        HomePage homePage = OpenPageFlow.openHomePage();
        CategoryPage categoryPage;
        if (subCategoryName != null)
        {
            categoryPage = homePage.topNav.clickSubCategoryByNames(mainCategoryName, subCategoryName);
        }
        else
        {
            categoryPage = homePage.topNav.clickCategory(mainCategoryName);
        }

        categoryPage.validateStructure();
        categoryPage.validateCategoryName(subCategoryName != null ? subCategoryName : mainCategoryName);

        int pagination = categoryPage.pagination.getNumberOfSites();
        categoryPage.pagination.clickOnRandomSite(new Random(pagination));
    }
}
