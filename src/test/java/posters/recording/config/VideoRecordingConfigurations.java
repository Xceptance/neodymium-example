package posters.recording.config;

import org.aeonbits.owner.Config.Sources;

@Sources(
    {
      "system:env", "system:properties", "file:config/dev-video-recording.properties", "file:config/video-recording.properties"
    })
public interface VideoRecordingConfigurations extends RecordingConfigurations
{
    @Key("video.enableFilming")
    @DefaultValue("true")
    public boolean enableFilming();

    @Key("video.oneImagePerMilliseconds")
    @DefaultValue("500")
    public int oneImagePerMilliseconds();

    @Key("video.tempFolderToStoreVideos")
    @DefaultValue("target/videos/")
    public String tempFolderToStoreVideos();

    @Key("video.deleteVideosAfterAddingToAllureReport")
    @DefaultValue("true")
    public boolean deleteVideosAfterAddingToAllureReport();

    @Key("video.appendAllVideosToReport")
    @DefaultValue("false")
    public boolean appendAllVideosToReport();

    @Key("video.imageQuality")
    @DefaultValue("1.0")
    public double imageQuality();

    @Key("video.imageScaleFactor")
    @DefaultValue("1.0")
    public double imageScaleFactor();

    @Key("video.ffmpegBinaryPath")
    @DefaultValue("ffmpeg")
    public String ffmpegBinaryPath();

    @Key("video.ffmpegLogFile")
    @DefaultValue("target/ffmpeg_output_msg.txt")
    public String ffmpegLogFile();

    @Override
    @DefaultValue("mp4")
    public String format();

    @Override
    @DefaultValue("false")
    public boolean loop();
}
