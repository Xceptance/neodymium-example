package com.xceptance.neodymium.scripting.template.selenide.page;

import org.junit.Assert;

import com.xceptance.neodymium.scripting.template.selenide.component.CFooter;
import com.xceptance.neodymium.scripting.template.selenide.component.CHeader;
import com.xceptance.neodymium.scripting.template.selenide.component.CMiniCart;
import com.xceptance.neodymium.scripting.template.selenide.component.CSearch;

public abstract class BasicPage
{

    private CFooter footer;

    private CHeader header;

    private CMiniCart miniCart;

    private CSearch search;

    public BasicPage()
    {
        validatePage();
        validateBasiComponents();
    }

    private void validateBasiComponents()
    {
        header();
        footer();
        miniCart();
        search();
    }

    private void validatePage()
    {
        Assert.assertTrue("The current page doesn't match the awaited page", isAwaitedPage());
    }

    protected boolean isAwaitedPage()
    {
        return true;
    }

    public CFooter footer()
    {
        if (footer == null)
        {
            footer = new CFooter();
        }
        return footer;
    }

    public CHeader header()
    {
        if (header == null)
        {
            header = new CHeader();
        }
        return header;
    }

    public CMiniCart miniCart()
    {
        if (miniCart == null)
        {
            miniCart = new CMiniCart();
        }
        return miniCart;
    }

    public CSearch search()
    {
        if (search == null)
        {
            search = new CSearch();
        }
        return search;
    }

    abstract public void validate();
}
