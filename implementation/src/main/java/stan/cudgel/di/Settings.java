package stan.cudgel.di;

import stan.cudgel.cores.SettingsCore;
import stan.cudgel.cores.TelegramCore;

public interface Settings
{
    SettingsCore.Media getMedia();
    void setMedia(SettingsCore.Media media);

    TelegramCore.Settings getTelegramSettings();
    void setTelegramSettings(TelegramCore.Settings settings);
}