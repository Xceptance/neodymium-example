package posters.tests.newTests.cart;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.Products;
import posters.flows.AddProductsToCartFlow;
import posters.flows.OpenPageFlow;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.tests.AbstractTest;

public class MiniCartTest extends AbstractTest
{
    private Products products;

    @Before
    public void dataSetup()
    {
        products = DataUtils.get(Products.class);
    }

    @Test
    public void testAddToMiniCart()
    {
        OpenPageFlow.openHomePage();
        ProductdetailPage pdp = AddProductsToCartFlow.addToCart(products);
        pdp.miniCart.openMiniCart();
        products.getProducts().forEach(product -> pdp.miniCart.validateMiniCartByProduct(product));
        pdp.miniCart.validateSubtotal(products.getProductsTotalPrice());
    }
}