package posters.tests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.NeodymiumRunner;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.util.DataUtils;

import posters.tests.testdata.AddToCartTestData;
import posters.tests.testdata.pageobjects.components.HeaderTestData;
import posters.tests.testdata.pageobjects.components.PaginationTestData;
import posters.tests.testdata.pageobjects.components.SearchTestData;

@RunWith(NeodymiumRunner.class)
@Browser("Chrome_1024x768")
public abstract class AbstractTest
{
    protected SearchTestData searchTestData;

    protected AddToCartTestData addToCartTestData;
    
    protected HeaderTestData headerTestData;
    
    protected PaginationTestData paginationTestData;

    @Before
    public void setup()
    {
        searchTestData = DataUtils.get(SearchTestData.class);
        addToCartTestData = DataUtils.get(AddToCartTestData.class);
        headerTestData = DataUtils.get(HeaderTestData.class);
        paginationTestData = DataUtils.get(PaginationTestData.class);
    }

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
}
