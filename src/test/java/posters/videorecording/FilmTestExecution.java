package posters.videorecording;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Timer;
import java.util.WeakHashMap;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;

import com.xceptance.neodymium.util.AllureAddons;
import com.xceptance.neodymium.util.Neodymium;

import io.qameta.allure.Allure;

public class FilmTestExecution
{
    private static final Map<Thread, VideoRecoringConfigutations> CONTEXTS = Collections.synchronizedMap(new WeakHashMap<>());

    private String folderName;

    private Timer screenshotTime;

    private String videoFileName;

    private static VideoRecoringConfigutations getContext()
    {
        return CONTEXTS.computeIfAbsent(Thread.currentThread(), key -> {
            return ConfigFactory.create(VideoRecoringConfigutations.class);
        });
    }

    public FilmTestExecution startFilmingTest(String testName)
    {
        folderName = getContext().tempFolderToStoreVideos() + testName.replaceAll("\\s", "-").replaceAll(":", "-").replaceAll("/", "_");
        videoFileName = folderName + ".mp4";
        screenshotTime = new Timer();
        File directory = new File(folderName);
        if (!directory.exists())
        {
            directory.mkdir();
        }
        TakeScreenshotsThread takeScreenshotsThread = new TakeScreenshotsThread(Neodymium.getDriver(), folderName);
        screenshotTime.schedule(takeScreenshotsThread, 0, getContext().oneImagePerMilliseconds());
        return this;
    }

    public void finishFilmingTest(boolean testFailed)
    {
        LocalDateTime now = LocalDateTime.now();
        screenshotTime.cancel();
        try
        {
            convertScreenshotsToVideo();
            if (getContext().appendAllVideosToReport() || testFailed)
            {
                Allure.addAttachment(folderName, "video/mp4", new FileInputStream(videoFileName), "mp4");
            }
            if (getContext().deleteVideosAfterAddingToAllureReport())
            {
                new File(videoFileName).delete();
            }
            FileUtils.deleteDirectory(new File(folderName));
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }
        LocalDateTime finish = LocalDateTime.now();
        AllureAddons.addToReport("video creation for this test took " + Duration.between(now, finish).toMillis() + " milliseconds", "");
    }

    private void convertScreenshotsToVideo() throws IOException, InterruptedException
    {
        ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-r", " 5/1", "-i", folderName + "/img%d.png", "-c:v", "libx264", videoFileName);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = in.readLine()) != null)
        {
            System.out.println(line);
        }
        if (p.waitFor() == 0)
        {
            System.out.println("File " + folderName + " written successfully.");
        }
        else
        {
            System.out.println("File " + folderName + " write error.");
        }
    }
}
