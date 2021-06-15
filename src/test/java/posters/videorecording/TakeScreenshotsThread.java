package posters.videorecording;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.util.AllureAddons;

import io.qameta.allure.Allure;

public class TakeScreenshotsThread extends Thread
{
    private WebDriver driver;

    private ProcessBuilder pb;

    private String videoFileName;

    private boolean run = true;

    private boolean testFailed = true;

    public TakeScreenshotsThread(WebDriver driver, String videoFileName) throws IOException
    {
        pb = new ProcessBuilder(FilmTestExecution.getContext()
                                                 .ffmpegBinaryPath(), "-y", "-f", "image2pipe", "-r", " 5/1", "-i", "pipe:0", "-c:v", "libx264", videoFileName);
        pb.redirectErrorStream(true);
        pb.redirectOutput(Redirect.appendTo(new File(FilmTestExecution.getContext().ffmpegLogFile())));
        this.videoFileName = videoFileName;
        this.driver = driver;
    }

    @Override
    public synchronized void run()
    {
        try
        {
            Process p = pb.start();

            OutputStream ffmpegInput = p.getOutputStream();
            long turns = 0;
            long millis = 0;
            while (run)
            {
                long start = new Date().getTime();

                File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                byte[] image;
                image = new byte[(int) file.length()];
                try (FileInputStream fileInputStream = new FileInputStream(file))
                {
                    fileInputStream.read(image);

                    ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(image));
                    BufferedImage img = ImageIO.read(iis);

                    ImageIO.write(img, "PNG", ffmpegInput);

                    long duration = new Date().getTime() - start;
                    millis += duration;
                    turns++;
                    long sleep = FilmTestExecution.getContext().oneImagePerMilliseconds() - duration;
                    Thread.sleep(sleep > 0 ? sleep : 0);
                }
                catch (IOException | InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
            ffmpegInput.flush();
            ffmpegInput.close();
            AllureAddons.addToReport("avarage videos sequence recording creation duration = " + millis + " / " + turns + "="
                                     + millis / turns, "");
            long videoProcessingStart = new Date().getTime();
            while (p.isAlive())
            {
                if (new Date().getTime() - videoProcessingStart > 200000)
                {
                    System.out.println("something went wrong with video processing");
                    break;
                }
                System.out.println("process video is processing");
                Selenide.sleep(200);
            }

            AllureAddons.addToReport("video processing took " + (new Date().getTime() - videoProcessingStart + " ms"), "");
            if (FilmTestExecution.getContext().appendAllVideosToReport() || testFailed)
            {
                Allure.addAttachment(videoFileName, "video/mp4", new FileInputStream(videoFileName), "mp4");
            }
            if (FilmTestExecution.getContext().deleteVideosAfterAddingToAllureReport())
            {
                new File(videoFileName).delete();
                FileUtils.deleteDirectory(new File(FilmTestExecution.getContext().tempFolderToStoreVideos()));
            }
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    public void stopRun(boolean testFailed)
    {
        this.testFailed = testFailed;
        run = false;
    }
}
