package posters.pageobjects.components;

import static com.codeborne.selenide.Selenide.title;

import org.junit.Assert;

public class Title extends AbstractComponent
{

    public void isComponentAvailable()
    {
    }

    public void validateTitle(String title)
    {
        Assert.assertEquals(title, title());
    }
}
