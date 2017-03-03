package stan.cudgel.contracts;

public interface ScreenShotContract
{
    interface Model
    {
        void saveScreenShot(byte[] b);
        void sendScreenShot(byte[] b);
    }
    interface View
    {
        void hide();
    }
    interface Presenter
    {
        void saveScreenShot(byte[] b);
        void sendScreenShot(byte[] b);
    }

    interface Behaviour
    {
        void close();
    }
    interface Callback
    {
        void newScreenShot();
    }
}