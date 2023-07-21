package posters.pageobjects.pages.browsing;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Step;
import posters.dataobjects.User;

public class HomePage extends AbstractBrowsingPage
{
    @Override
    @Step("ensure this is a home page")
    public HomePage isExpectedPage()
    {
        super.isExpectedPage();
        $("#titleSlide").should(exist);
        return this;
    }

    @Override
    @Step("validate structure home page")
    public void validateStructure()
    {
        super.validateStructure();

        // Verifies the company Logo and name are visible.
        $("#colorlib-logo").shouldBe(visible);

        // Verifies the Navigation bar is visible
        $("#categoryMenu .navi").shouldBe(visible);
        // Asserts there's categories in the nav bar.
        $$("#categoryMenu .has-dropdown").shouldHave(sizeGreaterThan(0));

        // Asserts the first headline is there.
        // $("#titleIndex").shouldBe(matchText("[A-Z].{3,}"));
        
        // Asserts the animated poster rotation is there.
        $(".flex-control-nav").shouldBe(visible);

        // Verifies the 'Intro Quote' section is there.
        $(".colorlib-intro .container h2").shouldBe(matchText("[A-Z].{3,}"));
        
        // Verifies the "Hot products" section is there.
        $(".colorlib-product .colorlib-heading h2").shouldBe(matchText("[A-Z].{3,}"));
        
        // Asserts there's a list of items under "Hot Products".
        $(".colorlib-product #productList").shouldBe(visible);
        // Asserts there's at least 1 item in the list.
        $$(".container .row-pb-md .product-entry").shouldHave(sizeGreaterThan(0));
    }

    @Step("validate successful order on home page")
    public void validateSuccessfulOrder()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("HomePage.validation.successfulOrder"));
        // Verify that the mini cart is empty again
        miniCart.validateTotalCount(0);
        miniCart.validateSubtotal("$0.00");
    }

    /**
     * @param firstName
     *            The name should be shown in the mini User Menu
     */
    @Step("validate successful login on home page")
    public void validateSuccessfulLogin(String firstName)
    {
        // Verify that you are logged in
        successMessage.validateSuccessMessage(Neodymium.localizedText("HomePage.validation.successfulLogin"));
        // Verify that the user menu shows your first name
        userMenu.validateLoggedInName(firstName);

    }

    /**
     * @param user
     */
    @Step("validate successful user login on home page")
    public void validateSuccessfulLogin(User user)
    {
        validateSuccessfulLogin(user.getFirstName());
    }

    @Step("validate successful account deletion on home page")
    public void validateSuccessfulDeletedAccount()
    {
        successMessage.validateSuccessMessage(Neodymium.localizedText("HomePage.validation.successfulAccountDeletion"));
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
