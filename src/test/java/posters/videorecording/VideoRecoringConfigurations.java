package posters.videorecording;

import org.aeonbits.owner.Mutable;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources(
    {
      "system:env", "system:properties", "file:config/dev-video-recording.properties", "file:config/video-recording.properties"
    })
public interface VideoRecoringConfigurations extends Mutable
{
    @Key("video.oneImagePerMilliseconds")
    @DefaultValue("500")
    public int oneImagePerMilliseconds();

    @Key("video.ffmpegBinaryPath")
    @DefaultValue("ffmpeg")
    public String ffmpegBinaryPath();

    @Key("video.ffmpegLogFile")
    @DefaultValue("target/ffmpeg_output_msg.txt")
    public String ffmpegLogFile();

    @Key("video.tempFolderToStoreVideos")
    @DefaultValue("target/videos/")
    public String tempFolderToStoreVideos();

    @Key("video.deleteVideosAfterAddingToAllureReport")
    @DefaultValue("true")
    public boolean deleteVideosAfterAddingToAllureReport();

    @Key("video.appendAllVideosToReport")
    @DefaultValue("false")
    public boolean appendAllVideosToReport();
}
