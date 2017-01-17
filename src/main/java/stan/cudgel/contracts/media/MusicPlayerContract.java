package stan.cudgel.contracts.media;

public interface MusicPlayerContract
{
    interface View
    {
        void showStop();
        void showPlay();
        void showPause();
    }
    interface Presenter
    {
        void stop();
        void playPauseSwitch();
    }

    interface Behaviour
    {
        void close();
    }
}