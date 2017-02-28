package stan.cudgel.modules.cudgel;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundRepeat;

import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.units.CallbackConnector;
import stan.cudgel.units.ui.MVPPane;
import stan.cudgel.utils.CSS;
import stan.cudgel.R;

public class CudgelPane
    extends MVPPane<CudgelContract.Presenter>
{
    private interface Styles
    {
        int pane_size = 144;
        int small_button_size = 32;

        CSS small_button = new CSS()
                .addFxEffectDropshadow(CSS.FxEffectBlurType.THREE_PASS_BOX, R.colors.BLACK, 3, 0, 0, 0)
                .addFxBackgroundRadius(small_button_size/2)
                .addFxBackgroundPosition(CSS.FxBackgroundPosition.CENTER)
                .addFxBackgroundRepeat(BackgroundRepeat.NO_REPEAT)
                .addFxBackgroundSize(18);
        interface MusicPlayer
        {
            CSS main = small_button.copy()
                    .addClearFocusBorder()
                    .addFxBackgroundImage(R.images.MUSIC);
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
        interface Settings
        {
            CSS main = small_button.copy()
                    .addClearFocusBorder()
                    .addFxBackgroundImage(R.images.SETTINGS);
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

    private CudgelContract.Behaviour behaviour;
    private PlatformUtil.ViewDragger viewDragger;
    private PlatformUtil platformUtil;

    private CudgelButton cudgelButton;
    private Button musicPlayerButton;
    private Button settingsButton;

    public CudgelPane(PlatformUtil pu, CudgelContract.Behaviour b, CallbackConnector<CudgelContract.Callback> connector)
    {
        super("-fx-background-color: null", Styles.pane_size, Styles.pane_size);
        platformUtil = pu;
        behaviour = b;
        connector.set(new CudgelContract.Callback()
        {
            public void showMusicPlayerButton(boolean show)
            {
                musicPlayerButton.setVisible(show);
            }
            public void showSettingsButton(boolean show)
            {
                settingsButton.setVisible(show);
            }
        });
    }
    protected void initViews()
    {
        cudgelButton = new CudgelButton();
        musicPlayerButton = new Button();
        musicPlayerButton.setMinSize(Styles.small_button_size, Styles.small_button_size);
        setStyle(musicPlayerButton, Styles.MusicPlayer.normal, Styles.MusicPlayer.hover, Styles.MusicPlayer.pressed);
        settingsButton = new Button();
        settingsButton.setMinSize(Styles.small_button_size, Styles.small_button_size);
        setStyle(settingsButton, Styles.Settings.normal, Styles.Settings.hover, Styles.Settings.pressed);
        addChildrens(cudgelButton, musicPlayerButton, settingsButton);
    }
    protected void init()
    {
        moveNode(cudgelButton
            ,getWidth()/2 - cudgelButton.getWidth()/2
            ,getHeight()/2 - cudgelButton.getHeight()/2);
        cudgelButton.setOnMouseReleased(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = null;
            }
            else if(event.getButton() == MouseButton.SECONDARY && cudgelButton.isHover())
            {
                behaviour.exit();
            }
        });
        cudgelButton.setOnMousePressed(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger = platformUtil.drag(CudgelPane.this, event.getScreenX(), event.getScreenY());
            }
        });
        cudgelButton.setOnMouseDragged(event ->
        {
            if(event.getButton() == MouseButton.PRIMARY)
            {
                viewDragger.drag(event.getScreenX(), event.getScreenY());
            }
        });
        moveNode(musicPlayerButton, getWidth()/2 - musicPlayerButton.getWidth()/2, 0);
        musicPlayerButton.setOnAction(event -> behaviour.openMusicPlayer());
        moveNode(settingsButton, 0, getHeight()/2 - musicPlayerButton.getHeight()/2);
        settingsButton.setOnAction(event -> behaviour.openSettings());
    }
}