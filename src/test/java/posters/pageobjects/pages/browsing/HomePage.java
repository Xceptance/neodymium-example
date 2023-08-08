package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.tests.testdata.dataobjects.User;

public class HomePage extends AbstractBrowsingPage
{
    private ElementsCollection slideNavigation = $$(".flex-control-nav li");
    
    private ElementsCollection slideHeadline = $$("#titleSlide h1");
    
    private ElementsCollection slideButton = $$("#titleSlide .btn-primary");
    
    private ElementsCollection featuredCategories = $$(".pName");
    
    private ElementsCollection featuredContent = $$("#productList h2");
    
    @Override
    @Step("ensure this is a home page")
    public HomePage isExpectedPage()
    {
        super.isExpectedPage();
        $("#titleSlide").should(exist);
        return this;
    }

    /// ----- validate homepage ----- ///
    
    @Step("validate poster slide")
    public void validatePosterSlide()
    {
        validatePosterSlide("4", "HomePage.slider.headline.4");
        validatePosterSlide("3", "HomePage.slider.headline.3");
        validatePosterSlide("2", "HomePage.slider.headline.2");
        validatePosterSlide("1", "HomePage.slider.headline.1");
    }
    
    @Step("validate slider headline")
    public void validatePosterSlide(String position, String headline)
    {
        slideNavigation.findBy(exactText(position)).click();
        slideHeadline.findBy(exactText(Neodymium.localizedText(headline))).shouldBe(visible);
        slideButton.findBy(exactText(Neodymium.localizedText("HomePage.slider.button"))).shouldBe(visible);
    }
    
    @Step("validate featured categories")
    public void validateFeaturedCategories()
    {
        $$(".featured-img").shouldHaveSize(4);
        featuredCategories.findBy(exactText(Neodymium.localizedText("header.topNavigation.1.title"))).shouldBe(visible);
        featuredCategories.findBy(exactText(Neodymium.localizedText("header.topNavigation.2.title"))).shouldBe(visible);
        featuredCategories.findBy(exactText(Neodymium.localizedText("header.topNavigation.3.title"))).shouldBe(visible);
        featuredCategories.findBy(exactText(Neodymium.localizedText("header.topNavigation.4.title"))).shouldBe(visible);
    }
    
    @Step("validate featured content")
    public void validateFeaturedContent()
    {
        $$(".colorlib-product .container .row h2").findBy(exactText(Neodymium.localizedText("HomePage.hotProducts"))).shouldBe(visible);
        $$(".prod-img").shouldHaveSize(12);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.1"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.2"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.3"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.4"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.5"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.6"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.7"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.8"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.9"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.10"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.11"))).shouldBe(visible);
        featuredContent.findBy(exactText(Neodymium.localizedText("HomePage.featuredContent.12"))).shouldBe(visible);
    }
    
    @Override
    @Step("validate structure home page")
    public void validateStructure()
    {
        super.validateStructure();

        // validate poster slide
        validatePosterSlide();
        
        // validate intro
        $("#intro").shouldHave(exactText(Neodymium.localizedText("HomePage.intro"))).shouldBe(visible);
        
        // validate featured categories
        validateFeaturedCategories();
        
        // validate featured content
        validateFeaturedContent();
        
        // validate shop all products button
        $("a.btn-primary-shop").shouldHave(exactText(Neodymium.localizedText("HomePage.shopAllProducts"))).shouldBe(visible);
    }
    
    @Step("validate successful login on home page")
    public void validateSuccessfulLogin(String firstName)
    {
        // validate success message
        successMessage.validateSuccessMessage(Neodymium.localizedText("HomePage.validation.successfulLogin"));
        
        // validate {firstName} in user menu
        userMenu.validateLoggedInName(firstName);
    }
    
    @Step("validate successful account deletion on home page")
    public void validateSuccessfulDeletedAccount()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("HomePage.validation.successfulAccountDeletion"));
    }
    
    // --------------------------------------------------
    
    @Step("validate successful order on home page")
    public void validateSuccessfulOrder()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("HomePage.validation.successfulOrder"));
        // Verify that the mini cart is empty again
        miniCart.validateTotalCount(0);
        miniCart.validateSubtotal("$0.00");
    }

    public void validateAndVisualAssert()
    {
        validateStructureAndVisual();
        footer.validateStructure();
    }

    public ProductDetailPage clickOnPresentedProduct(String productName)
    {
        $$("#productList .thumbnail .pName").filter(exactText(productName)).shouldHaveSize(1).first().click();
        return new ProductDetailPage().isExpectedPage();
    }
}
