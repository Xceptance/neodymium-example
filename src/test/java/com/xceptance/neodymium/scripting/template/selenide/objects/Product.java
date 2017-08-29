/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.objects;

/**
 * @author pfotenhauer
 */
public class Product
{
    String name;

    String price;

    String style;

    String size;

    /**
     * 
     */
    public Product(String name, String price, String style, String size)
    {
        this.name = name;
        this.price = price;
        this.style = style;
        this.size = style;
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
    public String getPrice()
    {
        return price;
    }

    /**
     * @param price
     *            the price to set
     */
    public void setPrice(String price)
    {
        this.price = price;
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
}
