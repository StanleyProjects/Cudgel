package stan.cudgel.boxes;

import java.util.HashMap;
import java.util.Map;

import stan.boxes.ORM;
import stan.cudgel.cores.SettingsCore;
import stan.cudgel.cores.TelegramCore;
import stan.cudgel.di.FoldersAccess;
import stan.cudgel.di.Settings;
import stan.boxes.Case;
import stan.cudgel.modules.settings.models.Media;
import stan.cudgel.modules.telegram.models.TelegramSettings;

public class Cases
    implements Settings
{
    private final FoldersAccess foldersAccess;
    private final Case<SettingsCore.Media> mediaCase;
    private final Case<TelegramCore.Settings> telegramSettingsCase;

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
        telegramSettingsCase = new Case<>(new TelegramSettings(-1, null, -1), new ORM<TelegramCore.Settings>()
        {
            public Map write(TelegramCore.Settings settings)
            {
                Map map = new HashMap();
                map.put("botId", settings.getBotId());
                map.put("botToken", settings.getBotToken());
                map.put("chatId", settings.getChatId());
                return map;
            }
            public TelegramCore.Settings read(Map map)
            {
                return new TelegramSettings(((Long)map.get("botId")).intValue(),
                        (String)map.get("botToken"),
                        ((Long)map.get("chatId")).intValue());
            }
        }, foldersAccess.getDatabasePath() + "/telegramSettingsCase");
    }

    public SettingsCore.Media getMedia()
    {
        return mediaCase.get();
    }
    public void setMedia(SettingsCore.Media media)
    {
        mediaCase.save(media);
    }

    public TelegramCore.Settings getTelegramSettings()
    {
        return telegramSettingsCase.get();
    }
    public void setTelegramSettings(TelegramCore.Settings settings)
    {
        telegramSettingsCase.save(settings);
    }
}