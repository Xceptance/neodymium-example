package posters.pageobjects.components;

import com.xceptance.neodymium.util.SelenideAddons;
import io.qameta.allure.Step;
import org.junit.Assert;

import static com.codeborne.selenide.Selenide.title;

public class Title extends AbstractComponent<Title>
{
    @Override
    @Step("check availability of title")
    public Title assertComponentAvailable()
    {
        return super.assertComponentAvailable();
    }

    @Override
    @Step("check availability of title")
    public boolean isComponentAvailable()
    {
        return true;
    }

    @Step("validate that the page title matches {title}")
    public void validateTitle(String title)
    {
        SelenideAddons.wrapAssertionError(() -> {
            Assert.assertEquals(title, title());
        });
    }
}
