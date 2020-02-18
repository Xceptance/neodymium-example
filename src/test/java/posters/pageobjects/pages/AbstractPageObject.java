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

    abstract public <T extends AbstractPageObject> T isExpectedPage();

    abstract public <T extends AbstractPageObject> T validateStructure();

    public <T extends AbstractPageObject> T validateVisual(String testCaseName)
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
        return (T) this;
    }

    public <T extends AbstractPageObject> T validateStructureAndVisual()
    {
        validateStructureAndVisual(null);
        return (T) this;
    }

    public <T extends AbstractPageObject> T validateStructureAndVisual(String testCaseName)
    {
        validateStructure();
        validateVisual(testCaseName);
        return (T) this;
    }

    public <T extends AbstractPageObject> T scrollToTop()
    {
        $("body").scrollTo();
        return (T) this;
    }
}
