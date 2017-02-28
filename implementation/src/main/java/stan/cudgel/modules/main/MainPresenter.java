package stan.cudgel.modules.main;

import stan.cudgel.contracts.MainContract;
import stan.cudgel.units.mvp.RouterPresenter;

public class MainPresenter
    extends RouterPresenter<MainContract.View, MainContract.Router>
    implements MainContract.Presenter
{
    private boolean showMusicPlayer;
    private boolean showSettings;

    MainPresenter(MainContract.View v, MainContract.Router r)
    {
        super(v, r);
        showMusicPlayer = false;
    }

    public void showMusicPlayer(boolean show)
    {
        if(showMusicPlayer != show)
        {
            showMusicPlayer = show;
            getRouter().showMusicPlayer(show);
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