package posters.tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.common.browser.Browser;

@Browser("Chrome_1200x768")
@Browser("Firefox_1200x768")
public abstract class AbstractTest
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
}
