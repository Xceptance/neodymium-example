/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.component;

import static com.codeborne.selenide.Selenide.$;

/**
 * @author pfotenhauer
 */
public class CheckoutHeader extends AbstractComponent
{

    /*
     * (non-Javadoc)
     * 
     * @see com.xceptance.neodymium.scripting.template.selenide.component.BasicComponent#isComponentAvailable()
     */
    @Override
    protected boolean exists()
    {
        return $("body > header nav#headerCheckout").exists();
    }
}
