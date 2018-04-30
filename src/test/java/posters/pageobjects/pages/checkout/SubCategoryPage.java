package posters.pageobjects.pages.checkout;

import static com.codeborne.selenide.Selenide.$;

import java.util.Random;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import io.qameta.allure.Step;

/**
 * @author schaefer
 */
public class SubCategoryPage extends AbstractCheckoutPage
{
    private static Random random;

    private int categoryPositionX;

    private int categoryPositionY;

    private int productPositionX;

    private int productPositionY;

    private static SelenideElement pagination = $("#pagination-bottom");

    public SubCategoryPage(long seed)
    {
        random = new Random(seed);
    }

    public int getCategoryPositionX()
    {
        return categoryPositionX;
    }

    public int getCategoryPositionY()
    {
        return categoryPositionY;
    }

    public int getProductPositionX()
    {
        return productPositionX;
    }

    public int getProductPositionY()
    {
        return productPositionY;
    }

    @Step("choose randomly an int for a (sub)section")
    public void choseCategory()
    {
        // compute random horizontal category position (for posters: random value from 1 to 4)
        // 1: World of nature
        // 2: Dining
        // 3: Transportation
        // 4: Panoramas
        categoryPositionX = random.nextInt(3) + 1;

        // compute random vertical category position (for posters: random value from 1 to 4)
        // if (categoryPositionX == 4) == true then categoryPositionY has range from 1 to 4
        // otherwise from 1 to 3, because any category has 3 subcategories instead of category 4,
        // which has 4
        if (categoryPositionX == 4)
        {
            categoryPositionY = random.nextInt(3) + 1;
        }
        else
        {
            categoryPositionY = random.nextInt(2) + 1;
        }

    }

    @Step("go to a randomly choosen overview page using the pagination")
    public void goToOverviewPage()
    {
        String stringOfPaginationChildren = pagination.getText();
        stringOfPaginationChildren = stringOfPaginationChildren.replace(">", "");
        stringOfPaginationChildren = stringOfPaginationChildren.replace("\n", "");

        int amountOfProductOverviewPages = stringOfPaginationChildren.length();
        int intChosenElementValue = random.nextInt(amountOfProductOverviewPages) + 1;

        String stringChosenElementValue = String.valueOf(intChosenElementValue);

        SelenideElement chosenSelenideElement = pagination.$(Selectors.withText(stringChosenElementValue));
        chosenSelenideElement.scrollTo();
        chosenSelenideElement.click();
    }

    @Step("choose randomly a product in an overview page")
    public void choseDetailPage()
    {
        productPositionX = random.nextInt(2) + 1;
        productPositionY = random.nextInt(1) + 1;
    }
}
