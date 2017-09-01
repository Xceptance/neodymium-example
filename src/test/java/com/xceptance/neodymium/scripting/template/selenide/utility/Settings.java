/**
 * 
 */
package com.xceptance.neodymium.scripting.template.selenide.utility;

/**
 * @author pfotenhauer
 */
public class Settings
{

    // TODO implement in neodymium libary
    public static final long timeout = 1500;

    @Deprecated
    public static final String driver = "webdriver.chrome.driver";

    @Deprecated
    public static final String driverLocation = "/usr/local/Cellar/chromedriver/2.31/bin/chromedriver";

    @Deprecated
    public static final String browser = "chrome";

    @Deprecated
    public static final String browserSize = "800x600";

    @Deprecated
    public static boolean holdBrowserOpen = false;

    public static final boolean DEBUG = true;
}
