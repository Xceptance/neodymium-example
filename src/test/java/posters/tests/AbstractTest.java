package posters.tests;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xceptance.neodymium.NeodymiumRunner;
import com.xceptance.neodymium.module.statement.browser.multibrowser.Browser;
import com.xceptance.neodymium.util.Neodymium;

import posters.flows.OpenHomePageFlow;
import posters.gifrecording.RecordGifThread;
import posters.pageobjects.pages.browsing.HomePage;

/**
 * @author pfotenhauer
 */
@RunWith(NeodymiumRunner.class)
@Browser("Chrome_1024x768")
public abstract class AbstractTest
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    protected HomePage homePage;

    private RecordGifThread recordGifThread;

    @Rule
    public TestName name = new TestName();

    @Rule
    public TestWatcher watchman = new TestWatcher()
    {
        @Override
        protected void failed(Throwable e, Description description)
        {
            recordGifThread.stopRun(true);
            try
            {
                recordGifThread.join();
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
        }

        @Override
        protected void succeeded(Description description)
        {
            recordGifThread.stopRun(recordGifThread.getConfig().appendAllGifsToReport());
            try
            {
                recordGifThread.join();
            }
            catch (InterruptedException e1)
            {
                e1.printStackTrace();
            }
        }
    };

    @Before
    public void openHomePageAndStartFilming()
    {
        homePage = OpenHomePageFlow.flow();
        recordGifThread = new RecordGifThread(Neodymium.getDriver(), name.getMethodName().replaceAll("\\s", "-").replaceAll(":", "-")
                                                                         .replaceAll("/", "|"));
        recordGifThread.start();
    }
}
