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
            getModel().saveScreenShot(b);
            getView().hide();
        });
    }

    public void sendScreenShot(byte[] b)
    {
    }
}