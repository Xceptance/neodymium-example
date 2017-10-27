/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page.checkout;

import org.junit.Assert;

import com.xceptance.neodymium.scripting.template.selenide.component.CheckoutHeader;
import com.xceptance.neodymium.scripting.template.selenide.component.Footer;
import com.xceptance.neodymium.scripting.template.selenide.component.UserMenu;
import com.xceptance.neodymium.scripting.template.selenide.page.AbstractPageObject;
import com.xceptance.neodymium.scripting.template.selenide.utility.Settings;

/**
 * @author pfotenhauer
 */
public abstract class AbstractCheckoutPage extends AbstractPageObject
{

    private CheckoutHeader header;

    private Footer footer;

    private UserMenu userMenu;

    public AbstractCheckoutPage()
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
        userMenu();
    }

    void validatePage()
    {
        Assert.assertTrue("The current page doesn't match the expected page", isExpectedPage());
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

    public CheckoutHeader header()
    {
        if (header == null)
        {
            header = new CheckoutHeader();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            header.validateComponent();
        }
        return header;
    }
}
