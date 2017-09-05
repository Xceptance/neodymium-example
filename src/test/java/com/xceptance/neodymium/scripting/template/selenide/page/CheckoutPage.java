/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

import org.junit.Assert;

import com.xceptance.neodymium.scripting.template.selenide.component.CCheckoutHeader;
import com.xceptance.neodymium.scripting.template.selenide.component.CFooter;
import com.xceptance.neodymium.scripting.template.selenide.component.CUserMenu;
import com.xceptance.neodymium.scripting.template.selenide.utility.Settings;

/**
 * @author pfotenhauer
 */
public abstract class CheckoutPage implements PageObject
{

    private CCheckoutHeader header;

    private CFooter footer;

    private CUserMenu userMenu;

    public CheckoutPage()
    {
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            validateBasiComponents();
        }
    }

    private void validateBasiComponents()
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
    public CUserMenu userMenu()
    {
        if (userMenu == null)
        {
            userMenu = new CUserMenu();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            userMenu.validateComponent();
        }
        return userMenu;
    }

    public CFooter footer()
    {
        if (footer == null)
        {
            footer = new CFooter();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            footer.validateComponent();
        }
        return footer;
    }

    public CCheckoutHeader header()
    {
        if (header == null)
        {
            header = new CCheckoutHeader();
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
