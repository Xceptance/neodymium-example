package posters.dataobjects;

import com.xceptance.neodymium.util.Neodymium;

import posters.pageobjects.utility.PriceHelper;

/**
 * @author pfotenhauer
 */
public class Product
{
    String name;

    String unitPrice;

    String style;

    String size;

    int amount;

    /**
     * @param name
     *            the name to set
     * @param unitPrice
     *            the unitPrice to set
     * @param style
     *            the style to set
     * @param size
     *            the size to set
     * @param amount
     *            the amount to set
     */
    public Product(String name, String unitPrice, String style, String size, int amount)
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.style = style;
        this.size = size;
        this.amount = amount;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the unitPrice
     */
    public String getUnitPrice()
    {
        return unitPrice;
    }

    /**
     * @return the style
     */
    public String getStyle()
    {
        return style;
    }

    /**
     * @return the size
     */
    public String getSize()
    {
        return size;
    }

    /**
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

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

    private double getUnitPriceDouble()
    {
        return Double.parseDouble(PriceHelper.removeCurrency(unitPrice));
    }

    public double getTotalPrice()
    {
        return amount * getUnitPriceDouble();
    }
}
