package posters.pageobjects.pages.checkout;

import posters.pageobjects.components.AbstractOverlayComponent;
import posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractListPage extends AbstractCheckoutPage
{

    public abstract AbstractPageObject isExpectedPage();

    public abstract AbstractCheckoutPage selectAddressByPosition(int position);

    public abstract AbstractOverlayComponent getOverlayComponent();

}
