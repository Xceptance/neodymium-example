package posters.pageobjects.pages;

import com.xceptance.neodymium.util.SelenideAddons;
import org.junit.jupiter.api.Assertions;
import posters.pageobjects.components.Title;

public abstract class AbstractPageObject<T extends AbstractPageObject<T>>
{
    public Title title = new Title();

    public T assertExpectedPage()
    {
        SelenideAddons.wrapAssertionError(() -> {
            Assertions.assertTrue(isExpectedPage(), "Page could not be identified");
        });
        return (T) this;
    }

    public abstract boolean isExpectedPage();

    public abstract T validateStructure();
}
