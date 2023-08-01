package posters.pageobjects.pages.browsing;

import posters.pageobjects.components.ErrorMessage;
import posters.pageobjects.components.Footer;
import posters.pageobjects.components.Header;
import posters.pageobjects.components.MiniCart;
import posters.pageobjects.components.Search;
import posters.pageobjects.components.SuccessMessage;
import posters.pageobjects.components.TopNavigation;
import posters.pageobjects.components.UserMenu;
import posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractBrowsingPage extends AbstractPageObject
{
    public Header header = new Header();

    public Footer footer = new Footer();

    public MiniCart miniCart = new MiniCart();

    public Search search = new Search();

    public TopNavigation topNav = new TopNavigation();

    public UserMenu userMenu = new UserMenu();

    public SuccessMessage successMessage = new SuccessMessage();

    public ErrorMessage errorMessage = new ErrorMessage();

    @Override
    public void validateStructure()
    {
        isExpectedPage();

        header.isComponentAvailable();
        footer.isComponentAvailable();
        miniCart.isComponentAvailable();
        search.isComponentAvailable();
        topNav.isComponentAvailable();
        userMenu.isComponentAvailable();
        successMessage.isComponentAvailable();
        errorMessage.isComponentAvailable();
    }
}
