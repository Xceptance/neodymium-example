package posters.videorecording;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class TakeScreenshotsThread extends TimerTask
{
    private WebDriver driver;

    private volatile int screenshotCounter = 0;

    private volatile String folder;

    public TakeScreenshotsThread(WebDriver driver, String folder)
    {
        this.driver = driver;
        this.folder = folder;
    }

    @Override
    public synchronized void run()
    {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try
        {
            FileUtils.copyFile(scrFile, new File(this.folder + "/img" + (++screenshotCounter) + ".png"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
