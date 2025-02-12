package posters.pageobjects.pages.checkout;

import posters.pageobjects.components.CheckoutHeader;
import posters.pageobjects.components.Footer;
import posters.pageobjects.components.UserMenu;
import posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractCheckoutPage<T extends AbstractCheckoutPage<T>> extends AbstractPageObject<T>
{
    public CheckoutHeader checkoutHeader = new CheckoutHeader();

    public Footer footer = new Footer();

    public UserMenu userMenu = new UserMenu();

    @Override
    public T validateStructure()
    {
        assertExpectedPage();

        checkoutHeader.assertComponentAvailable();
        footer.assertComponentAvailable();
        userMenu.assertComponentAvailable();

        return (T) this;
    }
}
