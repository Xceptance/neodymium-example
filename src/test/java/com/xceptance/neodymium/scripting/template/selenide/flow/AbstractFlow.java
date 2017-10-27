/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.flow;

import com.xceptance.neodymium.scripting.template.selenide.page.PageObject;

/**
 * @author pfotenhauer
 */
public abstract class AbstractFlow<T extends PageObject>
{
    abstract public T flow();
}
