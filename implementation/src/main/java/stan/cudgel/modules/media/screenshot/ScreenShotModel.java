package stan.cudgel.modules.media.screenshot;

import java.io.FileOutputStream;
import java.io.IOException;

import stan.cudgel.contracts.ScreenShotContract;

class ScreenShotModel
    implements ScreenShotContract.Model
{
    ScreenShotModel()
    {

    }

    public void saveScreenShot(byte[] b)
    {
        try(FileOutputStream stream = new FileOutputStream(".../image.png"))
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