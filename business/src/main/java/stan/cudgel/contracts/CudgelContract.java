package stan.cudgel.contracts;

public interface CudgelContract
{
    interface View
    {
    }
    interface Presenter
    {
    }

    interface Behaviour
    {
        void openMusicPlayer();
        void openSettings();
        void takeScreenShot();
        void exit();
    }
    interface Callback
    {
        void showMusicPlayerButton(boolean show);
        void showSettingsButton(boolean show);
        void showScreenShotButton(boolean show);
    }
}