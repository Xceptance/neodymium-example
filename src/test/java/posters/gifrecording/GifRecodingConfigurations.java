package posters.gifrecording;

import org.aeonbits.owner.Mutable;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(LoadType.MERGE)
@Sources(
    {
      "system:env", "system:properties", "file:config/dev-gif-recording.properties", "file:config/gif-recording.properties"
    })
public interface GifRecodingConfigurations extends Mutable
{
    @Key("gif.imageQuality")
    @DefaultValue("0.2f")
    public double imageQuality();

    @Key("gif.imageScaleFactor")
    @DefaultValue("1.0")
    public double imageScaleFactor();

    @Key("gif.oneImagePerMilliseconds")
    @DefaultValue("500")
    public int oneImagePerMilliseconds();

    @Key("gif.loop")
    @DefaultValue("false")
    public boolean loop();

    @Key("gif.tempFolderToStoreGifs")
    @DefaultValue("target/gifs/")
    public String tempFolderToStoreGifs();

    @Key("gif.deleteGifsAfterAddingToAllureReport")
    @DefaultValue("true")
    public boolean deleteGifsAfterAddingToAllureReport();

    @Key("gif.appendAllGifsToReport")
    @DefaultValue("false")
    public boolean appendAllGifsToReport();
}
