package posters.pageobjects.pages.browsing;

import posters.pageobjects.components.ErrorMessage;
import posters.pageobjects.components.Footer;
import posters.pageobjects.components.Header;
import posters.pageobjects.components.SuccessMessage;
import posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractBrowsingPage<T extends AbstractBrowsingPage<T>> extends AbstractPageObject<T>
{
    public Header header = new Header();

    public Footer footer = new Footer();

    public SuccessMessage successMessage = new SuccessMessage();

    public ErrorMessage errorMessage = new ErrorMessage();

    @Override
    public T validateStructure()
    {
        super.assertExpectedPage();

        header.assertComponentAvailable();
        footer.assertComponentAvailable();
        header.search.assertComponentAvailable();
        header.topNav.assertComponentAvailable();
        header.userMenu.assertComponentAvailable();

        return (T) this;
    }
}
