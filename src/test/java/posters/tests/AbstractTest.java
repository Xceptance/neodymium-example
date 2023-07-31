package posters.tests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.NeodymiumRunner;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.util.DataUtils;

import posters.tests.testdata.AddToCartTestData;
import posters.tests.testdata.SearchTestData;

@RunWith(NeodymiumRunner.class)
@Browser("Chrome_1024x768")
public abstract class AbstractTest
{
    protected SearchTestData searchTestData;
    
    @Before
    public void setup()
    {
        searchTestData = DataUtils.get(SearchTestData.class);
    }
    
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
}
