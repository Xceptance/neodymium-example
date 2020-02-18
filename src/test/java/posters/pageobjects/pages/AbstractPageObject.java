/**
 * 
 */
package posters.pageobjects.pages;

import static com.codeborne.selenide.Selenide.$;

import org.apache.commons.lang3.StringUtils;

import com.xceptance.neodymium.util.Neodymium;
import com.xceptance.neodymium.visual.ai.AI;

import posters.pageobjects.components.Title;

/**
 * @author pfotenhauer
 */
public abstract class AbstractPageObject
{
    public Title title = new Title();

    abstract public AbstractPageObject isExpectedPage();

    abstract public AbstractPageObject validateStructure();

    public AbstractPageObject validateVisual(String testCaseName)
    {
        String className = this.getClass().getSimpleName();

        if (StringUtils.isAllEmpty(testCaseName))
        {
            new AI().execute(Neodymium.getDriver(), className, "validate" + className);
        }
        else
        {
            new AI().execute(Neodymium.getDriver(), testCaseName, "validate" + className);
        }
        return this;
    }

    public AbstractPageObject validateStructureAndVisual()
    {
        validateStructureAndVisual(null);
        return this;
    }

    public AbstractPageObject validateStructureAndVisual(String testCaseName)
    {
        validateStructure();
        validateVisual(testCaseName);
        return this;
    }

    public AbstractPageObject scrollToTop()
    {
        $("body").scrollTo();
        return this;
    }
}
