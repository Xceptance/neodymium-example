package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;

import io.qameta.allure.Step;

public abstract class AbstractProductListingPage extends AbstractBrowsingPage
{
    @Override
    public void validateStructure()
    {
        isExpectedPage();
        
        // validate poster count in headline is not 0
        $("#total-product-count").shouldNotBe(exactText("0")).shouldBe(visible);

        // validate at least 1 poster is displayed
        $("#product-0").shouldBe(visible);
    }
    
    /**
     * @param expectedResultCount number of results for specific category/search
     */
    @Step("validate amount results '{expectedResultCount}'")
    public void validateResultCount(int expectedResultCount) 
    {
        $("#total-product-count").shouldHave(exactText(Integer.toString(expectedResultCount))).shouldBe(visible);
    }
    
    @Step("get a product name by position '{position}'")
    public String getProductNameByPosition(int position)
    {
        return $("#product-" + (position - 1) + " h5").text();
    }

    @Step("click on a product by product name '{productName}'")
    public ProductDetailPage clickProductByName(String productName) 
    {
        $$(".card-title").findBy(exactText(productName)).closest(".product-tile").find(".btn.btn-primary").click(ClickOptions.usingJavaScript());
        return new ProductDetailPage().isExpectedPage();
    }
    
    @Step("click on a product by position '{position}'")
    public ProductDetailPage clickProductByPosition(int position)
    {
        $("#product-" + (position - 1) + " .btn.btn-primary").click(ClickOptions.usingJavaScript());
        return new ProductDetailPage().isExpectedPage();
    }
    
    @Step("open homepage from category page")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}
