package stan.cudgel.modules.settings;

import stan.cudgel.contracts.SettingsContract;
import stan.cudgel.cores.SettingsCore;
import stan.cudgel.di.Settings;

class SettingsModel
    implements SettingsContract.Model
{
    private final Settings settings;

    SettingsModel(Settings ss)
    {
        settings = ss;
    }

    public SettingsCore.Media getMedia()
    {
        return settings.getMedia();
    }

    public void saveMedia(SettingsCore.Media media)
    {
        settings.setMedia(media);
    }
}