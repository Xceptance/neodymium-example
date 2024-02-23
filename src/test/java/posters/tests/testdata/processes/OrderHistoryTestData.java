package posters.tests.testdata.processes;

import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.tests.testdata.dataobjects.User;

public class OrderHistoryTestData
{
    private User user;
    
    private String topCategory1;
    
    private String topCategory2;

    private String topCategory3;

    private int resultPosition;
    
    private String sizeProduct;
    
    private String styleProduct;
    
    private int shipAddrPos;
    
    private int billAddrPos;
    
    private int creditCardPos;
    
    private Boolean shipAddrEqualBillAddr;
    
    private Address shippingAddress;
    
    private Address billingAddress;
    
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
    
    public int getResultPosition()
    {
        return resultPosition;
    }
    
    public String getsSizeProduct()
    {
        return sizeProduct;
    }
    
    public String getStyleProduct()
    {
        return styleProduct;
    }
    
    public int getShipAddrPos() 
    {
        return shipAddrPos;
    }
    
    public int getBillAddrPos() 
    {
        return billAddrPos;
    }
    
    public int getCreditCardPos() 
    {
        return creditCardPos;
    }
    
    public Boolean getShipAddrEqualBillAddr()
    {
        return shipAddrEqualBillAddr;
    }
    
    public Address getShippingAddress() 
    {
        return shippingAddress;
    }
    
    public Address getBillingAddress() 
    {
        return billingAddress;
    }
    
    public CreditCard getCreditCard() 
    {
        return creditCard;
    }
}