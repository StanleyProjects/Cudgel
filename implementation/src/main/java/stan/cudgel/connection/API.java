package stan.cudgel.connection;

import java.util.HashMap;
import java.util.Map;

import stan.cudgel.di.Connection;

public interface API
{
    interface Telegram
    {
        String BASE_URL = "https://api.telegram.org";
        class Bot
        {
            static public String getSendMessageLink(int botId, String botToken)
            {
                return BASE_URL + "/bot"+botId+":"+botToken+"/sendMessage";
            }
            static public String getSendPhotoLink(int botId, String botToken)
            {
                return BASE_URL + "/bot"+botId+":"+botToken+"/sendPhoto";
            }
            static public Map<String, String> getSendMessageParams(int chatId, String text)
            {
                Map<String, String> params = new HashMap<>();
                params.put("chat_id", String.valueOf(chatId));
                params.put("text", text);
                return params;
            }
            static public Map<String, String> getSendPhotoParams(int chatId)
            {
                Map<String, String> params = new HashMap<>();
                params.put("chat_id", String.valueOf(chatId));
                return params;
            }
            static public Connection.FilePart getSendPhotoFilePart(byte[] data)
            {
                Connection.FilePart part = new Connection.FilePart();
                part.type = "photo";
                part.name = "0";
                part.data = data;
                return part;
            }
        }
    }
}