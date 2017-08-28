/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.page;

/**
 * @author pfotenhauer
 */
public interface PageObject
{

    public void validate();

    default boolean isExpectedPage()
    {
        return true;
    }
}
