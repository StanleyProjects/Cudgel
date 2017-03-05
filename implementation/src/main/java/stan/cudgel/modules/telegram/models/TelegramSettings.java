package stan.cudgel.modules.telegram.models;

import stan.cudgel.cores.TelegramCore;

public class TelegramSettings
    implements TelegramCore.Settings
{
    private int botId;
    private String botToken;
    private int chatId;

    public TelegramSettings(int bi, String bt, int ci)
    {
        botId = bi;
        botToken = bt;
        chatId = ci;
    }

    public int getBotId()
    {
        return botId;
    }
    public String getBotToken()
    {
        return botToken;
    }
    public int getChatId()
    {
        return chatId;
    }
}