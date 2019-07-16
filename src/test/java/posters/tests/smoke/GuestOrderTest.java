/**
 * 
 */
package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.dataobjects.Address;
import posters.dataobjects.CreditCard;
import posters.dataobjects.Product;
import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.CategoryPage;
import posters.pageobjects.pages.browsing.HomePage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.pageobjects.pages.checkout.CartPage;
import posters.pageobjects.pages.checkout.NewBillingAddressPage;
import posters.pageobjects.pages.checkout.NewPaymentPage;
import posters.pageobjects.pages.checkout.NewShippingAddressPage;
import posters.pageobjects.pages.checkout.PlaceOrderPage;
import posters.tests.AbstractTest;

/**
 * @author pfotenhauer
 */
@Owner("Joe Fix")
@Severity(SeverityLevel.BLOCKER)
@Tag("smoke")
@Tag("registered")
public class GuestOrderTest extends AbstractTest
{
    @Test
    public void testOrderingAsGuest()
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

        // Go to category
        final String categoryName = homePage.topNav.getSubCategoryNameByPosition(3, 2);
        CategoryPage categoryPage = homePage.topNav.clickSubCategoryByPosition(3, 2);
        categoryPage.validate(categoryName);

        // Goto product page
        final String productName = categoryPage.getProductNameByPosition(1, 1);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(1, 1);
        productPage.validate(productName);

        productPage.addToCart("64 x 48 in", "gloss");

        // Go to cart and validate
        final Product product = productPage.getProduct();
        CartPage cartPage = productPage.miniCart.openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(shippingCosts);
        cartPage.miniCart.validateMiniCart(1, product);
        cartPage.miniCart.validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product);
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal, 0.00);

        final String name = Neodymium.dataValue("name");
        final String company = Neodymium.dataValue("company");
        final String street = Neodymium.dataValue("street");
        final String city = Neodymium.dataValue("city");
        final String state = Neodymium.dataValue("state");
        final String zip = Neodymium.dataValue("zip");
        final String country = Neodymium.dataValue("country");
        // setup checkout data
        final Address shippingAddress = new Address(name, company, street, city, state, zip, country);
        final boolean sameBillingAddress = false;
        final Address billingAddress = new Address(name, company, street, city, state, zip, country);
        final CreditCard creditcard = new CreditCard("Jimmy Blue", "4111111111111111", "xxxx xxxx xxxx 1111", "04", "2022");
        // Go to shipping address and validate
        NewShippingAddressPage shippingAddressPage = cartPage.openNewShippingPage();
        shippingAddressPage.validateStructure();

        // Send shipping address and validate billing form
        NewBillingAddressPage billingAddressPage = shippingAddressPage.sendShippingAddressForm(shippingAddress, sameBillingAddress);
        billingAddressPage.validateStructure();

        // Send billing address and validate payment form
        NewPaymentPage paymentPage = billingAddressPage.sendBillingAddressForm(billingAddress);
        paymentPage.validateStructure();

        // Send payment data and validate place order page
        PlaceOrderPage placeOrderPage = paymentPage.sendPaymentForm(creditcard);
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateAddressesAndPayment(shippingAddress, billingAddress, creditcard);

        // Place order
        homePage = placeOrderPage.placeOrder();
        // Validate order confirmation on home page
        homePage.validate();
        homePage.validateSuccessfulOrder();
    }
}
