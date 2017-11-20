package posters.neodymium.tests.smoke;

import org.junit.Test;

import com.xceptance.neodymium.multibrowser.Browser;

import posters.flows.OpenHomePageFlow;
import posters.neodymium.dataObjects.Product;
import posters.neodymium.tests.BasicTest;
import posters.pageObjects.pages.browsing.CategoryPage;
import posters.pageObjects.pages.browsing.HomePage;
import posters.pageObjects.pages.browsing.ProductdetailPage;
import posters.pageObjects.pages.checkout.CartPage;

@Browser(
{
  "Chrome_1024x768"
})
public class TAddToCart extends BasicTest
{

    @Test
    public void testAddProductsToCart()
    {
        int totalCount = 0;

        // Goto homepage
        HomePage homePage = new OpenHomePageFlow().flow();
        homePage.validate();

        homePage.miniCart().validateTotalCount(totalCount);
        homePage.miniCart().validateSubtotal("$0.00");
        final String oldSubtotal = homePage.miniCart().getSubtotal();

        // Go to a top category page
        final String topCatName = "World of Nature";
        CategoryPage categoryPage = homePage.topNav().clickCategory(topCatName);
        categoryPage.validateCategoryName(topCatName);

        // Goto sub category page
        final String categoryName = categoryPage.topNav().getSubCategoryNameByPosition(1, 1);
        categoryPage = categoryPage.topNav().clickSubCategoryByPosition(1, 1);
        categoryPage.validate(categoryName);

        // Goto product page and add to cart
        final String productName = categoryPage.getProductNameByPosition(1, 1);
        ProductdetailPage productPage = categoryPage.clickProductByPosition(1, 1);
        productPage.validate(productName);
        productPage.addToCart("16 x 12 in", "matte");

        // Goto cart and validate
        final Product product = productPage.getProduct();
        CartPage cartPage = productPage.miniCart().openCartPage();
        cartPage.validate(SHIPPINGCOSTS);
        cartPage.miniCart().validateMiniCart(1, product);
        cartPage.validateCartItem(1, product);

        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal, "$0.00");

        final String oldSubtotal2 = cartPage.miniCart().getSubtotal();
        cartPage.miniCart().validateTotalCount(++totalCount);

        // Search for product on cart page
        final String searchTerm = "pizza";
        final int searchTermExpectedCount = 1;
        categoryPage = cartPage.search().categoryPageResult(searchTerm);
        categoryPage.validateSearchHits(searchTerm, searchTermExpectedCount);
        final String productName2 = categoryPage.getProductNameByPosition(1, 1);

        // Goto product page and add to cart
        productPage = categoryPage.clickProductByPosition(1, 1);
        productPage.validate(productName2);
        productPage.addToCart("64 x 48 in", "gloss");
        final Product product2 = productPage.getProduct();

        // Goto cart and validate
        cartPage = productPage.miniCart().openCartPage();
        cartPage.validate(SHIPPINGCOSTS);

        cartPage.miniCart().validateMiniCart(1, product2);

        cartPage.miniCart().validateTotalCount(++totalCount);
        cartPage.validateCartItem(1, product2);
        cartPage.validateSubAndLineItemTotalAfterAdd(1, oldSubtotal2, "$0.00");

        int productToUpdatePosition = 1;
        int newProductAmount = 3;
        final String oldSubtotal3 = cartPage.miniCart().getSubtotal();
        Product productBeforeUpdate = cartPage.getProduct(productToUpdatePosition);

        // Update amount of product on cart page
        cartPage.updateProductCount(productToUpdatePosition, newProductAmount);
        cartPage.validateProductAmount(productToUpdatePosition, newProductAmount);

        final String newLinItemPrice = cartPage.getProductTotalUnitPrice(productToUpdatePosition);
        cartPage.validateSubAndLineItemTotalAfterAdd(productToUpdatePosition,
                                                     oldSubtotal3,
                                                     productBeforeUpdate.getTotalUnitPrice());
        cartPage.validateCartItem(1, productBeforeUpdate, newProductAmount);
        cartPage.miniCart().validateMiniCart(1, productBeforeUpdate, newProductAmount, newLinItemPrice);
        totalCount = totalCount + newProductAmount - 1;
        cartPage.miniCart().validateTotalCount(totalCount);

        final String oldLineItemTotal = cartPage.getProductTotalUnitPrice(productToUpdatePosition);
        final String oldSubTotal4 = cartPage.miniCart().getSubtotal();

        // Remove product on cart page
        cartPage.removeProduct(productToUpdatePosition);
        cartPage.validateSubAndLineItemTotalAfterRemove(oldSubTotal4, oldLineItemTotal);
        totalCount = totalCount - newProductAmount;
        cartPage.miniCart().validateTotalCount(totalCount);

        Product productFromCartPage = cartPage.getProduct(1);
        productPage = cartPage.openProductPage(1);
        productPage.validate(productFromCartPage.getName());
        productPage.addToCart(productFromCartPage.getSize(), productFromCartPage.getStyle());
        cartPage = productPage.miniCart().openCartPage();

        cartPage.validateCartItem(1, productFromCartPage, 2);
        cartPage.miniCart().validateTotalCount(++totalCount);
    }
}
