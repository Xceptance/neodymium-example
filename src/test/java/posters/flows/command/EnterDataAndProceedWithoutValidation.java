package posters.flows.command;

import posters.dataobjects.AddressContainer;
import posters.pageobjects.pages.AbstractPageObject;
import posters.pageobjects.pages.checkout.AbstractCheckoutPage;
import posters.pageobjects.pages.checkout.AbstractEnterAddressPage;

public class EnterDataAndProceedWithoutValidation implements Command
{
    @Override
    public AbstractCheckoutPage execute(AbstractPageObject page, AddressContainer address)
    {
        AbstractEnterAddressPage addressPage = (AbstractEnterAddressPage) page;
        addressPage.isExpectedPage();
        addressPage.fillForm(address);

        return addressPage.sendCorrectForm();
    }

}
