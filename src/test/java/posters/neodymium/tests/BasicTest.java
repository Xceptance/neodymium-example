/**
 * 
 */
package posters.neodymium.tests;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Configuration;
import com.xceptance.neodymium.NeodymiumRunner;
import com.xceptance.neodymium.TestData;
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
public class BasicTest
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasicTest.class);

    // reads the test data from test data file, can't be final
    // because it needs to be readable
    @TestData
    public Map<String, String> data = new HashMap<>();

    /**
     * Build it up and set the basic timeouts
     */
    @Before
    public void before()
    {
        Context.create(data);
        Context.get().driver = getWebDriver();

        Configuration.timeout = Context.get().configuration.timeout();
        Configuration.collectionsTimeout = 2 * Configuration.timeout;
    }

    @Step("\"{stepName}\" step")
    public void step(String stepName)
    {
        LOGGER.trace(stepName);
    }
}
