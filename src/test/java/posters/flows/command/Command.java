package posters.flows.command;

import posters.dataobjects.AddressContainer;
import posters.pageobjects.pages.AbstractPageObject;
import posters.pageobjects.pages.checkout.AbstractCheckoutPage;

public interface Command
{
    public AbstractCheckoutPage execute(AbstractPageObject page, AddressContainer address);

}
