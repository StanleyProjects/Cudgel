package stan.cudgel.units.ui;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.paint.Color;

public abstract class MVPPane<P>
        extends Pane
{
    private P presenter;
    private String tag;

    public MVPPane(String css, double width, double height)
    {
        super();
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
    final protected void addChildrens(Node... childrens)
    {
        getChildren().addAll(childrens);
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

    abstract protected void initViews();
    abstract protected void init();
}