package stan.cudgel.contracts;

public interface MainContract
{
    interface View
    {
    	void showMusicPlayer(boolean show);
    }
    interface Presenter
    {
    	void showMusicPlayer(boolean show);
    	void exit();
    }
}