package stan.cudgel.di;

import stan.cudgel.cores.SettingsCore;

public interface Settings
{
    SettingsCore.Media getMedia();
    void setMedia(SettingsCore.Media media);
}