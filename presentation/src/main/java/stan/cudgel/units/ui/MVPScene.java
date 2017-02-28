package stan.cudgel.units.ui;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public abstract class MVPScene<P>
        extends Scene
{
    private P presenter;
    private Pane root;
    private String tag;

    public MVPScene(Pane pane, String css, double width, double height, Color color)
    {
        super(pane, width, height, color);
        root = pane;
        tag = "["+getClass().getName().replace(getClass().getPackage().getName()+".", "")+"]";
        initViews(root);
        getRoot().setVisible(false);
        root.setStyle(css);
        runOnUiThread(()->
        {
            init();
            getRoot().setVisible(true);
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
    final protected void addChildrens(Node... childrens)
    {
        root.getChildren().addAll(childrens);
    }
    final protected void log(String message)
    {
        System.out.println(tag +": "+ message);
    }
    final protected void runOnNewThread(Runnable r)
    {
        new Thread(r).start();
    }
    final protected void runOnNewThread(Runnable r, final long ms)
    {
        new Thread(r)
        {
            @Override
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

    protected void setPresenter(P p)
    {
        presenter = p;
    }
    protected P getPresenter()
    {
        return presenter;
    }

    abstract protected void initViews(Pane root);
    abstract protected void init();
}