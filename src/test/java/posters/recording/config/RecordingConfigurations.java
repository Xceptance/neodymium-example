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

    @Key("gif.tempFolderToStoreRecoring")
    @DefaultValue("target/gifs/")
    public String tempFolderToStoreRecoring();

    @Key("gif.deleteRecordingsAfterAddingToAllureReport")
    @DefaultValue("true")
    public boolean deleteRecordingsAfterAddingToAllureReport();

    @Key("gif.appendAllRecordingsToReport")
    @DefaultValue("false")
    public boolean appendAllRecordingsToReport();

    @Key("gif.imageQuality")
    @DefaultValue("0.2f")
    public double imageQuality();

    @Key("gif.imageScaleFactor")
    @DefaultValue("1.0")
    public double imageScaleFactor();

    public String format();

    // only for gif
    public boolean loop();

    // only for video
    public String ffmpegBinaryPath();

    public String ffmpegLogFile();
}
