/**
 * 
 */
package posters.pageobjects.pages;

import static com.codeborne.selenide.Selenide.$;

import org.apache.commons.lang3.StringUtils;

import com.xceptance.neodymium.util.Context;
import com.xceptance.neodymium.visual.ai.AI;

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

    public void validateVisual(String testCaseName)
    {
        if (StringUtils.isAllEmpty(testCaseName))
        {
            new AI().execute(Context.get().driver, this.getClass().getSimpleName(), "validate" + this.getClass().getSimpleName());
        }
        else
        {
            new AI().execute(Context.get().driver, testCaseName, "validate" + this.getClass().getSimpleName());
        }
    }

    public void validateStructureAndVisual()
    {
        validateStructureAndVisual(null);
    }

    public void validateStructureAndVisual(String testCaseName)
    {
        validateStructure();
        validateVisual(testCaseName);
    }

    public void scrollToTop()
    {
        $("body").scrollTo();
    }
}
