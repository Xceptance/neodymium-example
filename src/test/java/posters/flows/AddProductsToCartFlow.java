package posters.flows;

import posters.dataobjects.Product;
import posters.dataobjects.Products;
import posters.pageobjects.pages.browsing.ProductDetailPage;

public class AddProductsToCartFlow
{
    public static ProductDetailPage addToCart(Products products)
    {
        ProductDetailPage pdp = new ProductDetailPage();
        for (Product product : products.getAll())
        {
            pdp = OpenPageFlow.openProductPageWithCookies(product.getRelativeUrl());
            pdp.isExpectedPage();
            pdp.addToCart(product.getSize(), product.getStyle());
        }
        return pdp;
    }
}
