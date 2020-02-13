package posters.tests.newTests.smoke;

import org.junit.Before;
import org.junit.Test;

import com.xceptance.neodymium.util.DataUtils;

import posters.dataobjects.Product;
import posters.flows.OpenPageFlow;
import posters.pageobjects.pages.browsing.ProductdetailPage;
import posters.tests.AbstractTest;

public class ProductPageTest extends AbstractTest
{
    private Product product;

    @Before
    public void dataSetup()
    {
        product = DataUtils.get(Product.class);
    }

    @Test
    public void testProductPage()
    {
        ProductdetailPage pdp = OpenPageFlow.openProductPage(product.getRelativeUrl());
        pdp.validateStructure();
        pdp.validate(product.getName());

        pdp.setSize(product.getSize());
        pdp.validateSelectedSize(product.getSize());

        pdp.setStyle(product.getStyle());
        pdp.validateSelectedStyle(product.getStyle());

        pdp.validateUnitPrice(product.getUnitPrice());
    }
}
