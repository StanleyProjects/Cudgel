package stan.cudgel.modules.media.screenshot;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import stan.cudgel.connection.API;
import stan.cudgel.contracts.ScreenShotContract;
import stan.cudgel.di.Connection;
import stan.cudgel.di.Settings;

class ScreenShotModel
    implements ScreenShotContract.Model
{
    private Settings settings;
    private Connection connection;

    ScreenShotModel(Settings ss, Connection cncn)
    {
        settings = ss;
        connection = cncn;
    }

    public void saveScreenShot(byte[] b)
            throws ScreenShotContract.SaveScreenshotException
    {
        try(FileOutputStream stream = new FileOutputStream(settings.getMedia().getScreenshotsPath() + "/" + System.currentTimeMillis() + ".png"))
        {
            stream.write(b);
        }
        catch(IOException e)
        {
            throw new ScreenShotContract.SaveScreenshotException();
        }
    }

    public void sendScreenShot(byte[] b)
            throws ScreenShotContract.NetworkException, ScreenShotContract.SendScreenshotTelegramException
    {
        Connection.Answer answer;
        try
        {
            answer = connection.post(
                    API.Telegram.Bot.getSendPhotoLink(settings.getTelegramSettings().getBotId(), settings.getTelegramSettings().getBotToken()),
                    API.Telegram.Bot.getSendPhotoParams(settings.getTelegramSettings().getChatId()), Collections.emptyList(), Collections.singletonList(API.Telegram.Bot.getSendPhotoFilePart(b)));
        }
        catch(IOException e)
        {
            ScreenShotContract.NetworkException exception = new ScreenShotContract.NetworkException();
            exception.addSuppressed(e);
            throw exception;
        }
        if(answer.getCode() != 200)
        {
            throw new ScreenShotContract.SendScreenshotTelegramException("code: " + answer.getCode() + "\ndata: " + answer.getData());
        }
    }
}