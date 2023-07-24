package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class CheckoutHeader extends AbstractComponent
{
    public void isComponentAvailable()
    {
        $("#headerCheckout").should(exist);
    }
}
