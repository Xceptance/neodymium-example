package posters.tests.testdata.dataobjects;

import java.util.List;

public class Order
{    
    List<Product> products;

    public Order(List<Product> products)
    {
        this.products = products;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    @Override
    public String toString()
    {
        return "Order [products=" + products + "]";
    }  
}