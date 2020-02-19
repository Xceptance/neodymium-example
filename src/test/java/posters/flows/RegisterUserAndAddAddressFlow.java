package posters.flows;

import posters.dataobjects.Address;
import posters.dataobjects.User;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.user.MyAddressesPage;

public class RegisterUserAndAddAddressFlow
{
    public static MyAddressesPage addShippingAddress(User user, Address address)
    {
        HomePage homePage = RegisterAndLoginFlow.registerAndLogin(user);
        MyAddressesPage myAddressesPage = homePage.userMenu.openAccountOverview().openMyAddressesPage();
        return myAddressesPage.addNewShippingAddress(address).isExpectedPage();
    }

    public static MyAddressesPage addBillingAddress(User user, Address address)
    {
        HomePage homePage = RegisterAndLoginFlow.registerAndLogin(user);
        MyAddressesPage myAddressesPage = homePage.userMenu.openAccountOverview().openMyAddressesPage();
        return myAddressesPage.addNewBillingAddress(address).isExpectedPage();
    }
}
