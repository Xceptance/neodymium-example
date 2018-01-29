/**
 * 
 */
package posters.neodymium.tests.smoke;

import org.junit.Test;

import posters.dataObjects.Address;
import posters.dataObjects.CreditCard;
import posters.dataObjects.Product;
import posters.flows.OpenHomePageFlow;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.CategoryPage;
import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.browsing.ProductdetailPage;
import posters.pageObjects.pages.checkout.CartPage;
import posters.pageObjects.pages.checkout.NewBillingAddressPage;
import posters.pageObjects.pages.checkout.NewPaymentPage;
import posters.pageObjects.pages.checkout.NewShippingAddressPage;
import posters.pageObjects.pages.checkout.PlaceOrderPlace;

/**
 * @author pfotenhauer
 */
public class GuestOrderTest extends BasicTest
{
    @Test
    public void testOrderingAsGuest()
    {
        // total product count will be updated throughout the test
        int totalCount = 0;
        final String shippingCosts = data.get("shippingCosts");

        // Goto homepage
        HomePage homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu().validateNotLoggedIn();
        // Assure an empty cart
        homePage.miniCart().validateTotalCount(totalCount);
        homePage.miniCart().validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart().getSubtotal();

        // Goto category
        final String categoryName = homePage.topNav().getSubCategoryNameByPosition(3, 2);
        CategoryPage categoryPage = homePage.topNav().clickSubCategoryByPosition(3, 2);
        categoryPage.validate(categoryName);

        // Goto product page
        final String productName = categoryPage.getProductNameByPosition(1, 1);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(1, 1);
        productPage.validate(productName);

        productPage.addToCart("64 x 48 in", "gloss");

        // Goto cart and validate
        final Product product = productPage.getProduct();
        CartPage cartPage = productPage.miniCart().openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(shippingCosts);
        cartPage.miniCart().validateMiniCart(1, product);
        cartPage.miniCart().validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product);
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal, "$0.00");

        final String name = data.get("name");
        final String company = data.get("company");
        final String street = data.get("street");
        final String city = data.get("city");
        final String state = data.get("state");
        final String zip = data.get("zip");
        final String country = data.get("country");
        // setup checkout data
        final Address shippingAddress = new Address(name, company, street, city, state, zip, country);
        final boolean sameBillingAddress = false;
        final Address billingAddress = new Address(name, company, street, city, state, zip, country);
        final CreditCard creditcard = new CreditCard("Jimmy Blue", "4111111111111111", "xxxx xxxx xxxx 1111", "04", "2018");
        // Goto shipping address and validate
        NewShippingAddressPage shippingAddressPage = cartPage.openNewShippingPage();
        shippingAddressPage.validateStructure();

        // Send shipping address and validate billing form
        NewBillingAddressPage billingAddressPage = shippingAddressPage.sendShippingAddressForm(shippingAddress, sameBillingAddress);
        billingAddressPage.validateStructure();

        // Send billing address and validate payment form
        NewPaymentPage paymentPage = billingAddressPage.sendBillingAddressForm(billingAddress);
        paymentPage.validateStructure();

        // Send payment data and validate place order page
        PlaceOrderPlace placeOrderPage = paymentPage.sendPaymentForm(creditcard);
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateAddressAndPayment(shippingAddress, billingAddress, creditcard);

        // Place order
        homePage = placeOrderPage.placeOrder();
        // Validate order confirmation on home page
        homePage.validate();
        homePage.validateSuccessfulOrder();
    }
}
