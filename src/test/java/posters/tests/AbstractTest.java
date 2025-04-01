package posters.tests;

import com.xceptance.neodymium.common.browser.Browser;
import com.xceptance.neodymium.util.DataUtils;
import com.xceptance.neodymium.util.Neodymium;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.commons.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Browser("Chrome_1200x768")
@Browser("Firefox_1200x768")
public abstract class AbstractTest
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    @BeforeEach
    public void setUpLocaleAndSite()
    {
        // get the locale from the test data
        String locale = DataUtils.asString("locale", "");
        if (StringUtils.isBlank(locale))
        {
            locale = "en_US";
            // or "default"?
        }

        Neodymium.configuration().setProperty("neodymium.locale", locale);
    }
}
