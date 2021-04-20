package posters.tests.smoke;

import org.junit.After;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.dataobjects.Address;
import posters.dataobjects.CreditCard;
import posters.flows.CartCleanUpFlow;
import posters.flows.OpenHomePageFlow;
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
        var homePage = OpenHomePageFlow.flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu.validateNotLoggedIn();
        // Assure an empty cart
        homePage.miniCart.validateTotalCount(totalCount);
        homePage.miniCart.validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart.getSubtotal();

        // Go to login form
        var loginPage = homePage.userMenu.openLogin();
        loginPage.validateStructure();
        final String email = Neodymium.dataValue("email");
        final String password = Neodymium.dataValue("password");
        homePage = loginPage.sendLoginform(email, password);

        final String firstname = Neodymium.dataValue("firstname");
        homePage.validateSuccessfulLogin(firstname);

        // Go to category
        final String categoryName = homePage.topNav.getSubCategoryNameByPosition(2, 3);
        var categoryPage = homePage.topNav.clickSubCategoryByPosition(2, 3);
        categoryPage.validate(categoryName);

        // Go to product page
        final String productName = categoryPage.getProductNameByPosition(2, 1);
        var productDetailPage = categoryPage.clickProductByPosition(2, 1);
        productDetailPage.validate(productName);

        productDetailPage.addToCart("32 x 24 in", "matte");

        // Go to cart and validate
        final var product = productDetailPage.getProduct();
        final var cartPage = productDetailPage.miniCart.openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(shippingCosts);
        cartPage.miniCart.validateMiniCart(1, product);
        cartPage.miniCart.validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product);
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal, 0.00);

        // Go to shipping address and validate
        final var shippingAddressPage = cartPage.openShippingPage();
        shippingAddressPage.validateStructure();

        // Send shipping address and validate billing form
        final var billingAddressPage = shippingAddressPage.selectShippingAddress(1);
        billingAddressPage.validateStructure();

        // Send billing address and validate payment form
        final var paymentPage = billingAddressPage.selectBillingAddress(1);
        paymentPage.validateStructure();

        // setup checkout data for validation
        final var shippingAddress = DataUtils.get(Address.class);
        final var billingAddress = DataUtils.get(Address.class);
        final var creditCard = new CreditCard("John Doe", "4111111111111111", "xxxx xxxx xxxx 1111", "08", "2022");

        // Send payment data and validate place order page
        final var placeOrderPage = paymentPage.selectCreditCard(1);
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateAddressesAndPayment(shippingAddress, billingAddress, creditCard);

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
