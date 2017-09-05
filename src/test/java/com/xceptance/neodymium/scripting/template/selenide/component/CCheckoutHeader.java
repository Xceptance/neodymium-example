/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author pfotenhauer
 */
public class CCheckoutHeader extends BasicComponent
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.component.BasicComponent#isComponentAvailable()
     */
    @Override
    protected boolean isComponentAvailable()
    {
        return $("body > header nav#headerCheckout").exists();
    }
}
