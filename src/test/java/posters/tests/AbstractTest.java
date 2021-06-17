package posters.tests;

import java.io.IOException;

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

import posters.flows.OpenHomePageFlow;
import posters.pageobjects.pages.browsing.HomePage;
import posters.recording.FilmTestExecution;

/**
 * @author pfotenhauer
 */
@RunWith(NeodymiumRunner.class)
@Browser("Chrome_1024x768")
public abstract class AbstractTest
{
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    protected HomePage homePage;

    @Rule
    public TestName name = new TestName();

    @Rule
    public TestWatcher watchman = new TestWatcher()
    {
        @Override
        protected void failed(Throwable e, Description description)
        {
            FilmTestExecution.finishGifFilming(name.getMethodName(), true);
            FilmTestExecution.finishVideoFilming(name.getMethodName(), true);
        }

        @Override
        protected void succeeded(Description description)
        {
            FilmTestExecution.finishGifFilming(name.getMethodName(), false);
            FilmTestExecution.finishVideoFilming(name.getMethodName(), false);
        }
    };

    @Before
    public void openHomePageAndStartFilming() throws IOException, InterruptedException
    {
        FilmTestExecution.startGifRecording(name.getMethodName());
        FilmTestExecution.startVideoRecording(name.getMethodName());
        homePage = OpenHomePageFlow.flow();
    }
}