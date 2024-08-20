package posters.tests.testdata.processes;

import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.tests.testdata.dataobjects.Order;
import posters.tests.testdata.dataobjects.User;

public class OrderHistoryTestData
{
    private User user;
    
    private String topCategory1;
    
    private String topCategory2;

    private String topCategory3;

    private Order order1;
    
    private Order order2;
    
    private Address address;
    
    private CreditCard creditCard;
    
    public User getUser() 
    {
        return user;
    }
    
    public String getTopCategory1()
    {
        return topCategory1;
    }
    
    public String getTopCategory2()
    {
        return topCategory2;
    }
    
    public String getTopCategory3()
    {
        return topCategory3;
    }
    
    public Order getOrder1() 
    {
        return order1;
    }
    
    public Order getOrder2() 
    {
        return order2;
    }
    
    public Address getAddress() 
    {
        return address;
    }
    
    public CreditCard getCreditCard() 
    {
        return creditCard;
    }
    
    @Override
    public String toString()
    {
        return "OrderHistoryTestData [user=" + user + ", topCategory1=" + topCategory1 + ", topCategory2=" + topCategory2 + ", topCategory3=" + topCategory3
            + ", order1=" + order1 + ", order2=" + order2 + ", address=" + address + ", creditCard=" + creditCard + "]";
    }
}