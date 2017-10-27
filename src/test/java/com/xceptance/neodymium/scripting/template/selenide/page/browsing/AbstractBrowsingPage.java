package com.xceptance.neodymium.scripting.template.selenide.page.browsing;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import org.junit.Assert;

import com.xceptance.neodymium.scripting.template.selenide.component.Footer;
import com.xceptance.neodymium.scripting.template.selenide.component.Header;
import com.xceptance.neodymium.scripting.template.selenide.component.MiniCart;
import com.xceptance.neodymium.scripting.template.selenide.component.Search;
import com.xceptance.neodymium.scripting.template.selenide.component.TopNavigation;
import com.xceptance.neodymium.scripting.template.selenide.component.UserMenu;
import com.xceptance.neodymium.scripting.template.selenide.page.AbstractPageObject;

public abstract class AbstractBrowsingPage extends AbstractPageObject
{

    private Header header;

    private Footer footer;

    private MiniCart miniCart;

    private Search search;

    private TopNavigation topNav;

    private UserMenu userMenu;

    public AbstractBrowsingPage()
    {
        validatePage();
    }

    void validatePage()
    {
        Assert.assertTrue("The current page doesn't match the expected page", isExpectedPage());
    }

    public Footer footer()
    {
        if (footer == null)
        {
            footer = new Footer();
        }
        footer.isComponentAvailable();
        return footer;
    }

    public Header header()
    {
        if (header == null)
        {
            header = new Header();
        }
        header.isComponentAvailable();
        return header;
    }

    public MiniCart miniCart()
    {
        if (miniCart == null)
        {
            miniCart = new MiniCart();
        }
        miniCart.isComponentAvailable();
        return miniCart;
    }

    public Search search()
    {
        if (search == null)
        {
            search = new Search();
        }
        search.isComponentAvailable();
        return search;
    }

    public TopNavigation topNav()
    {
        if (topNav == null)
        {
            topNav = new TopNavigation();
        }
        topNav.isComponentAvailable();
        return topNav;
    }

    /**
     * @return
     */
    public UserMenu userMenu()
    {
        if (userMenu == null)
        {
            userMenu = new UserMenu();
        }
        userMenu.isComponentAvailable();
        return userMenu;
    }

    protected void validateSuccessMessage(String message)
    {
        // Wait until javascript makes the success message visible
        // Waits until javascript makes the success message visible.
        $("#successMessage").shouldBe(visible);
        // Makes sure the correct text is displayed.
        $("#successMessage").shouldHave(exactText("× " + message));
    }

    protected void validateErrorMessage(String message)
    {
        // Wait until javascript makes the error message visible
        // Waits until javascript makes the error message visible.
        $("#errorMessage").shouldBe(visible);
        // Makes sure the correct text is displayed.
        $("#errorMessage").shouldHave(exactText("× " + message));
    }

    /**
     * 
     */
    public void validateNoErrorMessageOnPage()
    {
        // Check that the error message is not visible.
        $("#errorMessage").shouldNotBe(visible);
    }
}
