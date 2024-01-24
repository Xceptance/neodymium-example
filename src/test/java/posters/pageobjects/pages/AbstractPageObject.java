package posters.pageobjects.pages;

import posters.pageobjects.components.Title;

public abstract class AbstractPageObject
{
    public Title title = new Title();

    public AbstractPageObject isExpectedPage()
    {
        return this;
    }

    abstract public void validateStructure();
}
