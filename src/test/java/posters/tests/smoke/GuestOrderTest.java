package posters.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.Tag;
import posters.dataobjects.Address;
import posters.dataobjects.CreditCard;
import posters.flows.OpenHomePageFlow;
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
        var homePage = OpenHomePageFlow.flow();
        homePage.validateStructure();

        // Assure not logged in status
        homePage.userMenu.validateNotLoggedIn();

        // Assure an empty cart
        homePage.miniCart.validateTotalCount(totalCount);
        homePage.miniCart.validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart.getSubtotal();

        // Go to category
        final String categoryName = homePage.topNav.getSubCategoryNameByPosition(3, 2);
        var categoryPage = homePage.topNav.clickSubCategoryByPosition(3, 2);
        categoryPage.validate(categoryName);

        // Goto product page
        final String productName = categoryPage.getProductNameByPosition(1, 1);
        var productDetailPage = categoryPage.clickProductByPosition(1, 1);
        productDetailPage.validate(productName);
        productDetailPage.addToCart("64 x 48 in", "gloss");

        // Go to cart and validate
        final var product = productDetailPage.getProduct();
        var cartPage = productDetailPage.miniCart.openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(shippingCosts);
        cartPage.miniCart.validateMiniCart(1, product);
        cartPage.miniCart.validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product);
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal, 0.00);

        // setup checkout data
        final var shippingAddress = DataUtils.get(Address.class);
        final boolean sameBillingAddress = false;
        final var billingAddress = DataUtils.get(Address.class);
        final var creditCard = DataUtils.get(CreditCard.class);

        // Go to shipping address and validate
        var newShippingAddressPage = cartPage.openNewShippingPage();
        newShippingAddressPage.validateStructure();

        // Send shipping address and validate billing form
        var newBillingAddressPage = newShippingAddressPage.sendShippingAddressForm(shippingAddress, sameBillingAddress);
        newBillingAddressPage.validateStructure();

        // Send billing address and validate payment form
        var newPaymentPage = newBillingAddressPage.sendBillingAddressForm(billingAddress);
        newPaymentPage.validateStructure();

        // Send payment data and validate place order page
        var placeOrderPage = newPaymentPage.sendPaymentForm(creditCard);
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(1, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateAddressesAndPayment(shippingAddress, billingAddress, creditCard);

        // Place order
        //homePage = placeOrderPage.placeOrder();
        
        // Place Order
        var orderConfirmationPage = placeOrderPage.placeOrder();
        
        // Validate order confirmation on the Order Confirmation page
        orderConfirmationPage.validate();
        orderConfirmationPage.validateSuccessfulOrder();
        
        //navigate to the Home Page
        homePage = orderConfirmationPage.goHome();
       

        //Validate home page
        homePage.validateStructure();
    }
}
