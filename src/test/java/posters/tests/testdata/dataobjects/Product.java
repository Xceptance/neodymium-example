package posters.tests.testdata.dataobjects;

import com.xceptance.neodymium.util.Neodymium;

import posters.pageobjects.utility.PriceHelper;

public class Product
{
    String name;

    String unitPrice;

    String style;

    String size;

    int amount;

    public Product(String name, String unitPrice, String style, String size, int amount)
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.style = style;
        this.size = size;
        this.amount = amount;
    }

    // ----- get product data ----- //
    
    public String getName()
    {
        return name;
    }

    public String getUnitPrice()
    {
        return unitPrice;
    }

    public String getStyle()
    {
        return style;
    }

    public String getSize()
    {
        return size;
    }

    public int getAmount()
    {
        return amount;
    }

    // ----- get total product price ----- //
    
    private double getUnitPriceDouble()
    {
        return Double.parseDouble(PriceHelper.removeCurrency(unitPrice));
    }

    public double getTotalPrice()
    {
        return amount * getUnitPriceDouble();
    }
    
    // -------------------------------------------------------------
    
    @Override
    public String toString()
    {
        return String.format("Product [name()=%s, size()=%s, style()=%s, price()=%s]", getName(), getSize(), getStyle(), getUnitPrice(), getAmount());
    }

    @Override
    public boolean equals(Object obj)
    {
        Product other = (Product) obj;
        if (name.equals(other.name) && unitPrice.equals(other.unitPrice) && style.equals(other.style) && size.equals(other.size))
        {
            return true;
        }
        return false;
    }

    public String getRowRegex()
    {
        return getName()
               + "\\n[a-zA-Z\\s\\.\\,0-9\\!]+\\n"
               + Neodymium.localizedText("General.product.style")
               + "\\:\\s" + getStyle()
               + "\\n"
               + Neodymium.localizedText("General.product.size")
               + "\\:\\s" + getSize();
    }

    public String getCartRowRegex()
    {
        return getName()
               + "\\n"
               + Neodymium.localizedText("General.product.quantity")
               + ":\\s" + "\\d+\\s\\(" + getStyle()
               + ",\\s" + getSize()
               + "\\s\\)\\n\\$\\d+\\.\\d+";
    }
}
