/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.After;
import org.junit.Test;

import posters.flows.CartCleanUpFlow;
import posters.flows.OpenHomePageFlow;
import posters.neodymium.dataObjects.Address;
import posters.neodymium.dataObjects.CreditCard;
import posters.neodymium.dataObjects.Product;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.CategoryPage;
import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.browsing.ProductdetailPage;
import posters.pageObjects.pages.checkout.BillingAddressPage;
import posters.pageObjects.pages.checkout.CartPage;
import posters.pageObjects.pages.checkout.PaymentPage;
import posters.pageObjects.pages.checkout.PlaceOrderPlace;
import posters.pageObjects.pages.checkout.ShippingAddressPage;
import posters.pageObjects.pages.user.LoginPage;

/**
 * @author pfotenhauer
 */
public class RegisteredOrderTest extends BasicTest
{
    @Test
    public void testOrderingAsRegisteredUser()
    {
        // total product count will be updated throughout the test
        int totalCount = 0;
        final String shippingCosts = data.get("shippingCosts");

        // Goto homepage
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu().validateNotLoggedIn();
        // Assure an empty cart
        homePage.miniCart().validateTotalCount(totalCount);
        homePage.miniCart().validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart().getSubtotal();

        // Goto login form
        LoginPage loginPage = homePage.userMenu().openLogin();
        loginPage.validateStructure();
        final String email = data.get("email");
        final String password = data.get("password");
        homePage = loginPage.sendLoginform(email, password);

        final String firstname = data.get("firstname");
        homePage.validateSuccessfulLogin(firstname);

        // Goto category
        final String categoryName = homePage.topNav().getSubCategoryNameByPosition(2, 3);
        CategoryPage categoryPage = homePage.topNav().clickSubCategoryByPosition(2, 3);
        categoryPage.validate(categoryName);

        // Goto product page
        final String productName = categoryPage.getProductNameByPosition(2, 1);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(2, 1);
        productPage.validate(productName);

        productPage.addToCart("32 x 24 in", "matte");

        // Goto cart and validate
        final Product product = productPage.getProduct();
        CartPage cartPage = productPage.miniCart().openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(shippingCosts);
        cartPage.miniCart().validateMiniCart(1, product);
        cartPage.miniCart().validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product);
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal, "$0.00");

        // Goto shipping address and validate
        ShippingAddressPage shippingAddressPage = cartPage.openShippingPage();
        shippingAddressPage.validateStructure();

        // Send shipping address and validate billing form
        BillingAddressPage billingAddressPage = shippingAddressPage.selectShippingAddress(1);
        billingAddressPage.validateStructure();

        // Send billing address and validate payment form
        PaymentPage paymentPage = billingAddressPage.selectBillingAddress(1);
        paymentPage.validateStructure();

        final String name = firstname + " " + data.get("lastname");
        final String company = data.get("company");
        final String street = data.get("street");
        final String city = data.get("city");
        final String state = data.get("state");
        final String zip = data.get("zip");
        final String country = data.get("country");

        // setup checkout data for validation
        final Address shippingAddress = new Address(name, company, street, city, state, zip, country);
        final Address billingAddress = new Address(name, company, street, city, state, zip, country);
        final CreditCard creditcard = new CreditCard("John Doe", "4111111111111111", "xxxx xxxx xxxx 1111", "08", "2022");

        // Send payment data and validate place order page
        PlaceOrderPlace placeOrderPage = paymentPage.selectCreditCard(1);
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateAddressAndPayment(shippingAddress, billingAddress, creditcard);

        // Place order
        homePage = placeOrderPage.placeOrder();
        // Validate order confirmation on Homepage
        homePage.validate();
        homePage.validateSuccessfulOrder();
    }

    @After
    public void after()
    {
        new CartCleanUpFlow().flow();
    }
}
