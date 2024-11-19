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
        $(".uk-section [src=\"imgs/logo.png\"]").should(exist);
        $(".uk-margin > .uk-grid .uk-width-3-4").should(exist);
        return this;
    }

    @Step("validate successful login of user '{firstName}' on home page")
    public void validateSuccessfulLogin(String firstName, String lastName)
    {
        // validate {firstName} in user menu
        header.userMenu.validateLoggedInUserName(firstName, lastName);
    }

    /// ========== validate content homepage ========== ///

    @Step("validate featured categories")
    public void validateFeaturedCategories()
    {

    }


    @Override
    @Step("validate structure home page")
    public void validateStructure()
    {
        super.validateStructure();

        // validate sale banner
        saleBanner.validateStructure();

        // validate intro
        $("#intro-text-homepage").shouldHave(exactText(Neodymium.localizedText("homePage.intro"))).shouldBe(visible);


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
