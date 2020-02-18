package posters.tests.newTests.cart;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.Products;
import posters.flows.AddProductsToCartFlow;
import posters.flows.OpenPageFlow;
import posters.pageobjects.pages.browsing.CartPage;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.tests.AbstractTest;

public class CartTest extends AbstractTest
{
    private Products products;

    @Before
    public void dataSetup()
    {
        products = DataUtils.get(Products.class);
    }

    @Test
    public void testAddToCart()
    {
        OpenPageFlow.openHomePage();
        ProductdetailPage pdp = AddProductsToCartFlow.addToCart(products);
        CartPage cartPage = pdp.miniCart.openCartPage();
        products.getAll().forEach(product -> cartPage.validateContainsProduct(product));
        cartPage.validateSubtotal(products.getProductsTotalPrice());
    }

    @Test
    public void testUpdateQuantity()
    {
        OpenPageFlow.openHomePage();
        ProductdetailPage pdp = AddProductsToCartFlow.addToCart(products);
        CartPage cartPage = pdp.miniCart.openCartPage();
        products.getAll().forEach(product -> {
            cartPage.updateProductCountByName(product, 2);
            product.setAmount(2);
            cartPage.validateContainsProduct(product);
            cartPage.validateSubtotal(products.getProductsTotalPrice());
        });
    }

    @Test
    public void testDeleteProductFromCart()
    {
        OpenPageFlow.openHomePage();
        ProductdetailPage pdp = AddProductsToCartFlow.addToCart(products);
        CartPage cartPage = pdp.miniCart.openCartPage();
        products.getAll().forEach(product -> {
            cartPage.removeProductByName(product);
        });
        cartPage.validateIsEmpty();
    }
}
