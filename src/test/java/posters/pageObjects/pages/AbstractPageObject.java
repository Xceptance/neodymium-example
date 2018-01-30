/**
 * 
 */
package posters.pageObjects.pages;

import posters.pageObjects.components.Title;

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
