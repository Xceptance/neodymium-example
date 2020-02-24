package posters.dataobjects;

import posters.pageobjects.utility.PriceHelper;

public class OrderData
{
    private Products products;

    private ShippingAddress shippingAddress;

    private Address billingAddress;

    private CreditCard payment;

    private String shippingCost;

    private String orderTaxRate;

    private ShippingAddress secondShippingAddress;

    private Address secondBillingAddress;

    private CreditCard secondPayment;

    public String getOrderTotal()
    {
        double shippingCost = Double.parseDouble(PriceHelper.removeCurrency(this.shippingCost));
        double orderSubtotal = Double.parseDouble(PriceHelper.removeCurrency(products.getProductsTotalPrice()));
        return PriceHelper.format(shippingCost + orderSubtotal + getOrderTax());
    }

    public double getOrderTax()
    {
        double shippingCost = Double.parseDouble(PriceHelper.removeCurrency(this.shippingCost));
        double orderTaxRate = Double.parseDouble(this.orderTaxRate);
        double orderSubtotal = Double.parseDouble(PriceHelper.removeCurrency(products.getProductsTotalPrice()));
        return orderTaxRate * (shippingCost + orderSubtotal) * 0.01;
    }

    public Products getProducts()
    {
        return products;
    }

    public void setProducts(Products products)
    {
        this.products = products;
    }

    public ShippingAddress getShippingAddress()
    {
        this.shippingAddress.setUseForBilling(getBillingAddress() == null);
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress()
    {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress)
    {
        this.billingAddress = billingAddress;
    }

    public CreditCard getPayment()
    {
        return payment;
    }

    public void setPayment(CreditCard payment)
    {
        this.payment = payment;
    }

    public String getShippingCost()
    {
        return shippingCost;
    }

    public void setShippingCost(String shippingCosts)
    {
        this.shippingCost = shippingCosts;
    }

    public String getOrderTaxRate()
    {
        return orderTaxRate;
    }

    public void setOrderTaxRate(String orderTaxRate)
    {
        this.orderTaxRate = orderTaxRate;
    }

    public ShippingAddress getSecondShippingAddress()
    {
        return secondShippingAddress;
    }

    public void setSecondShippingAddress(ShippingAddress secondShippingAddress)
    {
        this.secondShippingAddress = secondShippingAddress;
    }

    public Address getSecondBillingAddress()
    {
        return secondBillingAddress;
    }

    public void setSecondBillingAddress(Address secondBillingAddress)
    {
        this.secondBillingAddress = secondBillingAddress;
    }

    public CreditCard getSecondPayment()
    {
        return secondPayment;
    }

    public void setSecondPayment(CreditCard secondPayment)
    {
        this.secondPayment = secondPayment;
    }

    public void makeSecondAddressesToMain()
    {
        if (secondShippingAddress != null)
        {
            secondShippingAddress.setSavedInAccount(true);
            this.shippingAddress = secondShippingAddress;

        }
        if (secondBillingAddress != null)
        {
            secondBillingAddress.setSavedInAccount(true);
            this.billingAddress = secondBillingAddress;

        }
        if (secondPayment != null)
        {
            secondPayment.setSavedInAccount(true);
            this.payment = secondPayment;
        }
    }

    @Override
    public String toString()
    {
        return "OrderData [products=" + products + ", shippingAddress=" + shippingAddress + ", billingAddress=" + billingAddress + ", payment=" + payment
               + ", shippingCost=" + shippingCost + "]";
    }

}
