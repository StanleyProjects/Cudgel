package stan.cudgel.boxes;

import java.util.HashMap;
import java.util.Map;

import stan.boxes.ORM;
import stan.cudgel.cores.SettingsCore;
import stan.cudgel.di.FoldersAccess;
import stan.cudgel.di.Settings;
import stan.boxes.Case;
import stan.cudgel.modules.settings.models.Media;

public class Cases
    implements Settings
{
    private final FoldersAccess foldersAccess;
    private final Case<SettingsCore.Media> mediaCase;

    public Cases(FoldersAccess fa)
    {
        foldersAccess = fa;
        mediaCase = new Case<>(new Media(foldersAccess.getScreenshotsPath()), new ORM<SettingsCore.Media>()
        {
            public Map write(SettingsCore.Media media)
            {
                Map map = new HashMap();
                map.put("screenshotsPath", media.getScreenshotsPath());
                return map;
            }
            public SettingsCore.Media read(Map map)
            {
                return new Media((String)map.get("screenshotsPath"));
            }
        }, foldersAccess.getDatabasePath() + "/mediaCase");
    }

    public SettingsCore.Media getMedia()
    {
        return mediaCase.get();
    }

    public void setMedia(SettingsCore.Media media)
    {
        mediaCase.save(media);
    }
}