package stan.cudgel.cores;

public interface TelegramCore
{
    interface Settings
    {
        int getBotId();
        String getBotToken();
        int getChatId();
    }
}