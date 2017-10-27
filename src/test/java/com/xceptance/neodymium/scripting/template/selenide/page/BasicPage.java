package com.xceptance.neodymium.scripting.template.selenide.page;

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
import com.xceptance.neodymium.scripting.template.selenide.utility.Settings;

// TODO rename Pages
public abstract class BasicPage implements PageObject
{

    private Header header;

    private Footer footer;

    private MiniCart miniCart;

    private Search search;

    private TopNavigation topNav;

    private UserMenu userMenu;

    public BasicPage()
    {
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            validateBasicComponents();
        }
    }

    private void validateBasicComponents()
    {
        header();
        footer();
        miniCart();
        search();
        topNav();
        userMenu();
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
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            footer.validateComponent();
        }
        return footer;
    }

    public Header header()
    {
        if (header == null)
        {
            header = new Header();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            header.validateComponent();
        }
        return header;
    }

    public MiniCart miniCart()
    {
        if (miniCart == null)
        {
            miniCart = new MiniCart();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            miniCart.validateComponent();
        }
        return miniCart;
    }

    public Search search()
    {
        if (search == null)
        {
            search = new Search();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            search.validateComponent();
        }
        return search;
    }

    public TopNavigation topNav()
    {
        if (topNav == null)
        {
            topNav = new TopNavigation();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            topNav.validateComponent();
        }
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
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            userMenu.validateComponent();
        }
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
