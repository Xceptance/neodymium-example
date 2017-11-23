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

import io.qameta.allure.Step;

/**
 * @author pfotenhauer
 */
@RunWith(NeodymiumRunner.class)
public class BasicTest
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(BasicTest.class);

    protected static final String SHIPPINGCOSTS = "$7.00";

    @TestData
    public Map<String, String> data;

    @Step("\"{stepName}\" step")
    public void step(String stepName)
    {
        LOGGER.trace(stepName);
    }
}
