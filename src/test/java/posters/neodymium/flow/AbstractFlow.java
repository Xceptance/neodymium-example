/**
 * 
 */
package posters.neodymium.flow;

import posters.pageObjects.pages.AbstractPageObject;

/**
 * @author pfotenhauer
 */
public abstract class AbstractFlow<T extends AbstractPageObject>
{
    abstract public T flow();
}
