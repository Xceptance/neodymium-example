package posters.pageobjects.pages.checkout;

import posters.pageobjects.components.CheckoutHeader;
import posters.pageobjects.components.Footer;
import posters.pageobjects.components.UserMenu;
import posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractCheckoutPage extends AbstractPageObject
{
    public CheckoutHeader checkoutHeader = new CheckoutHeader();

    public Footer footer = new Footer();

    public UserMenu userMenu = new UserMenu();

    @Override
    public void validateStructure()
    {
        isExpectedPage();

        checkoutHeader.isComponentAvailable();
        footer.isComponentAvailable();
        userMenu.isComponentAvailable();
    }
}
