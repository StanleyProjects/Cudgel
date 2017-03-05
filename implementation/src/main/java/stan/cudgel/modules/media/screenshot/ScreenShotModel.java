package stan.cudgel.modules.media.screenshot;

import java.io.FileOutputStream;
import java.io.IOException;

import stan.cudgel.contracts.ScreenShotContract;
import stan.cudgel.di.FoldersAccess;

class ScreenShotModel
    implements ScreenShotContract.Model
{
    private FoldersAccess foldersAccess;

    ScreenShotModel(FoldersAccess fa)
    {
        foldersAccess = fa;
    }

    public void saveScreenShot(byte[] b)
    {
        try(FileOutputStream stream = new FileOutputStream(foldersAccess.getScreenshotsPath() + "/" + System.currentTimeMillis() + ".png"))
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