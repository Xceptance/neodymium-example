package posters.recording;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.xceptance.neodymium.util.AllureAddons;

import io.qameta.allure.Allure;
import posters.recording.config.RecordingConfigurations;
import posters.recording.writers.Writer;

public class TakeScreenshotsThread extends Thread
{
    private WebDriver driver;

    private String fileName;

    private boolean run = true;

    private boolean testFailed = true;

    private RecordingConfigurations recordingConfigurations;

    private Writer writer;

    public TakeScreenshotsThread(WebDriver driver, Class<? extends Writer> writerClass, RecordingConfigurations recordingConfigurations,
        String testName) throws IOException
    {
        this.recordingConfigurations = recordingConfigurations;
        fileName = recordingConfigurations.tempFolderToStoreRecoring()
                   + testName.replaceAll("\\s", "-").replaceAll(":", "-").replaceAll("/", "_") + "." + recordingConfigurations.format();
        this.writer = Writer.instanciate(writerClass, recordingConfigurations, fileName);
        File directory = new File(recordingConfigurations.tempFolderToStoreRecoring());
        if (!directory.exists())
        {
            directory.mkdir();
        }
        this.driver = driver;
    }

    @Override
    public synchronized void run()
    {
        if (writer != null)
        {
            try
            {
                writer.start();

                long turns = 0;
                long millis = 0;
                while (run)
                {
                    long start = new Date().getTime();

                    File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    boolean isResizeNeeded = recordingConfigurations.imageScaleFactor() != 1.0;
                    boolean isCompressionNeeded = recordingConfigurations.imageQuality() != 1.0;
                    if (isResizeNeeded || isCompressionNeeded)
                    {
                        try
                        {
                            BufferedImage img = ImageIO.read(file);
                            if (isResizeNeeded)
                            {
                                img = writer.resizeImage(img, recordingConfigurations.imageScaleFactor());
                            }
                            if (isCompressionNeeded)
                            {
                                img = writer.compressImage(img, recordingConfigurations.imageQuality());
                            }
                            ImageIO.write(img, "jpg", file);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    writer.write(file);

                    long duration = new Date().getTime() - start;
                    millis += duration;
                    turns++;
                    long sleep = recordingConfigurations.oneImagePerMilliseconds() - duration;
                    try
                    {
                        Thread.sleep(sleep > 0 ? sleep : 0);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                }
                AllureAddons.addToReport("avarage videos sequence recording creation duration = " + millis + " / " + turns + "="
                                         + millis / turns, "");
                writer.stop();
                try
                {
                    if (recordingConfigurations.appendAllRecordingsToReport() || testFailed)
                    {

                        String type = recordingConfigurations.format().equals("mp4") ? "video/mp4" : "image/gif";
                        Allure.addAttachment(fileName, type, new FileInputStream(fileName), recordingConfigurations.format());

                    }
                    if (recordingConfigurations.deleteRecordingsAfterAddingToAllureReport())
                    {
                        new File(fileName).delete();
                        // FileUtils.deleteDirectory(new File(recordingConfigurations.tempFolderToStoreRecoring()));
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
    }

    public void stopRun(boolean testFailed)
    {
        this.testFailed = testFailed;
        run = false;
    }
}
