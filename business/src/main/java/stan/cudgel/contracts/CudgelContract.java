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
        void exit();
    }
    interface Callback
    {
        void showMusicPlayerButton(boolean show);
    }
}