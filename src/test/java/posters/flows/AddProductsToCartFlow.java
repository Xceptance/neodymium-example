package posters.flows;

import posters.dataobjects.Product;
import posters.dataobjects.Products;
import posters.pageobjects.pages.browsing.ProductdetailPage;

public class AddProductsToCartFlow
{
    public static ProductdetailPage addToCart(Products products)
    {
        ProductdetailPage pdp = new ProductdetailPage();
        for (Product product : products.getProducts())
        {
            pdp = OpenPageFlow.openProductPageWithCookies(product.getRelativeUrl());
            pdp.isExpectedPage();
            pdp.addToCart(product.getSize(), product.getStyle());
        }
        return pdp;
    }
}
