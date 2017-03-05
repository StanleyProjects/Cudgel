package stan.cudgel.contracts;

public interface ScreenShotContract
{
    interface Model
    {
        void saveScreenShot(byte[] b)
                throws SaveScreenshotException;
        void sendScreenShot(byte[] b)
                throws NetworkException, SendScreenshotTelegramException;
    }
    interface View
    {
        void success();
        void error(SaveScreenshotException e);
        void error(NetworkException e);
        void error(SendScreenshotTelegramException e);
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

    class SaveScreenshotException
            extends Exception
    {
    }
    class NetworkException
            extends Exception
    {
    }
    class SendScreenshotTelegramException
            extends Exception
    {
        public SendScreenshotTelegramException(String m)
        {
            super(m);
        }
    }
}