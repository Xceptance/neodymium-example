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
    public CheckoutHeader header = new CheckoutHeader();

    public Footer footer = new Footer();

    public UserMenu userMenu = new UserMenu();

    @Override
    public void validateStructure()
    {
        isExpectedPage();
    }
}
