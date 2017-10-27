/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.flow;

import com.xceptance.neodymium.scripting.template.selenide.page.AbstractPageObject;

/**
 * @author pfotenhauer
 */
public abstract class AbstractFlow<T extends AbstractPageObject>
{
    abstract public T flow();
}
