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
    }
}
