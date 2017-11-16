/**
 * 
 */
package posters.flows;

import posters.pageObjects.pages.AbstractPageObject;

/**
 * @author pfotenhauer
 */
public abstract class AbstractFlow<T extends AbstractPageObject>
{
    abstract public T flow();
}
