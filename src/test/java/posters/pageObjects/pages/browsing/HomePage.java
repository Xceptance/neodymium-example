package posters.pageObjects.pages.browsing;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.matchText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import cucumber.api.java.en.Then;
import posters.dataObjects.User;

public class HomePage extends AbstractBrowsingPage
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.AbstractPage()
     */
    @Override
    public void isExpectedPage()
    {
        $("#titleIndex").should(exist);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.scripting.selenide.page.AbstractPage()
     */
    @Then("^I want see the logo, the carousel and some hot products$")
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
        footer().validate();
    }

    public void validateSuccessfulOrder()
    {
        successMessage().validateSuccessMessage("Thank you for shopping with us!");
        // Verify that the mini cart is empty again
        miniCart().validateTotalCount(0);
        miniCart().validateSubtotal("$0.00");
    }

    /**
     * @param firstName
     *            The name should be shown in the mini User Menu
     */
    @Then("^I want to be logged in successfully with \"([^\"]*)\"")
    public void validateSuccessfulLogin(String firstName)
    {
        successMessage().validateSuccessMessage("Login successful. Have fun in our shop!");
        // Verify that the user menu shows your first name
        // Click on the mini user menu symbol
        userMenu().openUserMenu();
        // Asserts the Menu shows your first name.
        $("#userMenu .firstName").shouldHave(exactText(firstName));
        userMenu().closeUserMenu();

        // Verify that you are logged in
        // Makes sure the mini menu element has the "logged" class active instead of the "not-logged" class.
        $("#showUserMenu .logged").shouldHave(exactText(""));

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
        successMessage().validateSuccessMessage("Your account has been deleted. We hope to see you soon again!");
    }

    /**
     * @return
     */
    public boolean isLoggedIn()
    {
        return $("#userMenu .goToAccountOverview").exists();
    }
}
