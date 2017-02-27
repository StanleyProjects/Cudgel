package stan.cudgel.di;

import javafx.scene.Node;

public interface PlatformUtil
{
    void runOnUiThread(Runnable r);
    ViewDragger drag(Node node, double startX, double startY);
    void log(String message);
    void exit();

    interface ViewDragger
    {
    	void drag(double newX, double newY);
    }
}