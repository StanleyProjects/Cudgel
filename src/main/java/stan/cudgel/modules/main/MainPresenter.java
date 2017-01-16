package stan.cudgel.modules.main;

import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.MainContract;

public class MainPresenter
    implements MainContract.Presenter
{
    private MainContract.View view;

    private PlatformUtil platformUtil;
    private boolean showMusicPlayer;

    public MainPresenter(MainContract.View v, PlatformUtil pu)
    {
        view = v;
        platformUtil = pu;
        showMusicPlayer = false;
    }
    @Override
    public void showMusicPlayer(boolean show)
    {
        if(showMusicPlayer != show)
        {
            showMusicPlayer = show;
            view.showMusicPlayer(show);
        }
    }
    @Override
    public void exit()
    {
        platformUtil.exit();
    }
}