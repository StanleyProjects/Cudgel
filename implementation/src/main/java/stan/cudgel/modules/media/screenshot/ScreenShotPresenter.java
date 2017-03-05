package stan.cudgel.modules.media.screenshot;

import stan.cudgel.contracts.ScreenShotContract;
import stan.cudgel.units.mvp.ModelPresenter;

class ScreenShotPresenter
        extends ModelPresenter<ScreenShotContract.View, ScreenShotContract.Model>
        implements ScreenShotContract.Presenter
{
    ScreenShotPresenter(ScreenShotContract.View v, ScreenShotContract.Model m)
    {
        super(v, m);
    }

    public void saveScreenShot(byte[] b)
    {
        onNewThread(() ->
        {
            try
            {
                getModel().saveScreenShot(b);
                getView().success();
            }
            catch(ScreenShotContract.SaveScreenshotException e)
            {
                getView().error(e);
            }
        });
    }

    public void sendScreenShot(byte[] b)
    {
        onNewThread(() ->
        {
            try
            {
                getModel().sendScreenShot(b);
                getView().success();
            }
            catch(ScreenShotContract.NetworkException e)
            {
                getView().error(e);
            }
            catch(ScreenShotContract.SendScreenshotTelegramException e)
            {
                getView().error(e);
            }
        });
    }
}