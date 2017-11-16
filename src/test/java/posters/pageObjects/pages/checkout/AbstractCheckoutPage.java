/**
 * 
 */
package posters.pageObjects.pages.checkout;

import posters.pageObjects.components.CheckoutHeader;
import posters.pageObjects.components.Footer;
import posters.pageObjects.components.UserMenu;
import posters.pageObjects.pages.AbstractPageObject;

/**
 * @author pfotenhauer
 */
public abstract class AbstractCheckoutPage extends AbstractPageObject
{

    private CheckoutHeader header;

    private Footer footer;

    private UserMenu userMenu;

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
