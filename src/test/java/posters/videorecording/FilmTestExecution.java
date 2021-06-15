package posters.videorecording;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.aeonbits.owner.ConfigFactory;

import com.xceptance.neodymium.util.Neodymium;

public class FilmTestExecution
{
    private static final Map<Thread, VideoRecoringConfigurations> CONTEXTS = Collections.synchronizedMap(new WeakHashMap<>());

    private String folderName;

    private TakeScreenshotsThread takeScreenshotsThread;

    private String videoFileName;

    public static VideoRecoringConfigurations getContext()
    {
        return CONTEXTS.computeIfAbsent(Thread.currentThread(), key -> {
            return ConfigFactory.create(VideoRecoringConfigurations.class);
        });
    }

    public FilmTestExecution startFilmingTest(String testName)
    {
        File directory = new File(FilmTestExecution.getContext().tempFolderToStoreVideos());
        if (!directory.exists())
        {
            directory.mkdir();
        }
        folderName = getContext().tempFolderToStoreVideos() + testName.replaceAll("\\s", "-").replaceAll(":", "-").replaceAll("/", "_");
        videoFileName = folderName + ".mp4";
        try
        {
            takeScreenshotsThread = new TakeScreenshotsThread(Neodymium.getDriver(), videoFileName);
            takeScreenshotsThread.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return this;
    }

    public void finishFilmingTest(boolean testFailed)
    {
        takeScreenshotsThread.stopRun(testFailed);
        try
        {
            takeScreenshotsThread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
