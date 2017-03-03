package stan.cudgel.units.ui;

import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public abstract class UtilPane
        extends Pane
{
    private String tag;

    public UtilPane(String css, double width, double height)
    {
        super();
        setCache(true);
        setCacheHint(CacheHint.QUALITY);
        setStyle(css);
        setPrefSize(width,height);
        tag = "["+getClass().getName().replace(getClass().getPackage().getName()+".", "")+"]";
        initViews();
        runOnUiThread(()->
        {
            init();
            log("\n\tw: " + getWidth() + " h: " + getHeight());
        });
    }

    final protected void moveNode(Node node, double x, double y)
    {
        node.setLayoutX(x);
        node.setLayoutY(y);
    }
    final protected void setStyle(Node node, String normal, String hover, String pressed)
    {
        node.setStyle(normal);
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> node.setStyle(pressed));
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> node.setStyle(node.isHover() ? hover : normal));
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> node.setStyle(hover));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> 
        {
            if(!node.isPressed())
            {
                node.setStyle(normal);
            }
        });
    }
    final protected void setScale(Node node, double s)
    {
        s = (int)(s*100);
        s /= 100;
        if(node.getScaleX() != s)
        {
            node.setScaleX(s);
        }
        if(node.getScaleY() != s)
        {
            node.setScaleY(s);
        }
    }
    final protected void setSize(Node node, int w, int h)
    {
        //node.resize(w, h);
        node.minHeight(h);
        node.prefHeight(h);
        node.maxHeight(h);
        node.minWidth(w);
        node.prefWidth(w);
        node.maxWidth(w);
        /*
        node.setMinSize(w, h);
        node.setPrefSize(w, h);
        node.setMaxSize(w, h);
        */
    }
    final protected void setSize(Node node, int size)
    {
        setSize(node, size, size);
    }
    final protected void addChildrens(Node... childrens)
    {
        getChildren().addAll(childrens);
    }
    final protected void log(String message)
    {
        System.err.println(tag +": "+ message);
    }
    final protected void runOnNewThread(Runnable r)
    {
        new Thread(r).start();
    }
    final protected void runOnNewThread(Runnable r, final long ms)
    {
        new Thread(r)
        {
            public void run()
            {
                try
                {
                    sleep(ms);
                }
                catch(InterruptedException e)
                {
                }
                super.run();
            }
        }.start();
    }
    final protected void runOnUiThread(Runnable r)
    {
        Platform.runLater(r);
    }

    abstract protected void initViews();
    abstract protected void init();
}