package posters.cucumber.util;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.WebDriverRunner;
import com.xceptance.neodymium.multibrowser.WebDriverCache;
import com.xceptance.neodymium.multibrowser.WebDriverFactory;
import com.xceptance.neodymium.multibrowser.configuration.MultibrowserConfiguration;
import com.xceptance.neodymium.multibrowser.configuration.WebDriverProperties;

import cucumber.api.java.After;
import cucumber.api.java.en.Given;

public class Driver
{
    public static WebDriver driver;

    private static String browser;

    @Given("^The browser \"([^\"]*)\" is open$")
    public static void setUp(String browser)
    {
        Driver.browser = browser;
        // try to find appropriate web driver in cache before create a new instance
        if (MultibrowserConfiguration.getInstance().getWebDriverProperties().reuseWebDriver())
        {
            driver = WebDriverCache.getIntance().removeGetWebDriver(browser);
            if (driver != null)
            {
                driver.manage().deleteAllCookies();
            }
        }

        if (driver == null)
        {
            try
            {
                driver = WebDriverFactory.create(browser);
            }
            catch (MalformedURLException e)
            {
                throw new RuntimeException(e);
            }
            WebDriverRunner.setWebDriver(driver);
        }
    }

    // have a lower order number than default in order to shut down the driver after the test case specific after hooks
    @After(order = 100)
    public void tearDown()
    {
        WebDriverProperties webDriverProperties = MultibrowserConfiguration.getInstance().getWebDriverProperties();
        if (webDriverProperties.reuseWebDriver())
        {
            WebDriverCache.getIntance().putWebDriverForTag(Driver.browser, driver);
            driver = null;
        }
        else
        {
            if (!webDriverProperties.keepBrowserOpen() && driver != null)
            {
                driver.quit();
                driver = null;
            }
        }
    }
}
