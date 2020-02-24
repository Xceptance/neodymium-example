package posters.pageobjects.components;

import posters.dataobjects.AddressContainer;
import posters.pageobjects.pages.checkout.AbstractCheckoutPage;

public abstract class AbstractOverlayComponent extends AbstractComponent
{

    public abstract void isComponentAvailable();

    public abstract AbstractOverlayComponent fillForm(AddressContainer address);

    public abstract AbstractCheckoutPage sendForm();
}
