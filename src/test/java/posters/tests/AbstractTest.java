/**
 * 
 */
package posters.tests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Configuration;
import com.xceptance.neodymium.NeodymiumRunner;
import com.xceptance.neodymium.multibrowser.Browser;
import com.xceptance.neodymium.util.Context;

import io.qameta.allure.Step;

/**
 * @author pfotenhauer
 */
@RunWith(NeodymiumRunner.class)
@Browser(
    {
        "Chrome_1024x768"
    })
public class AbstractTest
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    /**
     * Build it up and set the basic timeouts
     */
    @Before
    public void before()
    {
        Configuration.timeout = Context.get().configuration.timeout();
        Configuration.collectionsTimeout = 2 * Configuration.timeout;
    }

    @Step("\"{stepName}\" step")
    public void step(String stepName)
    {
        LOGGER.trace(stepName);
    }
}
