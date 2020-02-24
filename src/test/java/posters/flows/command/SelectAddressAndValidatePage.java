package posters.flows.command;

import posters.dataobjects.AddressContainer;
import posters.pageobjects.pages.AbstractPageObject;
import posters.pageobjects.pages.checkout.AbstractCheckoutPage;
import posters.pageobjects.pages.checkout.AbstractListPage;

public class SelectAddressAndValidatePage implements Command
{

    @Override
    public AbstractCheckoutPage execute(AbstractPageObject page, AddressContainer address)
    {
        AbstractListPage listPage = (AbstractListPage) page;
        listPage.validateStructure();
        return listPage.selectAddressByPosition(1);
    }

}
