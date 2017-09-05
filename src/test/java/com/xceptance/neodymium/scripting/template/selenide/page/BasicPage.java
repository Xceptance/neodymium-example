package com.xceptance.neodymium.scripting.template.selenide.page;

import org.junit.Assert;

import com.xceptance.neodymium.scripting.template.selenide.component.CFooter;
import com.xceptance.neodymium.scripting.template.selenide.component.CHeader;
import com.xceptance.neodymium.scripting.template.selenide.component.CMiniCart;
import com.xceptance.neodymium.scripting.template.selenide.component.CSearch;
import com.xceptance.neodymium.scripting.template.selenide.component.CTopNav;
import com.xceptance.neodymium.scripting.template.selenide.component.CUserMenu;
import com.xceptance.neodymium.scripting.template.selenide.utility.Settings;

public abstract class BasicPage implements PageObject
{

    private CHeader header;

    private CFooter footer;

    private CMiniCart miniCart;

    private CSearch search;

    private CTopNav topNav;

    private CUserMenu userMenu;

    public BasicPage()
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
        miniCart();
        search();
        topNav();
        userMenu();
    }

    void validatePage()
    {
        Assert.assertTrue("The current page doesn't match the expected page", isExpectedPage());
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

    public CHeader header()
    {
        if (header == null)
        {
            header = new CHeader();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            header.validateComponent();
        }
        return header;
    }

    public CMiniCart miniCart()
    {
        if (miniCart == null)
        {
            miniCart = new CMiniCart();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            miniCart.validateComponent();
        }
        return miniCart;
    }

    public CSearch search()
    {
        if (search == null)
        {
            search = new CSearch();
        }
        // TODO Discuss a way to implement an implicit basic validation for components
        if (Settings.IMPLICITVALIDATION)
        {
            validatePage();
            search.validateComponent();
        }
        return search;
    }

    public CTopNav topNav()
    {
        if (topNav == null)
        {
            topNav = new CTopNav();
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
}
