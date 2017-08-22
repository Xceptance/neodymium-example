/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide;

import java.util.concurrent.TimeUnit;

import org.junit.Before;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.xceptance.neodymium.scripting.template.selenide.utility.Settings;

/**
 * @author pfotenhauer
 */
public class BasicTest
{
    @Before
    public void setBrowser()
    {
        System.setProperty(Settings.driver, Settings.driverLocation);
        Configuration.browser = Settings.browser;
        Configuration.browserSize = Settings.browserSize;
        Configuration.holdBrowserOpen = Settings.holdBrowserOpen;
        Configuration.timeout = Settings.timeout;
        WebDriverRunner.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
}
