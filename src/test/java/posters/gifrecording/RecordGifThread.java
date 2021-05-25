package posters.gifrecording;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.xceptance.neodymium.util.AllureAddons;

import io.qameta.allure.Allure;

public class RecordGifThread extends Thread
{
    private boolean run = true;

    private boolean save;

    private WebDriver driver;

    private String giftName;

    private final GifRecodingConfigurations CONFIGURATION;

    public RecordGifThread(WebDriver driver, String gifName)
    {
        this.CONFIGURATION = ConfigFactory.create(GifRecodingConfigurations.class);
        this.driver = driver;
        this.giftName = gifName;
    }

    public GifRecodingConfigurations getConfig()
    {
        return CONFIGURATION;
    }

    @Override
    public void run()
    {
        File directory = new File(CONFIGURATION.tempFolderToStoreGifs());
        if (!directory.exists())
        {
            directory.mkdir();
        }
        File gif = new File(CONFIGURATION.tempFolderToStoreGifs() + giftName);
        try (ImageOutputStream output = new FileImageOutputStream(gif);
            GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_4BYTE_ABGR, CONFIGURATION.oneImagePerMilliseconds(), CONFIGURATION.imageQuality(), CONFIGURATION.imageScaleFactor(), CONFIGURATION.loop()))
        {
            long turns = 0;
            long millis = 0;
            long millisScreen = 0;
            while (run)
            {
                LocalDateTime timerStart = LocalDateTime.now();

                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                LocalDateTime screenshotTime = LocalDateTime.now();
                millisScreen += Duration.between(timerStart, screenshotTime).toMillis();
                try
                {
                    BufferedImage next = ImageIO.read(scrFile);
                    writer.writeToSequence(next);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                LocalDateTime timerStop = LocalDateTime.now();
                long duration = Duration.between(timerStart, timerStop).toMillis();
                millis += duration;
                turns++;
                long sleep = CONFIGURATION.oneImagePerMilliseconds() - duration;
                Thread.sleep(sleep > 0 ? sleep : 0);
            }
            AllureAddons.addToReport("avarage gif sequence recording creation duration = " + millis + " / " + turns + "=" + millis / turns,
                                     "");
            AllureAddons.addToReport("avarage screenshot creation duration = " + millisScreen + " / " + turns + "=" + millisScreen / turns,
                                     "");
            if (save)
            {
                Allure.addAttachment(giftName, "image/gif", new FileInputStream(gif), "gif");
            }
            if (CONFIGURATION.deleteGifsAfterAddingToAllureReport())
            {
                gif.delete();
            }
        }
        catch (IOException | InterruptedException e1)
        {
            e1.printStackTrace();
        }
    }

    public void stopRun(boolean appendGif)
    {
        this.run = false;
        this.save = appendGif;
    }
}
