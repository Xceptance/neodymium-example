/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.scripting.template.selenide.flow.OpenHomePageFlow;
import com.xceptance.neodymium.scripting.template.selenide.objects.Address;
import com.xceptance.neodymium.scripting.template.selenide.objects.CreditCard;
import com.xceptance.neodymium.scripting.template.selenide.objects.Product;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.CategoryPage;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.HomePage;
import com.xceptance.neodymium.scripting.template.selenide.page.browsing.ProductdetailPage;
import com.xceptance.neodymium.scripting.template.selenide.page.checkout.CartPage;
import com.xceptance.neodymium.scripting.template.selenide.page.checkout.NewBillingAddressPage;
import com.xceptance.neodymium.scripting.template.selenide.page.checkout.NewPaymentPage;
import com.xceptance.neodymium.scripting.template.selenide.page.checkout.NewShippingAddressPage;
import com.xceptance.neodymium.scripting.template.selenide.page.checkout.PlaceOrderPlace;
import com.xceptance.neodymium.scripting.template.selenide.tests.BasicTest;

/**
 * @author pfotenhauer
 */
@Browser(
{
  "Chrome_1024x768"
})
public class TGuestOrder extends BasicTest
{
    @Test
    public void test()
    {
        // TODO put in place of first usage
        // Page types to use
        HomePage homePage;
        CategoryPage categoryPage;
        ProductdetailPage productPage;
        CartPage cartPage;
        NewShippingAddressPage shippingAddressPage;
        NewBillingAddressPage billingAddressPage;
        NewPaymentPage paymentPage;
        PlaceOrderPlace placeOrderPage;

        // total product count will be updated throughout the test
        int totalCount = 0;

        // Goto homepage
        homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        // Assure not logged in status
        homePage.userMenu().validateNotLoggedIn();
        // Assure an empty cart
        homePage.miniCart().validateTotalCount(totalCount);
        homePage.miniCart().validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart().getSubtotal();

        // Goto category
        final String categoryName = homePage.topNav().getSubCategoryNameByIndex(3, 2);
        categoryPage = homePage.topNav().clickSubCategoryByIndex(3, 2);
        categoryPage.validate(categoryName);

        // Goto product page
        final String productName = categoryPage.getProductNameByPosition(1, 1);
        productPage = categoryPage.clickProductByPosition(1, 1);
        productPage.validate(productName);

        productPage.addToCart("64 x 48 in", "gloss");

        // Goto cart and validate
        final Product product = productPage.getProduct();
        cartPage = productPage.miniCart().openCartPage();
        cartPage.validateStructure();
        cartPage.validateShippingCosts(SHIPPINGCOSTS);
        cartPage.miniCart().validateMiniCart(1, product);
        cartPage.miniCart().validateTotalCount(++totalCount);
        cartPage.validateCartItem(0, product);
        cartPage.validateSubAndLineItemTotalAfterAdd(0, oldSubtotal, "$0.00");

        // setup checkout data
        final Address shippingAddress = new Address("Jimmy Blue", "Ochsenknecht Records", "6 Wall St", "Burlington", "Massachusetts", "01803", "United States");
        final boolean sameBillingAddress = false;
        final Address billingAddress = new Address("Jimmy Blue", "Ochsenknecht Records", "6 Wall St", "Burlington", "Massachusetts", "01803", "United States");
        final CreditCard creditcard = new CreditCard("Jimmy Blue", "4111111111111111", "xxxx xxxx xxxx 1111", "04", "2018");
        // Goto shipping address and validate
        shippingAddressPage = cartPage.openNewShippingPage();
        shippingAddressPage.validateStructure();

        // Send shipping address and validate billing form
        billingAddressPage = shippingAddressPage.sendShippingAddressForm(shippingAddress, sameBillingAddress);
        billingAddressPage.validateStructure();

        // Send billing address and validate payment form
        paymentPage = billingAddressPage.sendBillingAddressForm(billingAddress);
        paymentPage.validateStructure();

        // Send payment data and validate place order page
        placeOrderPage = paymentPage.sendPaymentForm(creditcard);
        placeOrderPage.validateStructure();
        placeOrderPage.validateProduct(0, product.getName(), product.getAmount(), product.getStyle(), product.getSize());
        placeOrderPage.validateAddressAndPayment(shippingAddress, billingAddress, creditcard);

        // Place order
        homePage = placeOrderPage.placeOrder();
        // Validate oorder confirmation on Homepage
        homePage.validate();
        homePage.validateSuccessfulOrder();
    }
}
