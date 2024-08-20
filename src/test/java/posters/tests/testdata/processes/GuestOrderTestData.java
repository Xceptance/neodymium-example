package posters.tests.testdata.processes;

import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.tests.testdata.dataobjects.Product;

public class GuestOrderTestData
{
    private String topCategory;

    private Product product;
    
    private Address shippingAddress;
    
    private Boolean sameShippingAndBillingAddress;
    
    private Address billingAddress;
    
    private CreditCard creditCard;
    
    public String getTopCategory()
    {
        return topCategory;
    }
    
    public Product getProduct()
    {
        return product;
    }

    public Address getShippingAddress() 
    {
        return shippingAddress;
    }
    
    public Boolean getSameShippingAndBillingAddress()
    {
        return sameShippingAndBillingAddress;
    }
    
    public Address getBillingAddress() 
    {
        return billingAddress;
    }
    
    public CreditCard getCreditCard() 
    {
        return creditCard;
    }

    @Override
    public String toString()
    {
        return "GuestOrderTestData [topCategory=" + topCategory + ", product=" + product + ", shippingAddress=" + shippingAddress
               + ", sameShippingAndBillingAddress=" + sameShippingAndBillingAddress + ", billingAddress=" + billingAddress + ", creditCard=" + creditCard + "]";
    }
}