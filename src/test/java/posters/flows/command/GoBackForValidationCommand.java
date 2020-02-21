package posters.flows.command;

import com.codeborne.selenide.Selenide;

import posters.dataobjects.AddressContainer;
import posters.pageobjects.pages.AbstractPageObject;
import posters.pageobjects.pages.checkout.AbstractCheckoutPage;
import posters.pageobjects.pages.checkout.AbstractEnterAddressPage;

public class GoBackForValidationCommand implements posters.flows.command.Command
{
    @Override
    public AbstractCheckoutPage execute(AbstractPageObject page, AddressContainer address)
    {
        AbstractEnterAddressPage addressPage = (AbstractEnterAddressPage) page;
        addressPage.fillForm(address);
        addressPage.sendCorrectForm();
        Selenide.back();
        addressPage.isExpectedPage();
        addressPage.validateEnteredData(address);
        return addressPage.sendCorrectForm();
    }

}
