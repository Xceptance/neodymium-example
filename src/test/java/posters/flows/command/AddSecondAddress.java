package posters.flows.command;

import posters.dataobjects.AddressContainer;
import posters.pageobjects.pages.AbstractPageObject;
import posters.pageobjects.pages.checkout.AbstractCheckoutPage;
import posters.pageobjects.pages.checkout.AbstractListPage;

public class AddSecondAddress implements Command
{

    @Override
    public AbstractCheckoutPage execute(AbstractPageObject page, AddressContainer secondAddress)
    {
        AbstractListPage listPage = (AbstractListPage) page;
        return listPage.getOverlayComponent().fillForm(secondAddress).sendForm();
    }

}
