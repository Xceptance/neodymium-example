package posters.tests;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.NeodymiumRunner;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.util.DataUtils;

import posters.tests.testdata.processes.AddToCartTestData;
import posters.tests.testdata.processes.BrowseTestData;
import posters.tests.testdata.processes.GuestOrderTestData;
import posters.tests.testdata.processes.RegisteredOrderTestData;
import posters.tests.testdata.dataobjects.Address;
import posters.tests.testdata.dataobjects.CreditCard;
import posters.tests.testdata.dataobjects.Product;
import posters.tests.testdata.dataobjects.User;
import posters.tests.testdata.pageobjects.components.HeaderTestData;
import posters.tests.testdata.pageobjects.components.PaginationTestData;
import posters.tests.testdata.pageobjects.components.SearchTestData;

@RunWith(NeodymiumRunner.class)
@Browser("Chrome_1024x768")
public abstract class AbstractTest
{
    /// ----- dataobjects ----- ///
    
    protected Address addressData;

    protected CreditCard creditCardData;

    protected Product productData;

    protected User userData;

    /// ----- components ----- ///

    protected HeaderTestData headerTestData;

    protected PaginationTestData paginationTestData;

    protected SearchTestData searchTestData;

    /// ----- processes ----- ///

    protected AddToCartTestData addToCartTestData;

    protected BrowseTestData browseTestData;

    protected GuestOrderTestData guestOrderTestData;
    
    protected RegisteredOrderTestData registeredOrderTestData;

    @Before
    public void setup()
    {
        // dataobjects
        addressData = DataUtils.get(Address.class);
        creditCardData = DataUtils.get(CreditCard.class);
        productData = DataUtils.get(Product.class);
        userData = DataUtils.get(User.class);

        // components
        headerTestData = DataUtils.get(HeaderTestData.class);
        paginationTestData = DataUtils.get(PaginationTestData.class);
        searchTestData = DataUtils.get(SearchTestData.class);

        // processes
        addToCartTestData = DataUtils.get(AddToCartTestData.class);
        browseTestData = DataUtils.get(BrowseTestData.class);
        guestOrderTestData = DataUtils.get(GuestOrderTestData.class);
        registeredOrderTestData = DataUtils.get(RegisteredOrderTestData.class);
    }

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);
}
