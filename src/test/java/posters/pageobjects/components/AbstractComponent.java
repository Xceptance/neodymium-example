package posters.pageobjects.components;

import static com.codeborne.selenide.Selenide.actions;

import com.codeborne.selenide.SelenideElement;

public abstract class AbstractComponent
{
    abstract public void isComponentAvailable();
    
    // move mouse cursor to specific {selector}
    public void moveCursor(SelenideElement selector) 
    {
        actions().moveToElement(selector).perform();
    }
}
