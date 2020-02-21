package posters.pageobjects.pages.checkout;

import posters.dataobjects.AddressContainer;
import posters.pageobjects.pages.AbstractPageObject;

public abstract class AbstractEnterAddressPage extends AbstractCheckoutPage
{

    public abstract AbstractPageObject isExpectedPage();

    public abstract AbstractEnterAddressPage fillForm(AddressContainer address);

    public abstract AbstractCheckoutPage sendCorrectForm();

    public abstract AbstractCheckoutPage sendIncorrectForm();

    public abstract AbstractEnterAddressPage validateEnteredData(AddressContainer address);
}
