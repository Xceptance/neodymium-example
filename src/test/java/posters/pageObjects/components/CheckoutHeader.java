/**
 * 
 */
package posters.pageObjects.components;

import static com.codeborne.selenide.Condition.exist;
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
    public void isComponentAvailable()
    {
        $("body > header nav#headerCheckout").should(exist);
    }
}
