package posters.pageObjects.pages.browsing;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.xceptance.neodymium.util.Context;

import posters.dataObjects.User;

public class HomePage extends AbstractBrowsingPage
{
    @Override
    public void isExpectedPage()
    {
        $("#titleIndex").should(exist);
    }

    public void validateStructure()
    {
        super.validateStructure();

        // Verifies the company Logo and name are visible.
        $("a#brand").shouldBe(visible);

        // Verifies the Navigation bar is visible
        $("#categoryMenu .nav").shouldBe(visible);
        // Asserts there's categories in the nav bar.
        $$("#categoryMenu .nav > li.dropdown.header-menu-item").shouldHave(sizeGreaterThan(0));

        // Asserts the first headline is there.
        $("#titleIndex.h2").shouldBe(matchText("[A-Z].{3,}"));
        // Asserts the animated poster rotation is there.
        $("#pShopCarousel").shouldBe(visible);

        // Verifies the "Hot products" section is there.
        $("#main .margin_top20 .h2").shouldBe(matchText("[A-Z].{3,}"));
        // Asserts there's a list of items under "Hot Products".
        $("#productList").shouldBe(visible);
        // Asserts there's at least 1 item in the list.
        $$("#productList > li").shouldHave(sizeGreaterThan(0));
    }

    public void validate()
    {
        validateStructure();
        footer.validate();
    }

    public void validateSuccessfulOrder()
    {
        successMessage.validateSuccessMessage(Context.localizedText("HomePage.validation.successfulOrder"));
        // Verify that the mini cart is empty again
        miniCart.validateTotalCount(0);
        miniCart.validateSubtotal("$0.00");
    }

    /**
     * @param firstName
     *            The name should be shown in the mini User Menu
     */
    public void validateSuccessfulLogin(String firstName)
    {
        // Verify that you are logged in
        successMessage.validateSuccessMessage(Context.localizedText("HomePage.validation.successfulLogin"));
        // Verify that the user menu shows your first name
        userMenu.validateLoggedInName(firstName);

    }

    /**
     * @param user
     */
    public void validateSuccessfulLogin(User user)
    {
        validateSuccessfulLogin(user.getFirstName());
    }

    /**
     * 
     */
    public void validateSuccessfulDeletedAccount()
    {
        successMessage.validateSuccessMessage(Context.localizedText("HomePage.validation.successfulAccountDeletion"));
    }
}
