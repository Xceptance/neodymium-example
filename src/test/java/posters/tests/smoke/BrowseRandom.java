/**
 * 
 */
package posters.tests.smoke;

import static com.codeborne.selenide.Selenide.$;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
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

    private static long seed;

    private static Random random;

    @Before
    public void setup()
    {
        numberOfProductDetailPages = DataUtils.asInt("numberOfProductDetailPages");
        seed = DataUtils.asLong("seed");
    }

    @Test
    public void searchProductDetailPage()
    {
        random = new Random(seed);

        for (int i = 1; i <= numberOfProductDetailPages; i++)
        {
            // compute random horizontal category position (for posters: random value from 1 to 4)
            // 1: World of nature
            // 2: Dining
            // 3: Transportation
            // 4: Panoramas
            int categoryPositionX = random.nextInt(3) + 1;

            // compute random vertical category position (for posters: random value from 1 to 4)
            // if (categoryPositionX == 4) == true then categoryPositionY has range from 1 to 4
            // otherwise from 1 to 3, because any category has 3 subcategories instead of category 4,
            // which has 4
            int categoryPositionY;
            if (categoryPositionX == 4)
            {
                categoryPositionY = random.nextInt(3) + 1;
            }
            else
            {
                categoryPositionY = random.nextInt(2) + 1;
            }

            // Goto homepage
            HomePage homePage = OpenHomePageFlow.flow();
            homePage.validate();

            // Goto subcategory
            final String categoryName = homePage.topNav.getSubCategoryNameByPosition(categoryPositionX, categoryPositionY);
            CategoryPage categoryPage = homePage.topNav.clickSubCategoryByPosition(categoryPositionX, categoryPositionY);
            categoryPage.validate(categoryName);

            // Goto any product overview page
            SelenideElement pagination = $("#pagination-bottom");
            String stringOfPaginationChildren = pagination.getText();
            stringOfPaginationChildren = stringOfPaginationChildren.replace(">", "");
            stringOfPaginationChildren = stringOfPaginationChildren.replace("\n", "");
            int amountOfProductOverviewPages = stringOfPaginationChildren.length();
            int intChosenElementValue = random.nextInt(amountOfProductOverviewPages) + 1;
            String stringChosenElementValue = String.valueOf(intChosenElementValue);
            SelenideElement chosenSelenideElement = pagination.$(Selectors.withText(stringChosenElementValue));
            chosenSelenideElement.scrollTo();
            chosenSelenideElement.click();

            // Goto product detail page
            int productPositionX = random.nextInt(2) + 1;
            int productPositionY = random.nextInt(1) + 1;
            final String productName = categoryPage.getProductNameByPosition(productPositionX, productPositionY);
            ProductdetailPage productPage = categoryPage.clickProductByPosition(productPositionX, productPositionY);
            productPage.validate(productName);
        }

    }
}
