package stan.cudgel.modules.settings.models;

import stan.cudgel.cores.SettingsCore;

public class Media
    implements SettingsCore.Media
{
    private String screenshotsPath;

    public Media(String sp)
    {
        screenshotsPath = sp;
    }

    public String getScreenshotsPath()
    {
        return screenshotsPath;
    }
}