package stan.cudgel.contracts;

public interface MainContract
{
    interface View
    {
    }
    interface Router
    {
        void showSettings(boolean show);
        void showMusicPlayer(boolean show);
    	void exit();
    }
    interface Presenter
    {
        void showSettings(boolean show);
    	void showMusicPlayer(boolean show);
    	void exit();
    }
}