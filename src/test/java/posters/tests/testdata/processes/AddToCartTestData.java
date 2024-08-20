package posters.tests.testdata.processes;

import posters.tests.testdata.dataobjects.Product;

public class AddToCartTestData
{
    private String topCategory;

    private String subCategory;
    
    private String searchTerm;

    private Product product1;
    
    private Product product2;

    public String getTopCategory()
    {
        return topCategory;
    }

    public String getSubCategory()
    {
        return subCategory;
    }
    
    public String getSearchTerm()
    {
        return searchTerm;
    }
    
    public Product getProduct1()
    {
        return product1;
    }
    
    public Product getProduct2()
    {
        return product2;
    }
    
    @Override
    public String toString()
    {
        return "AddToCartTestData [topCategory=" + topCategory + ", subCategory=" + subCategory + ", searchTerm=" + searchTerm + ", product1=" + product1
            + ", product2=" + product2 + "]";
    }
}