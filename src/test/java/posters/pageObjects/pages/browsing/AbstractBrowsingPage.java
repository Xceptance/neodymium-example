package posters.pageObjects.pages.browsing;

import posters.pageObjects.components.ErrorMessage;
import posters.pageObjects.components.Footer;
import posters.pageObjects.components.Header;
import posters.pageObjects.components.MiniCart;
import posters.pageObjects.components.Search;
import posters.pageObjects.components.SuccessMessage;
import posters.pageObjects.components.TopNavigation;
import posters.pageObjects.components.UserMenu;
import posters.pageObjects.pages.AbstractPageObject;

public abstract class AbstractBrowsingPage extends AbstractPageObject
{

    private Header header;

    private Footer footer;

    private MiniCart miniCart;

    private Search search;

    private TopNavigation topNav;

    private UserMenu userMenu;

    private SuccessMessage successMessage;

    private ErrorMessage errorMessage;

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.page.AbstractPageObject#validateStructure()
     */
    @Override
    public void validateStructure()
    {
        isExpectedPage();
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

    public SuccessMessage successMessage()
    {
        if (successMessage == null)
        {
            successMessage = new SuccessMessage();
        }
        successMessage.isComponentAvailable();
        return successMessage;
    }

    public ErrorMessage errorMessage()
    {
        if (errorMessage == null)
        {
            errorMessage = new ErrorMessage();
        }
        errorMessage.isComponentAvailable();
        return errorMessage;
    }
}
