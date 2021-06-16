package posters.recording;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.aeonbits.owner.ConfigFactory;

import com.xceptance.neodymium.util.Neodymium;

import posters.recording.config.GifRecordingConfigurations;
import posters.recording.config.RecordingConfigurations;
import posters.recording.config.VideoRecordingConfigurations;
import posters.recording.writers.GifSequenceWriter;
import posters.recording.writers.VideoWriter;
import posters.recording.writers.Writer;

public class FilmTestExecution
{
    private static final Map<Thread, VideoRecordingConfigurations> CONTEXTS_VIDEO = Collections.synchronizedMap(new WeakHashMap<>());

    private static final Map<Thread, GifRecordingConfigurations> CONTEXTS_GIF = Collections.synchronizedMap(new WeakHashMap<>());

    private TakeScreenshotsThread takeScreenshotsThread;

    public static RecordingConfigurations getContext(Class<? extends RecordingConfigurations> configurationClass)
    {
        if (configurationClass.equals(VideoRecordingConfigurations.class))
        {
            return CONTEXTS_VIDEO.computeIfAbsent(Thread.currentThread(), key -> {
                return ConfigFactory.create(VideoRecordingConfigurations.class);
            });
        }
        return CONTEXTS_GIF.computeIfAbsent(Thread.currentThread(), key -> {
            return ConfigFactory.create(GifRecordingConfigurations.class);
        });
    }

    private FilmTestExecution startFilmingTest(Class<? extends Writer> writer, Class<? extends RecordingConfigurations> configurationClass,
                                               String testName)
    {
        RecordingConfigurations config = getContext(configurationClass);
        if (config.enableFilming())
        {
            try
            {
                takeScreenshotsThread = new TakeScreenshotsThread(Neodymium.getDriver(), writer, getContext(configurationClass), testName);
                takeScreenshotsThread.start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return this;
    }

    public FilmTestExecution startFilmingVideo(String testName)
    {
        return startFilmingTest(VideoWriter.class, VideoRecordingConfigurations.class, testName);
    }

    public FilmTestExecution startFilmingGif(String testName)
    {
        return startFilmingTest(GifSequenceWriter.class, GifRecordingConfigurations.class, testName);
    }

    public void finishFilmingTest(boolean testFailed)
    {
        if (takeScreenshotsThread != null)
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
}
