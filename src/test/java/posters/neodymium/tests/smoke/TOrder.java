/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;

import posters.flows.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.objects.Address;
import posters.pageObjects.objects.CreditCard;
import posters.pageObjects.objects.Product;
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
@Browser(
{
  "Chrome_1024x768"
})
public class TOrder extends BasicTest
{
    @Test
    public void test()
    {
        // total product count will be updated throughout the test
        int totalCount = 0;

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
        homePage = loginPage.sendLoginform("john@doe.com", "topsecret");

        homePage.validateSuccessfulLogin("John");

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
        cartPage.validateShippingCosts(SHIPPINGCOSTS);
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

        // setup checkout data for validation
        final Address shippingAddress = new Address("John Doe", "John Doe Inc.", "5-7 John Doe street", "New York", "NY", "12345", "United States");
        final Address billingAddress = new Address("John Doe", "John Doe Inc.", "5-7 John Doe street", "New York", "NY", "12345", "United States");
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
}
