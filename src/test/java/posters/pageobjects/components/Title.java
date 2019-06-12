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
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }
}
