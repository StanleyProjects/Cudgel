package stan.cudgel.units.ui;

public abstract class MVPPane<P>
        extends UtilPane
{
    private P presenter;

    protected MVPPane(String css, double width, double height)
    {
        super(css, width, height);
    }

    protected void setPresenter(P p)
    {
        presenter = p;
    }
    protected P getPresenter()
    {
        return presenter;
    }
}