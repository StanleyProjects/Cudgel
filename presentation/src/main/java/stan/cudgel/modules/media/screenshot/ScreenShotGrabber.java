package stan.cudgel.modules.media.screenshot;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import stan.cudgel.R;
import stan.cudgel.units.ui.UtilPane;
import stan.cudgel.utils.CSS;

class ScreenShotGrabber
    extends UtilPane
{
    private interface Styles
    {
        String main = new CSS()
                .addFxBackgroundColor(R.colors.WHITE, 0.3)
                .generate();
    }

    private Rectangle rect;
    private double leftRect;
    private double topRect;

    private ScreenShotGrabberListener listener;

    ScreenShotGrabber(ScreenShotGrabberListener l)
    {
        super(Styles.main, 0, 0);
        listener = l;
    }
    protected void initViews()
    {
        rect = new Rectangle();
        rect.setFill(Color.web(R.colors.PRIMARY, 0.3));
        getChildren().add(rect);
    }
    protected void init()
    {
        clear();
        setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                listener.grab((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), (int)rect.getHeight());
                clear();
            }
            else if(event.getButton() == MouseButton.SECONDARY)
            {
                clear();
                listener.cancel();
            }
        });
        setOnMousePressed(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                clear();
                leftRect = event.getX();
                topRect = event.getY();
                rect.setX(event.getX());
                rect.setY(event.getY());
            }
        });
        setOnMouseDragged(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                double x = event.getX() - leftRect;
                rect.setX(x<0 ? event.getX() : leftRect);
                rect.setWidth(x<0 ? leftRect - event.getX() : x);
                double y = event.getY() - topRect;
                rect.setY(y<0 ? event.getY() : topRect);
                rect.setHeight(y<0 ? topRect - event.getY() : y);
            }
        });
    }


    private void clear()
    {
        rect.setWidth(0);
        rect.setHeight(0);
    }

    interface ScreenShotGrabberListener
    {
        void cancel();
        void grab(int x, int y, int w, int h);
    }
}