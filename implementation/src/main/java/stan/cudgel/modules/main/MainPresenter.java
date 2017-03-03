package stan.cudgel.modules.main;

import stan.cudgel.contracts.MainContract;
import stan.cudgel.units.mvp.RouterPresenter;

class MainPresenter
    extends RouterPresenter<MainContract.View, MainContract.Router>
    implements MainContract.Presenter
{
    private boolean showMusicPlayer;
    private boolean showSettings;
    private boolean showScreenShoter;

    MainPresenter(MainContract.View v, MainContract.Router r)
    {
        super(v, r);
        showMusicPlayer = false;
        showSettings = false;
        showScreenShoter = false;
    }

    public void showMusicPlayer(boolean show)
    {
        if(showMusicPlayer != show)
        {
            showMusicPlayer = show;
            getRouter().showMusicPlayer(show);
        }
    }

    @Override
    public void showScreenShoter(boolean show)
    {
        if(showScreenShoter != show)
        {
            showScreenShoter = show;
            getRouter().showScreenShoter(show);
        }
    }

    public void showSettings(boolean show)
    {
        if(showSettings != show)
        {
            showSettings = show;
            getRouter().showSettings(show);
        }
    }
    public void exit()
    {
        getRouter().exit();
    }
}