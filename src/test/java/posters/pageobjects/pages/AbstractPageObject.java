package posters.pageobjects.pages;

import posters.pageobjects.components.Title;

public abstract class AbstractPageObject
{
    public Title title = new Title();

    public abstract AbstractPageObject assertExpectedPage();

    public abstract void validateStructure();
}
