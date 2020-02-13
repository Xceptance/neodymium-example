package posters.dataobjects;

import java.util.ArrayList;

import posters.pageobjects.utility.PriceHelper;

public class Products
{
    private ArrayList<Product> products;

    public ArrayList<Product> getProducts()
    {
        return products;
    }

    public void setProducts(ArrayList<Product> products)
    {
        this.products = products;
    }

    public String getProductsTotalPrice()
    {
        double price = 0.00;
        for (Product product : products)
        {
            price += product.getTotalPrice();
        }
        return PriceHelper.format(price);
    }

    @Override
    public String toString()
    {
        return "Products [products=" + products + "]";
    }

}
