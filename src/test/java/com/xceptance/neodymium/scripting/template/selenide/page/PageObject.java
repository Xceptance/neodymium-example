/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

/**
 * @author pfotenhauer
 */
public interface PageObject
{
    public void validateStructure();

    default boolean isExpectedPage()
    {
        return true;
    }
}
