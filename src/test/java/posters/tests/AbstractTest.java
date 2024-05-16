package posters.tests;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.common.browser.Browser;
import com.xceptance.neodymium.junit4.NeodymiumRunner;

@RunWith(NeodymiumRunner.class)
@Browser("Chrome_1200x768")
@Browser("Firefox_1200x768")
public abstract class AbstractTest
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
}
