package posters.pageobjects.pages;

import static com.codeborne.selenide.Selenide.$;

import posters.pageobjects.components.Title;

public abstract class AbstractPageObject
{
    public Title title = new Title();

    public AbstractPageObject isExpectedPage()
    {
        return this;
    }

    abstract public void validateStructure();

    public void scrollToTop()
    {
        $("body");
    }
}
