package posters.dataobjects;

import posters.pageobjects.utility.PriceHelper;

public class OrderData
{
    private Products products;

    private Address shippingAddress;

    private Address billingAddress;

    private CreditCard payment;

    private String shippingCost;

    private String orderTaxRate;

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

    public Address getShippingAddress()
    {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress)
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

    @Override
    public String toString()
    {
        return "OrderData [products=" + products + ", shippingAddress=" + shippingAddress + ", billingAddress=" + billingAddress + ", payment=" + payment
               + ", shippingCost=" + shippingCost + "]";
    }

}
