package stan.cudgel.modules.main;

import stan.cudgel.contracts.MainContract;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.units.mvp.Presenter;

public class MainPresenter
    extends Presenter<MainContract.View>
    implements MainContract.Presenter
{
    private MainContract.View view;

    private PlatformUtil platformUtil;
    private boolean showMusicPlayer;

    MainPresenter(MainContract.View v, PlatformUtil pu)
    {
        super(v);
        platformUtil = pu;
        showMusicPlayer = false;
    }

    public void showMusicPlayer(boolean show)
    {
        if(showMusicPlayer != show)
        {
            showMusicPlayer = show;
            getView().showMusicPlayer(show);
        }
    }
    public void exit()
    {
        platformUtil.exit();
    }
}