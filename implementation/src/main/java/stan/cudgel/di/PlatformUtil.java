package stan.cudgel.di;

import javafx.scene.Node;

public interface PlatformUtil
{
    void runOnUiThread(Runnable r);
    ViewDragger newDragger(Node node, double startX, double startY);
    ScreenShoter getScreenShoter();
    void log(String message);
    void exit();

    interface ViewDragger
    {
    	void drag(double newX, double newY);
    }

    interface ScreenShoter
    {
        byte[] take(int x, int y, int w, int h);
    }
}