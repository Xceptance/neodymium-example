package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

import io.qameta.allure.Step;

public class CheckoutHeader extends AbstractComponent
{
    @Override
    @Step("ensure availability checkout header")
    public void isComponentAvailable()
    {
        $("#headerCheckout").should(exist);
    }
}
