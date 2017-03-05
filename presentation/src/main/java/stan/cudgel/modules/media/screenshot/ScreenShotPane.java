package stan.cudgel.modules.media.screenshot;

import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.Pane;
import stan.cudgel.App;
import stan.cudgel.R;
import stan.cudgel.contracts.ScreenShotContract;
import stan.cudgel.di.PlatformUtil;
import stan.cudgel.units.CallbackConnector;
import stan.cudgel.units.ui.MVPPane;
import stan.cudgel.utils.CSS;

public class ScreenShotPane
    extends MVPPane<ScreenShotContract.Presenter>
{
    private interface Styles
    {
        int small_button_size = 24;
        int medium_button_size = 48;

        String main = new CSS()
                .addFxBackgroundColor("null")
                .generate();

        interface Cancel
        {
            CSS main = new CSS()
                    .addClearFocusBorder()
                    .addFxEffectDropshadow(BlurType.THREE_PASS_BOX, R.colors.BLACK, 3, 0)
                    .addFxBackgroundRadius(small_button_size/2)
                    .addFxBackgroundPosition(CSS.FxBackgroundPosition.CENTER)
                    .addFxBackgroundRepeat(BackgroundRepeat.NO_REPEAT)
                    .addFxBackgroundSize(small_button_size/3*2)
                    .addFxBackgroundImage(R.images.CLOSE_BLACK);
            String normal = main.copy()
                                .addFxBackgroundColor(R.colors.WHITE)
                                .generate();
            String hover = main.copy()
                               .addFxBackgroundColor(R.colors.GRAY)
                               .generate();
            String pressed = main.copy()
                                 .addFxBackgroundColor(R.colors.GRAY_DARK)
                                 .generate();
        }
        interface Save
        {
            CSS main = new CSS()
                    .addClearFocusBorder()
                    .addFxEffectDropshadow(BlurType.THREE_PASS_BOX, R.colors.BLACK, 3, 0)
                    .addFxBackgroundRadius(medium_button_size/2)
                    .addFxBackgroundPosition(CSS.FxBackgroundPosition.CENTER)
                    .addFxBackgroundRepeat(BackgroundRepeat.NO_REPEAT)
                    .addFxBackgroundSize(medium_button_size/3*2)
                    .addFxBackgroundImage(R.images.DOWNLOAD);
            String normal = main.copy()
                                .addFxBackgroundColor(R.colors.PRIMARY)
                                .generate();
            String hover = main.copy()
                               .addFxBackgroundColor(R.colors.PRIMARY, 25)
                               .generate();
            String pressed = main.copy()
                                 .addFxBackgroundColor(R.colors.PRIMARY, 50)
                                 .generate();
        }
    }
    private enum State
    {
        TAKE,
        SAVE
    }

    private final ScreenShotContract.View view = new ScreenShotContract.View()
    {
        public void hide()
        {
            behaviour.close();
        }
    };

    private Pane grabber;
    private Button saveButton;
    private Button sendTelegramButton;
    private Button cancelButton;

    private final PlatformUtil.ScreenShoter screenShoter;
    private final ScreenShotContract.Behaviour behaviour;

    private State viewState;
    private byte[] tempImage;

    public ScreenShotPane(PlatformUtil.ScreenShoter ss, ScreenShotContract.Behaviour b, CallbackConnector<ScreenShotContract.Callback> connector, double w, double h)
    {
        super(Styles.main, w, h);
        screenShoter = ss;
        behaviour = b;
        connector.set(new ScreenShotContract.Callback()
        {
            @Override
            public void newScreenShot()
            {
                clear();
            }
        });
    }
    protected void initViews()
    {
        grabber = new ScreenShotGrabber(new ScreenShotGrabber.ScreenShotGrabberListener()
        {
            public void cancel()
            {
                tempImage = null;
                behaviour.close();
            }
            public void grab(int x, int y, int w, int h)
            {
                viewState = State.SAVE;
                grabber.setVisible(false);
                runOnNewThread(() ->
                {
                    tempImage = screenShoter.take(x, y, w, h);
                    runOnUiThread(() ->
                    {
                        cancelButton.setVisible(true);
                        saveButton.setVisible(true);
                    });
                }, 100);
            }
        });
        cancelButton = new Button();
        cancelButton.setMinSize(Styles.small_button_size, Styles.small_button_size);
        setStyle(cancelButton, Styles.Cancel.normal, Styles.Cancel.hover, Styles.Cancel.pressed);
        saveButton = new Button();
        saveButton.setMinSize(Styles.medium_button_size, Styles.medium_button_size);
        setStyle(saveButton, Styles.Save.normal, Styles.Save.hover, Styles.Save.pressed);
        addChildrens(grabber, cancelButton, saveButton);
    }
    protected void init()
    {
        grabber.setMinSize(getWidth(), getHeight());
        clear();
        setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                //getPresenter().saveScreenShot(screenShoter.take(0, 0, (int)getWidth(), (int)getHeight()));
            }
            else if(event.getButton() == MouseButton.SECONDARY)
            {
                //behaviour.close();
            }
        });
        setOnMousePressed(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
            }
        });
        setOnMouseDragged(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY && viewState == State.TAKE)
            {
                moveNode(cancelButton, (int)event.getX() - cancelButton.getWidth()/2, (int)event.getY() - cancelButton.getHeight());
                moveNode(saveButton, (int)event.getX() - saveButton.getWidth() - cancelButton.getWidth()/2, (int)event.getY() - saveButton.getHeight()/2);
            }
        });
        cancelButton.setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY && cancelButton.isHover())
            {
                behaviour.close();
            }
        });
        saveButton.setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY && saveButton.isHover())
            {
                getPresenter().saveScreenShot(tempImage);
            }
            else if(event.getButton() == MouseButton.SECONDARY && saveButton.isHover())
            {
                behaviour.close();
            }
        });
        setPresenter(new ScreenShotPresenter(view, new ScreenShotModel(App.getAppComponent().getFoldersAccess())));
    }

    private void clear()
    {
        tempImage = null;
        grabber.setVisible(true);
        cancelButton.setVisible(false);
        saveButton.setVisible(false);
        viewState = State.TAKE;
    }
}