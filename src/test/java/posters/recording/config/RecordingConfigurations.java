package posters.recording.config;

import org.aeonbits.owner.Mutable;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;

@LoadPolicy(LoadType.MERGE)
public interface RecordingConfigurations extends Mutable
{
    @Key("gif.enableFilming")
    @DefaultValue("true")
    public boolean enableFilming();

    @Key("gif.oneImagePerMilliseconds")
    @DefaultValue("500")
    public int oneImagePerMilliseconds();

    @Key("gif.tempFolderToStoreVideos")
    @DefaultValue("target/videos/")
    public String tempFolderToStoreVideos();

    @Key("gif.deleteVideosAfterAddingToAllureReport")
    @DefaultValue("true")
    public boolean deleteVideosAfterAddingToAllureReport();

    @Key("gif.appendAllVideosToReport")
    @DefaultValue("false")
    public boolean appendAllVideosToReport();

    @Key("gif.imageQuality")
    @DefaultValue("0.2f")
    public double imageQuality();

    @Key("gif.imageScaleFactor")
    @DefaultValue("1.0")
    public double imageScaleFactor();

    public String format();

    // only for gif
    @DefaultValue("false")
    public boolean loop();

    // only for video
    @Key("video.ffmpegBinaryPath")
    @DefaultValue("ffmpeg")
    public String ffmpegBinaryPath();

    @Key("video.ffmpegLogFile")
    @DefaultValue("target/ffmpeg_output_msg.txt")
    public String ffmpegLogFile();
}
