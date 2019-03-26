/**
 * 
 */
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
     * @param unitPrice
     * @param totalUnitPrice
     * @param style
     * @param size
     * @param amount
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
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setUnitPrice(String price)
    {
        this.unitPrice = price;
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
     * @param style
     *            the style to set
     */
    public void setStyle(String style)
    {
        this.style = style;
    }

    /**
     * @return the size
     */
    public String getSize()
    {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(String size)
    {
        this.size = size;
    }

    /**
     * @return the size
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    @Override
    public String toString()
    {
        return String.format("Product [name()=%s, size()=%s, style()=%s, price()=%s]", getName(), getSize(), getStyle(), getUnitPrice(), getAmount());
    }

    @Override
    public boolean equals(Object obj)
    {
    	if(!this.getClass().isInstance(obj))
    	{
    		System.out.println("Found incompatible type for comparison");
    		return false;
    	}
    	
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
