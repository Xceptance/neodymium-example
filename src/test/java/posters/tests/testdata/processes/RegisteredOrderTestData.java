package posters.tests.testdata.processes;

import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.tests.testdata.dataobjects.User;

public class RegisteredOrderTestData
{
    private User user;
    
    private String topCategory;

    private int resultPosition;
    
    private String sizeProduct;
    
    private String styleProduct;
    
    private int amountChange;
    
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
    
    public String getTopCategory()
    {
        return topCategory;
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
    
    public int getAmountChange()
    {
        return amountChange;
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