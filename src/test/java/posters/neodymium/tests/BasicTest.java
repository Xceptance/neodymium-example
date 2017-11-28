/**
 * 
 */
package posters.neodymium.tests;

import java.util.Map;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.NeodymiumRunner;
import com.xceptance.neodymium.TestData;
import com.xceptance.neodymium.multibrowser.Browser;

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

    @TestData
    public Map<String, String> data;

    @Step("\"{stepName}\" step")
    public void step(String stepName)
    {
        LOGGER.trace(stepName);
    }
}
