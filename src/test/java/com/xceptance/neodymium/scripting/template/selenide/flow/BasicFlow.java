/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.flow;

import com.xceptance.neodymium.scripting.template.selenide.page.PageObject;

// TODO rename Flows
/**
 * @author pfotenhauer
 */
public interface BasicFlow<T extends PageObject>
{
    public T flow();
}
