package stan.cudgel.modules.cudgel;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BackgroundRepeat;

import stan.cudgel.di.PlatformUtil;
import stan.cudgel.contracts.CudgelContract;
import stan.cudgel.units.CallbackConnector;
import stan.cudgel.units.ui.MVPPane;
import stan.cudgel.utils.CSS;
import stan.cudgel.R;
import stan.cudgel.utils.ValueAnimator;

public class CudgelPane
    extends MVPPane<CudgelContract.Presenter>
{
    private interface Styles
    {
        int pane_size = 144;
        int small_button_size = 32;
        int scale_animation_time = 200;

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
                /*
                if(show)
                {
                    ValueAnimator.create(1000, 0, Styles.small_button_size, i -> musicPlayerButton.setMinSize(i, i)).animate();
                }
                else
                {
                    ValueAnimator.create(1000, Styles.small_button_size, 0, i -> musicPlayerButton.setMinSize(i, i)).animate();
                }
                */
                musicPlayerButton.setVisible(show);
            }
            public void showSettingsButton(boolean show)
            {
//                ValueAnimator.Updater<Double> scaleUpdater = d -> runOnUiThread(() -> setScale(settingsButton, d));
                ValueAnimator.Updater<Double> scaleUpdater = d ->
                {
//                    System.out.println("d " + d);
                    runOnUiThread(() ->
                    {
                        System.out.println("setScale " + d);
                        //setScale(settingsButton, d);
                        settingsButton.setScaleX(d);
                        settingsButton.setScaleY(d);
                        settingsButton.setOpacity(d);
                    });
                };
                ValueAnimator.Interpolator<Double> scaleInterpolator = ValueAnimator.linearDoubleInterpolator;
//                ValueAnimator.Interpolator<Double> scaleInterpolator = new ValueAnimator.AccelerateDoubleInterpolator(2);
                if(show)
                {
                    ValueAnimator.create(Styles.scale_animation_time, 0, 1, scaleUpdater, scaleInterpolator).setAnimationListener(new ValueAnimator.AnimationListener()
                    {
                        public void begin()
                        {
                            System.err.println("animation show begin");
                            //runOnUiThread(()->settingsButton.setVisible(true));
                        }
                        public void end()
                        {
                            System.err.println("animation show end");
                        }
                        public void cancel()
                        {
                        }
                    }).animate();
                }
                else
                {
                    ValueAnimator.create(Styles.scale_animation_time, 1, 0, scaleUpdater, scaleInterpolator).setAnimationListener(new ValueAnimator.AnimationListener()
                    {
                        public void begin()
                        {
                            System.err.println("animation show begin");
                        }
                        public void end()
                        {
                            System.err.println("animation show end");
//                            runOnUiThread(()->settingsButton.setVisible(false));
                        }
                        public void cancel()
                        {
                        }
                    }).animate();
                }
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