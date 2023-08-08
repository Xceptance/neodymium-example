package posters.tests.smoke;

import org.junit.After;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.flows.CartCleanUpFlow;
import posters.flows.OpenHomePageFlow;
import posters.tests.AbstractTest;

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
        homePage.validateStructure();

        // Assure not logged in status
        homePage.userMenu.validateNotLoggedIn();

        // Assure an empty cart
        homePage.miniCart.validateTotalCount(totalCount);
        homePage.miniCart.validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart.getSubtotal();

        // Go to login form
        var loginPage = homePage.userMenu.openLoginPage();
        loginPage.validateStructure();
        final String email = Neodymium.dataValue("email");
        final String password = Neodymium.dataValue("password");
        homePage = loginPage.sendLoginForm(userData);
        final String firstName = Neodymium.dataValue("firstName");
        homePage.validateSuccessfulLogin(firstName);

        // Go to category
        final String categoryName = homePage.topNav.getSubCategoryNameByPosition(2, 3);
        var categoryPage = homePage.topNav.clickSubCategoryByPosition(2, 3);
        //categoryPage.validate(categoryName);

        // Go to product page
        final String productName = categoryPage.getProductNameByPosition(2, 1);
        var productDetailPage = categoryPage.clickProductByPosition(2, 1);
        productDetailPage.validate(productName);
        productDetailPage.addToCart("32 x 24 in", "matte");

        // Go to cart and validate
        var product = productDetailPage.getProduct();
        var cartPage = productDetailPage.miniCart.openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(shippingCosts);
        cartPage.miniCart.validateMiniCart(1, product);
        cartPage.miniCart.validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product);
        cartPage.validateTotalAfterAdd(1, oldSubtotal, 0.00);

        // Go to shipping address and validate
        var shippingAddressPage = cartPage.openShippingAddressPage();
        shippingAddressPage.validateStructure();

        // Send shipping address and validate billing form
        var billingAddressPage = shippingAddressPage.selectShippingAddress(1);
        billingAddressPage.validateStructure();

        // Send billing address and validate payment form
        var paymentPage = billingAddressPage.selectBillingAddress(1);
        paymentPage.validateStructure();

        // setup checkout data for validation
        final var shippingAddress = DataUtils.get(Address.class);
        final var billingAddress = DataUtils.get(Address.class);
        final var creditCard = DataUtils.get(CreditCard.class);

        // Send payment data and validate place order page
        var placeOrderPage = paymentPage.selectCreditCard(1);
        placeOrderPage.validateStructure();
        //placeOrderPage.validateProduct(1, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateOrderOverview(shippingAddress, billingAddress, creditCard);

        // Place order
        var OrderConfirmationPage = placeOrderPage.placeOrder();

        // Validate order confirmation on Order Confirmation Page
        OrderConfirmationPage.validateStructure();
    }

    @After
    public void after()
    {
        CartCleanUpFlow.flow();
    }
}
