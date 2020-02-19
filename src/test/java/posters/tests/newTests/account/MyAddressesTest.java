package posters.tests.newTests.account;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.module.statement.testdata.DataSet;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import posters.dataobjects.Addresses;
import posters.dataobjects.User;
import posters.flows.RegisterUserAndAddAddressFlow;
import posters.pageobjects.pages.user.MyAddressesPage;
import posters.tests.AbstractTest;

public class MyAddressesTest extends AbstractTest
{
    private Addresses addresses;

    private User user;

    @Before
    public void dataSetUp()
    {
        addresses = DataUtils.get(Addresses.class);
        user = User.createRandomUser();
    }

    @DataSet(id = "add/delete shipping/billing")
    @Test
    public void testMyAddressesShipping()
    {
        MyAddressesPage myAddressesPage = RegisterUserAndAddAddressFlow.addShippingAddress(user, addresses.getAddress());

        myAddressesPage.validateShippingAddress(addresses.getAddress());
        myAddressesPage.validateStructure();
    }

    @DataSet(id = "add/delete shipping/billing")
    @Test
    public void testMyAddressesBilling()
    {
        MyAddressesPage myAddressesPage = RegisterUserAndAddAddressFlow.addBillingAddress(user, addresses.getAddress());

        myAddressesPage.validateBillingAddress(addresses.getAddress());
    }

    @DataSet(id = "edit shipping/billing")
    @Test
    public void testEditMyShippingAddress()
    {
        MyAddressesPage myAddressesPage = RegisterUserAndAddAddressFlow.addShippingAddress(user, addresses.getAddress());

        myAddressesPage.editShippingAddress(addresses.getAddress().getAddressLine(), addresses.getNewAddress());
        myAddressesPage.validateShippingAddress(addresses.getNewAddress());
    }

    @DataSet(id = "edit shipping/billing")
    @Test
    public void testEditMyBillingAddress()
    {
        MyAddressesPage myAddressesPage = RegisterUserAndAddAddressFlow.addBillingAddress(user, addresses.getAddress());

        myAddressesPage.editBillingAddress(addresses.getAddress().getAddressLine(), addresses.getNewAddress());
        myAddressesPage.validateBillingAddress(addresses.getNewAddress());
    }

    @DataSet(id = "add/delete shipping/billing")
    @Test
    public void deleteShippingAddress()
    {
        MyAddressesPage myAddressesPage = RegisterUserAndAddAddressFlow.addShippingAddress(user, addresses.getAddress());

        myAddressesPage.deleteShippingAddress(addresses.getAddress().getAddressLine()).confirmDelete(user.getPassword());

        myAddressesPage.successMessage.validateSuccessMessage(Neodymium.localizedText("AccountPages.validation.addressDeletedSuccessMessage"));
        myAddressesPage.validateShippingAddressDoesntExist(addresses.getAddress().getAddressLine());
    }

    @DataSet(id = "add/delete shipping/billing")
    @Test
    public void deleteBillingAddress()
    {
        MyAddressesPage myAddressesPage = RegisterUserAndAddAddressFlow.addBillingAddress(user, addresses.getAddress());

        myAddressesPage.deleteBillingAddress(addresses.getAddress().getAddressLine()).confirmDelete(user.getPassword());

        myAddressesPage.successMessage.validateSuccessMessage(Neodymium.localizedText("AccountPages.validation.addressDeletedSuccessMessage"));
        myAddressesPage.validateBillingAddressDoesntExist(addresses.getAddress().getAddressLine());
    }
}
