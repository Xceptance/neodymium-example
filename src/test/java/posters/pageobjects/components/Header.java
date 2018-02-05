package posters.pageobjects.components;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;

public class Header extends AbstractComponent
{
    public void isComponentAvailable()
    {
        $("#globalNavigation").should(exist);
    }
}
