/**
 * 
 */
package posters.dataObjects;

/**
 * @author pfotenhauer
 */
public class Product
{
    String name;

    String unitPrice;

    String totalUnitPrice;

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
    public Product(String name, String unitPrice, String totalUnitPrice, String style, String size, int amount)
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.totalUnitPrice = totalUnitPrice;
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
     * @return the price
     */
    public String getUnitPrice()
    {
        return unitPrice;
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
     * @return the totalUnitPrice
     */
    public String getTotalUnitPrice()
    {
        return totalUnitPrice;
    }

    /**
     * @param totalUnitPrice
     *            the totalUnitPrice to set
     */
    public void setTotalUnitPrice(String totalUnitPrice)
    {
        this.totalUnitPrice = totalUnitPrice;
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
     * @return the amount
     */
    public int getAmount()
    {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(int amount)
    {
        this.amount = amount;
    }
}
