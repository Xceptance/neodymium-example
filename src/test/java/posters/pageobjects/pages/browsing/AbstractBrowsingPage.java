package posters.pageobjects.pages.browsing;

import posters.pageobjects.components.ErrorMessage;
import posters.pageobjects.components.Footer;
import posters.pageobjects.components.Header;
import posters.pageobjects.components.SuccessMessage;
import posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractBrowsingPage extends AbstractPageObject
{
    public Header header = new Header();

    public Footer footer = new Footer();

    public SuccessMessage successMessage = new SuccessMessage();

    public ErrorMessage errorMessage = new ErrorMessage();

    @Override
    public void validateStructure()
    {
        isExpectedPage();

        header.isComponentAvailable();
        footer.isComponentAvailable();
        //header.miniCart.isComponentAvailable();
        header.search.isComponentAvailable();
        header.topNav.isComponentAvailable();
        header.userMenu.isComponentAvailable();
    }
}
