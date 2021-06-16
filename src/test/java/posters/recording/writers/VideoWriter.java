package posters.recording.writers;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Selenide;
import com.xceptance.neodymium.util.AllureAddons;

import posters.recording.config.RecordingConfigurations;

public class VideoWriter implements Writer
{
    private static final Logger LOGGER = LoggerFactory.getLogger(VideoWriter.class);

    private ProcessBuilder pb;

    private OutputStream ffmpegInput;

    private Process p;

    protected VideoWriter(RecordingConfigurations recordingConfigurations, String videoFileName) throws IOException
    {
        pb = new ProcessBuilder(recordingConfigurations.ffmpegBinaryPath(), "-y", "-f", "image2pipe", "-r", " 5/1", "-i", "pipe:0", "-c:v", "libx264", videoFileName);
        pb.redirectErrorStream(true);
        pb.redirectOutput(Redirect.appendTo(new File(recordingConfigurations.ffmpegLogFile())));
    }

    @Override
    public void start() throws IOException
    {
        p = pb.start();
        ffmpegInput = p.getOutputStream();
    }

    @Override
    public void write(File image)
    {
        byte[] imageBytes;
        imageBytes = new byte[(int) image.length()];
        try (FileInputStream fileInputStream = new FileInputStream(image))
        {
            fileInputStream.read(imageBytes);

            ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imageBytes));
            BufferedImage img = ImageIO.read(iis);

            ImageIO.write(img, "PNG", ffmpegInput);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void stop()
    {
        try
        {
            ffmpegInput.close();
            ffmpegInput.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        long videoProcessingStart = new Date().getTime();
        while (p.isAlive())
        {
            if (new Date().getTime() - videoProcessingStart > 200000)
            {
                LOGGER.error("something went wrong with video processing");
                break;
            }
            LOGGER.info("process video is processing");
            Selenide.sleep(200);
        }

        AllureAddons.addToReport("video processing took " + (new Date().getTime() - videoProcessingStart + " ms"), "");
    }
}
