package stan.cudgel.modules.media.screenshot;

import java.io.FileOutputStream;
import java.io.IOException;

import stan.cudgel.contracts.ScreenShotContract;
import stan.cudgel.di.FoldersAccess;
import stan.cudgel.di.Settings;

class ScreenShotModel
    implements ScreenShotContract.Model
{
    private Settings settings;

    ScreenShotModel(Settings ss)
    {
        settings = ss;
    }

    public void saveScreenShot(byte[] b)
    {
        try(FileOutputStream stream = new FileOutputStream(settings.getMedia().getScreenshotsPath() + "/" + System.currentTimeMillis() + ".png"))
        {
            stream.write(b);
        }
        catch(IOException e)
        {
        }
    }

    public void sendScreenShot(byte[] b)
    {
    }
}