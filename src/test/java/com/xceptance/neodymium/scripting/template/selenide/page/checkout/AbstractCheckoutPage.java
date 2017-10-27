/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page.checkout;

import org.junit.Assert;

import com.xceptance.neodymium.scripting.template.selenide.component.CheckoutHeader;
import com.xceptance.neodymium.scripting.template.selenide.component.Footer;
import com.xceptance.neodymium.scripting.template.selenide.component.UserMenu;
import com.xceptance.neodymium.scripting.template.selenide.page.AbstractPageObject;

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
        validatePage();
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
        userMenu.isComponentAvailable();
        return userMenu;
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

    public CheckoutHeader header()
    {
        if (header == null)
        {
            header = new CheckoutHeader();
        }
        header.isComponentAvailable();
        return header;
    }
}
