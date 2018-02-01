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

    abstract public void validateStructure();

    public void isExpectedPage()
    {
    }
}
