package stan.cudgel.modules.media.musicplayer;

import stan.cudgel.contracts.media.MusicPlayerContract;

public class MusicPlayerPresenter
    implements MusicPlayerContract.Presenter
{
    private int state;

    private MusicPlayerContract.View view;

    public MusicPlayerPresenter(MusicPlayerContract.View v)
    {
        this.view = v;
        state = States.NOT_INIT;
    }

    @Override
    public void stop()
    {
        state = States.STOP;
    }
    @Override
    public void playPauseSwitch()
    {
        if(state == States.NOT_INIT)
        {
            return;
        }
        if(state != States.PLAY)
        {
            state = States.PLAY;
            view.showPause();
        }
        else
        {
            state = States.PAUSE;
            view.showPlay();
        }
    }

    private interface States
    {
        int NOT_INIT = -1;
        int STOP = 1;
        int PLAY = 2;
        int PAUSE = 3;
    }
}