package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.pageobjects.components.SaleBanner;

public class HomePage extends AbstractBrowsingPage
{
    private ElementsCollection slideNavigation = $$("#carousel-btn");

    private ElementsCollection slideHeadline = $$(".carousel-content-product h1");

    private ElementsCollection slideButton = $$(".carousel-content-product .btn-primary");

    private ElementsCollection featuredCategories = $$(".category-tile-title");

    public SaleBanner saleBanner = new SaleBanner();

    @Override
    @Step("ensure this is a home page")
    public HomePage isExpectedPage()
    {
        super.isExpectedPage();
        $("#intro-text-homepage").should(exist);
        return this;
    }

    /// ========== validate content homepage ========== ///

    @Step("validate poster slide")
    public void validatePosterSlide()
    {
        for (int i = 1; i <= 4; i++)
        {
            slideNavigation.findBy(attribute("aria-label", "Slide " + i)).click(ClickOptions.usingJavaScript());
            slideHeadline.findBy(exactText(Neodymium.localizedText("homePage.slider." + i))).shouldBe(visible);
            slideButton.findBy(exactText(Neodymium.localizedText("button.buyHere"))).shouldBe(visible);
        }
    }

    @Step("validate featured categories")
    public void validateFeaturedCategories()
    {
        $$(".category-tile-image").shouldHave(size(4));

        for (int i = 1; i <= 4; i++)
        {
            featuredCategories.findBy(exactText(Neodymium.localizedText("header.topNavigation." + i + ".title"))).shouldBe(visible);
        }
    }

    @Step("validate featured products")
    public void validateFeaturedProducts()
    {
        $(".product-display-heading h2").shouldHave(exactText(Neodymium.localizedText("homePage.featuredProducts.headline"))).shouldBe(visible);
        $$(".card-img-top").shouldHave(size(12));

        for (int i = 1; i <= 12; i++)
        {
            SelenideElement productCard = $(".card.product-tile:nth-child(" + i + ")");

            productCard.find(".card-title").shouldHave(exactText(Neodymium.localizedText("homePage.featuredProducts." + i + ".title"))).shouldBe(visible);
            productCard.find(".product-tile-text").shouldHave(exactText(Neodymium.localizedText("homePage.featuredProducts." + i + ".description")))
                       .shouldBe(visible);
            productCard.find(".product-tile-price").shouldBe(visible);
            productCard.find(".btn").shouldHave(exactText(Neodymium.localizedText("button.buyHere"))).shouldBe(visible);
        }
    }

    @Override
    @Step("validate structure home page")
    public void validateStructure()
    {
        super.validateStructure();

        // validate sale banner
        saleBanner.validateStructure();

        // validate poster slide
        validatePosterSlide();

        // validate intro
        $("#intro-text-homepage").shouldHave(exactText(Neodymium.localizedText("homePage.intro"))).shouldBe(visible);

        // validate featured categories
        validateFeaturedCategories();

        // validate featured products
        validateFeaturedProducts();

        // validate shop all products button
        $(".btn-shop-all").shouldHave(exactText(Neodymium.localizedText("button.shopAllProducts"))).shouldBe(visible);
    }

    @Step("validate successful account deletion on home page")
    public void validateSuccessfulDeletedAccount()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("successMessage.successfulAccountDeletion"));
    }

    /// ========== homepage navigation ========== ///

    @Step("reload homepage")
    public HomePage openHomePage()
    {
        $("#header-brand").click(ClickOptions.usingJavaScript());
        return new HomePage().isExpectedPage();
    }
}
