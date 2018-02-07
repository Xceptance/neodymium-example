/**
 * 
 */
package posters.pageobjects.pages;

import posters.pageobjects.components.Title;

/**
 * @author pfotenhauer
 */
public abstract class AbstractPageObject
{
    public Title title = new Title();

    public void isExpectedPage()
    {
    }

    abstract public void validateStructure();
}
