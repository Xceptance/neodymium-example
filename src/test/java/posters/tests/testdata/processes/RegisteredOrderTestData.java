package posters.tests.testdata.processes;

import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.tests.testdata.dataobjects.Product;
import posters.tests.testdata.dataobjects.User;

public class RegisteredOrderTestData
{
    private User user;
    
    private String topCategory;

    private Product product;
    
    private Address address;
    
    private CreditCard creditCard;
    
    public User getUser() 
    {
        return user;
    }
    
    public String getTopCategory()
    {
        return topCategory;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public Address getAddress() 
    {
        return address;
    }
    
    public CreditCard getCreditCard() 
    {
        return creditCard;
    }
}