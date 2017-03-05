package stan.cudgel.utils;

import javafx.application.Platform;
import javafx.scene.Node;

import stan.cudgel.di.PlatformUtil;

public class JavaFXUtil
    implements PlatformUtil
{
    private ScreenShoter screenShoter;

    public JavaFXUtil(ScreenShoter ss)
    {
        screenShoter = ss;
    }

    public void runOnUiThread(Runnable r)
    {
        Platform.runLater(r);
    }

    public ScreenShoter getScreenShoter()
    {
        return screenShoter;
    }

    public void exit()
    {
        System.exit(0);
    }

    public void log(String message)
    {
        System.out.println(message);
    }

    public ViewDragger newDragger(Node n, double x, double y)
    {
        return new Dragger(n, x, y);
    }

    private class Dragger
        implements ViewDragger
    {
        private double dX;
        private double dY;
        private Node node;

        Dragger(Node n, double x, double y)
        {
            node = n;
            dX = Math.abs(node.getLayoutX() - x);
            dY = Math.abs(node.getLayoutY() - y);
        }

        public void drag(double newX, double newY)
        {
            node.setLayoutX(newX - dX);
            node.setLayoutY(newY - dY);
        }
    }
}