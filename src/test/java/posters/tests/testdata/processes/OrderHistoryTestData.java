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
    
    private String sizeProduct16x12;
    
    private String sizeProduct32x24;
    
    private String sizeProduct64x48;
    
    private String styleProductMatte;

    private String styleProductGloss;
    
    private int updateProductAmount;
    
    private int shippingAddressPosition;
    
    private int billingAddressPosition;
    
    private int creditCardPosition;
    
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
    
    public int getResultPosition()
    {
        return resultPosition;
    }
    
    public String getsSizeProduct16x12()
    {
        return sizeProduct16x12;
    }
    
    public String getsSizeProduct32x24()
    {
        return sizeProduct32x24;
    }
    
    public String getsSizeProduct64x48()
    {
        return sizeProduct64x48;
    }
    
    public String getStyleProductMatte()
    {
        return styleProductMatte;
    }
    
    public String getStyleProductGloss()
    {
        return styleProductGloss;
    }

    public int getUpdateProductAmount()
    {
        return updateProductAmount;
    }
    
    public int getShippingAddressPosition() 
    {
        return shippingAddressPosition;
    }
    
    public int getBillingAddressPosition() 
    {
        return billingAddressPosition;
    }
    
    public int getCreditCardPosition() 
    {
        return creditCardPosition;
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