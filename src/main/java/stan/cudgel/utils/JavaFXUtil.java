package stan.cudgel.utils;

import javafx.application.Platform;
import javafx.scene.Node;

import stan.cudgel.di.PlatformUtil;

public class JavaFXUtil
    implements PlatformUtil
{
    public JavaFXUtil()
    {

    }

    @Override
    public void runOnUiThread(Runnable r)
    {
        Platform.runLater(r);
    }

    @Override
    public void exit()
    {
        System.exit(0);
    }

    @Override
    public void log(Object tag, String message)
    {
        System.out.println(tag.getClass().getName() +"\n\t"+ message);
    }
    @Override
    public void log(String message)
    {
        System.out.println(message);
    }

    @Override
    public PlatformUtil.ViewDragger drag(Node n, double x, double y)
    {
        return new Dragger(n, x, y);
    }

    private class Dragger
        implements PlatformUtil.ViewDragger
    {
        private double dX;
        private double dY;
        private Node node;

        Dragger(Node n, double x, double y)
        {
            node = n;
            dX = node.getLayoutX() - x;
            dY= node.getLayoutY() - y;
        }

        @Override
        public void drag(double newX, double newY)
        {
            node.setLayoutX(newX + dX);
            node.setLayoutY(newY + dY);
        }
    }
}