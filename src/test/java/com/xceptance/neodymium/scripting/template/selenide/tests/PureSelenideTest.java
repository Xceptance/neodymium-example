/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.tests;

import org.junit.Before;

import com.codeborne.selenide.Configuration;

/**
 * @author pfotenhauer
 */
public class PureSelenideTest
{
    static final String SHIPPINGCOSTS = "$7.00";

    private static final String _driver = "webdriver.chrome.driver";

    private static final String _driverLocation = "/usr/local/Cellar/chromedriver/2.31/bin/chromedriver";

    private static final String _browser = "chrome";

    private static final String _browserSize = "800x600";

    private static boolean _holdBrowserOpen = false;

    @Before
    public void setBrowser()
    {
        System.setProperty(_driver, _driverLocation);
        Configuration.browser = _browser;
        Configuration.browserSize = _browserSize;
        Configuration.holdBrowserOpen = _holdBrowserOpen;
        Configuration.timeout = 1500;
    }
}
