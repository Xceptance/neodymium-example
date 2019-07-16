/**
 * 
 */
package posters.tests.smoke;

import org.junit.After;
import org.junit.Test;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.dataobjects.Address;
import posters.dataobjects.CreditCard;
import posters.dataobjects.Product;
import posters.flows.CartCleanUpFlow;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.pageobjects.pages.checkout.BillingAddressPage;
import posters.pageobjects.pages.checkout.CartPage;
import posters.pageobjects.pages.checkout.PaymentPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;
import posters.pageobjects.pages.checkout.ShippingAddressPage;
import posters.pageobjects.pages.user.LoginPage;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Owner("Lisa Smith")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class RegisteredOrderTest extends AbstractTest
{
    @Test
    public void testOrderingAsRegisteredUser()
    {
        // total product count will be updated throughout the test
        int totalCount = 0;
        final String shippingCosts = Neodymium.dataValue("shippingCosts");

        // Go to homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu.validateNotLoggedIn();
        // Assure an empty cart
        homePage.miniCart.validateTotalCount(totalCount);
        homePage.miniCart.validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart.getSubtotal();

        // Go to login form
        LoginPage loginPage = homePage.userMenu.openLogin();
        loginPage.validateStructure();
        final String email = Neodymium.dataValue("email");
        final String password = Neodymium.dataValue("password");
        homePage = loginPage.sendLoginform(email, password);

        final String firstname = Neodymium.dataValue("firstname");
        homePage.validateSuccessfulLogin(firstname);

        // Go to category
        final String categoryName = homePage.topNav.getSubCategoryNameByPosition(2, 3);
        CategoryPage categoryPage = homePage.topNav.clickSubCategoryByPosition(2, 3);
        categoryPage.validate(categoryName);

        // Go to product page
        final String productName = categoryPage.getProductNameByPosition(2, 1);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(2, 1);
        productPage.validate(productName);

        productPage.addToCart("32 x 24 in", "matte");

        // Go to cart and validate
        final Product product = productPage.getProduct();
        CartPage cartPage = productPage.miniCart.openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(shippingCosts);
        cartPage.miniCart.validateMiniCart(1, product);
        cartPage.miniCart.validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product);
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal, 0.00);

        // Go to shipping address and validate
        ShippingAddressPage shippingAddressPage = cartPage.openShippingPage();
        shippingAddressPage.validateStructure();

        // Send shipping address and validate billing form
        BillingAddressPage billingAddressPage = shippingAddressPage.selectShippingAddress(1);
        billingAddressPage.validateStructure();

        // Send billing address and validate payment form
        PaymentPage paymentPage = billingAddressPage.selectBillingAddress(1);
        paymentPage.validateStructure();

        final String name = firstname + " " + Neodymium.dataValue("lastname");
        final String company = Neodymium.dataValue("company");
        final String street = Neodymium.dataValue("street");
        final String city = Neodymium.dataValue("city");
        final String state = Neodymium.dataValue("state");
        final String zip = Neodymium.dataValue("zip");
        final String country = Neodymium.dataValue("country");

        // setup checkout data for validation
        final Address shippingAddress = new Address(name, company, street, city, state, zip, country);
        final Address billingAddress = new Address(name, company, street, city, state, zip, country);
        final CreditCard creditcard = new CreditCard("John Doe", "4111111111111111", "xxxx xxxx xxxx 1111", "08", "2022");

        // Send payment data and validate place order page
        PlaceOrderPage placeOrderPage = paymentPage.selectCreditCard(1);
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateAddressesAndPayment(shippingAddress, billingAddress, creditcard);

        // Place order
        homePage = placeOrderPage.placeOrder();
        // Validate order confirmation on Homepage
        homePage.validate();
        homePage.validateSuccessfulOrder();
    }

    @After
    public void after()
    {
        CartCleanUpFlow.flow();
    }
}
