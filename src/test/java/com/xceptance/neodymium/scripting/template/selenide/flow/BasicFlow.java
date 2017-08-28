/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.flow;

import com.xceptance.neodymium.scripting.template.selenide.page.PageObject;

/**
 * @author pfotenhauer
 */
public interface BasicFlow<T extends PageObject>
{
    public T flow();
}
