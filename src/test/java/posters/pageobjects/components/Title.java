package posters.pageobjects.components;

import static com.codeborne.selenide.Selenide.title;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.util.SelenideAddons;

import io.qameta.allure.Step;

public class Title extends AbstractComponent
{

    public void isComponentAvailable()
    {
    }

    @Step("validate that the page title matches {title}")
    public void validateTitle(String title)
    {
        SelenideAddons.wrapAssertionError(() -> {
            Assert.assertEquals(title, title());
        });

        Logger logger = LoggerFactory.getLogger(getClass());
        logger.trace("This is a logging example with severity: 'trace'. Can be found in posters.pageobjects.components.Title class.");
        logger.debug("This is a logging example with severity: 'debug'. Can be found in posters.pageobjects.components.Title class.");
        logger.info("This is a logging example with severity: 'info'. Can be found in posters.pageobjects.components.Title class.");
        logger.warn("This is a logging example with severity: 'warn'. Can be found in posters.pageobjects.components.Title class.");
        logger.error("This is a logging example with severity: 'error'. Can be found in posters.pageobjects.components.Title class.");
    }
}
