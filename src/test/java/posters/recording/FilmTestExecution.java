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

/**
 * Wrapper class to ease work with {@link TakeScreenshotsThread}. This class also ensures the thread safety of
 * {@link GifRecordingConfigurations} and {@link VideoRecordingConfigurations}
 * 
 * @author olha
 */
public class FilmTestExecution
{
    private static final Map<Thread, VideoRecordingConfigurations> CONTEXTS_VIDEO = Collections.synchronizedMap(new WeakHashMap<>());

    private static final Map<Thread, GifRecordingConfigurations> CONTEXTS_GIF = Collections.synchronizedMap(new WeakHashMap<>());

    private static final Map<Thread, TakeScreenshotsThread> GIF_THREADS = Collections.synchronizedMap(new WeakHashMap<>());

    private static final Map<Thread, TakeScreenshotsThread> VIDEO_THREADS = Collections.synchronizedMap(new WeakHashMap<>());

    /**
     * Gets {@link RecordingConfigurations} for current thread
     * 
     * @param configurationClass
     *            class of desired configurations ({@link VideoRecordingConfigurations} or
     *            {@link GifRecordingConfigurations})
     * @return gif or video configurations in form of {@link RecordingConfigurations} object
     */
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

    /**
     * Creates {@link TakeScreenshotsThread} for the specific test
     * 
     * @param testName
     *            name of the test to create the {@link TakeScreenshotsThread} for
     * @param isGif
     *            should the desired {@link TakeScreenshotsThread} create a gif
     * @return constructed {@link TakeScreenshotsThread}
     */
    private static TakeScreenshotsThread createTakeScreenshotsThread(String testName, boolean isGif)
    {
        try
        {
            return new TakeScreenshotsThread(Neodymium.getDriver(), isGif ? GifSequenceWriter.class
                                                                          : VideoWriter.class, getContext((isGif ? GifRecordingConfigurations.class
                                                                                                                 : VideoRecordingConfigurations.class)), testName);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates {@link TakeScreenshotsThread} object and puts it in the thread safe map
     * 
     * @param testName
     *            name of the test to create the {@link TakeScreenshotsThread} for
     * @param isGif
     *            should the desired {@link TakeScreenshotsThread} create a gif
     * @return constructed {@link TakeScreenshotsThread}
     */
    private static TakeScreenshotsThread createThread(String testName, boolean isGif)
    {
        Map<Thread, TakeScreenshotsThread> threads = isGif ? GIF_THREADS : VIDEO_THREADS;
        return threads.computeIfAbsent(Thread.currentThread(), key -> {
            return createTakeScreenshotsThread(testName, isGif);
        });
    }

    /**
     * Gets the {@link TakeScreenshotsThread} for the current thread and removes it
     * 
     * @param isGif
     *            should the desired {@link TakeScreenshotsThread} create a gif
     * @return {@link TakeScreenshotsThread} for the current thread
     */
    private static TakeScreenshotsThread popThread(boolean isGif)
    {
        Map<Thread, TakeScreenshotsThread> threads = isGif ? GIF_THREADS : VIDEO_THREADS;
        TakeScreenshotsThread thread = threads.get(Thread.currentThread());
        threads.remove(Thread.currentThread());
        return thread;
    }

    /**
     * Starts a gif recording for the current test
     * 
     * @param testName
     *            name of the test
     */
    public static void startGifRecording(String testName)
    {
        TakeScreenshotsThread thread = createThread(testName, true);
        if (thread != null)
        {
            thread.start();
        }
    }

    /**
     * Starts a video recording for the current test
     * 
     * @param testName
     *            name of the test
     */
    public static void startVideoRecording(String testName)
    {
        TakeScreenshotsThread thread = createThread(testName, false);
        if (thread != null)
        {
            thread.start();
        }
    }

    /**
     * Finishes gif filming for the current test
     * 
     * @param testFailed
     *            {@link Boolean} value of test test success (needed to decide whether the gif should be attached to
     *            allure report)
     */
    public static void finishGifFilming(boolean testFailed)
    {
        TakeScreenshotsThread thread = popThread(true);
        if (thread != null)
        {
            thread.stopRun(testFailed);
            try
            {
                thread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Finishes video filming for the current test
     * 
     * @param testFailed
     *            {@link Boolean} value of test test success (needed to decide whether the video should be attached to
     *            allure report)
     */
    public static void finishVideoFilming(boolean testFailed)
    {
        TakeScreenshotsThread thread = popThread(false);
        if (thread != null)
        {
            thread.stopRun(testFailed);
            try
            {
                thread.join();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
